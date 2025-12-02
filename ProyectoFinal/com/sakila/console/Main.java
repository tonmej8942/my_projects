package com.sakila.console;
import com.sakila.data.*;
import com.sakila.controllers.*;
import com.sakila.models.*;
import java.util.*;
import java.time.LocalDateTime;
public class Main {
    public static void main(String[] args) {
        ConnectionProvider provider = new ConnectionProvider(
            System.getenv().getOrDefault("SAKILA_URL", "jdbc:mysql//localhost:3306/sakila" /*"jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=UTC"*/),
            System.getenv().getOrDefault("SAKILA_USER", "TonyMej4289"/*"root"*/),
            System.getenv().getOrDefault("SAKILA_PASS", "!qcL4EJ$dRdc#aq9E!zC" /*"root"*/)
        );
        FilmController films = new FilmController(new FilmRepo(provider));
        InventoryController inventory = new InventoryController(new InventoryRepo(provider));
        RentalController rentals = new RentalController(new RentalRepo(provider));
        CustomerController customers = new CustomerController(new CustomerRepo(provider));
        PaymentController payments = new PaymentController(new PaymentRepo(provider));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=== Tienda de Peliculas: Sakila Edition ===");
            System.out.println("1. Gestion de Peliculas");
            System.out.println("2. Gestion de Inventario");
            System.out.println("3. Gestion de Rentas");
            System.out.println("4. Reportes");
            System.out.println("5. Gestion de Clientes");
            System.out.println("6. Gestion de Pagos");
            System.out.println("0. Salir");
            System.out.print("Seleccionar Opcion: ");
            String op = sc.nextLine();
            switch (op) {
                case "1": filmMenu(sc, films); break;
                case "2": inventoryMenu(sc, inventory); break;
                case "3": rentalMenu(sc, rentals); break;
                case "4": ReportsConsole.menu(sc, provider); break;
                case "5": customerMenu(sc, customers); break;
                case "6": paymentMenu(sc, payments); break;
                case "0": System.out.println("Hasta luego!!!"); return;
                default: System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
            }
        }
    }
    static void filmMenu(Scanner sc, FilmController films) {
        System.out.println("-- Menu de Peliculas --");
        System.out.println("a. Listar Peliculas");
        System.out.println("b. Buscar por Titulo (Regex (Titulo))");
        System.out.println("c. Crear Pelicula");
        System.out.println("d. Actualizar Pelicula");
        System.out.println("e. Eliminar Pelicula");
        System.out.print("Seleccionar Opcion: ");
        String op = sc.nextLine();
        switch (op) {
            case "a":
                films.listAll().forEach(f -> System.out.println(f.film_id + " - " + f.title));
                break;
            case "b":
                System.out.print("Regex de Pelicula: ");
                String rx = sc.nextLine();
                films.searchByTitleRegex(rx).forEach(f -> System.out.println(f.film_id + " - " + f.title));
                break;
            case "c":
                Film nf = new Film();
                System.out.print("Titulo: "); nf.title = sc.nextLine();
                System.out.print("Descripcion: "); nf.description = sc.nextLine();
                System.out.print("Año Lanzamiento: "); nf.release_year = Integer.parseInt(sc.nextLine());
                System.out.print("ID Idioma: "); nf.language_id = Integer.parseInt(sc.nextLine());
                nf.last_update = LocalDateTime.now();
                Integer id = films.create(nf);
                System.out.println("ID Pelicula Creada!!!" + id);
                break;
            case "d":
                System.out.print("ID de Pelicula: "); Integer uid = Integer.parseInt(sc.nextLine());
                Optional<Film> opt = films.get(uid);
                if (opt.isEmpty()) { System.out.println("No Existe Pelicula"); break; }
                Film uf = opt.get();
                System.out.print("Nuevo Titulo: "); uf.title = sc.nextLine();
                uf.last_update = LocalDateTime.now();
                System.out.println(films.update(uid, uf) ? "Actualizada!" : "Sin Cambios.");
                break;
            case "e":
                System.out.print("ID: "); Integer did = Integer.parseInt(sc.nextLine());
                System.out.println(films.remove(did) ? "Eliminada!" : "No se puede Eliminar!"); // (FK o Inexistente)
                break;
            default:
                System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
        }
    }
    static void inventoryMenu(Scanner sc, InventoryController inv) {
        System.out.println("-- Menu de Inventario --");
        System.out.println("a. Listar Inventario");
        System.out.println("b. Buscar por ID Pelicula");
        System.out.println("c. Crear Inventario");
        System.out.println("d. Eliminar Inventario");
        System.out.print("Seleccionar Opcion: ");
        String op = sc.nextLine();
        switch (op) {
            case "a": inv.listAll().forEach(i -> System.out.println(i.inventory_id + " Pelicula = " + i.film_id)); break;
            case "b":
                System.out.print("ID Pelicula: "); Integer fid = Integer.parseInt(sc.nextLine());
                inv.byFilm(fid).forEach(i -> System.out.println(i.inventory_id + " Tienda = " + i.store_id));
                break;
            case "c":
                Inventory ni = new Inventory();
                System.out.print("ID Pelicula: "); ni.film_id = Integer.parseInt(sc.nextLine());
                System.out.print("ID Tienda: "); ni.store_id = Integer.parseInt(sc.nextLine());
                ni.last_update = java.time.LocalDateTime.now();
                Integer iid = inv.create(ni);
                System.out.println("ID Inventario Creada!!!" + iid);
                break;
            case "d":
                System.out.print("ID Inventario: "); Integer rid = Integer.parseInt(sc.nextLine());
                System.out.println(inv.remove(rid) ? "Eliminado!" : "No se pudo Eliminar!");
                break;
            default:
                System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
        }
    }
    static void rentalMenu(Scanner sc, RentalController rentals) {
        System.out.println("-- Menu de Rentas --");
        System.out.println("a. Listar Rentas");
        System.out.println("b. Buscar por ID Cliente");
        System.out.println("c. Crear Renta");
        System.out.print("Seleccionar Opcion: ");
        String op = sc.nextLine();
        switch (op) {
            case "a": rentals.listAll().forEach(r -> System.out.println(r.rental_id + " Cliente = " + r.customer_id)); break;
            case "b":
                System.out.print("ID Cliente: "); Integer cid = Integer.parseInt(sc.nextLine());
                rentals.byCustomer(cid).forEach(r -> System.out.println(r.rental_id + " Inventario = " + r.inventory_id));
                break;
            case "c":
                Rental nr = new Rental();
                nr.rental_date = java.time.LocalDateTime.now();
                System.out.print("ID Inventario: "); nr.inventory_id = Integer.parseInt(sc.nextLine());
                System.out.print("ID Cliente: "); nr.customer_id = Integer.parseInt(sc.nextLine());
                System.out.print("ID Staff: "); nr.staff_id = Integer.parseInt(sc.nextLine());
                nr.last_update = java.time.LocalDateTime.now();
                Integer rid = rentals.create(nr);
                System.out.println("ID Renta Creada!!!" + rid);
                break;
            default:
                System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
        }
    }
    static void customerMenu(Scanner sc, CustomerController customers) {
    	System.out.println("-- Menu de Clientes --");
        System.out.println("a. Listar Clientes");
        System.out.println("b. Buscar por ID Tienda");
        System.out.println("c. Crear Cliente");
        System.out.println("d. Actualizar Cliente");
        System.out.println("e. Eliminar Cliente");
        System.out.print("Seleccionar Opcion: ");
        String op = sc.nextLine();
        switch (op) {
            case "a":
                customers.listAll().forEach(c ->
                    System.out.println(c.customer_id + " - " + c.first_name + " " + c.last_name + " (Tienda: " + c.store_id + ")"));
                break;
            case "b":
                System.out.print("ID Tienda: "); Integer sid = Integer.parseInt(sc.nextLine());
                customers.byStore(sid).forEach(c ->
                    System.out.println(c.customer_id + " - " + c.first_name + " " + c.last_name));
                break;
            case "c":
                com.sakila.models.Customer nc = new com.sakila.models.Customer();
                System.out.print("ID Tienda: "); nc.store_id = Integer.parseInt(sc.nextLine());
                System.out.print("Nombre: "); nc.first_name = sc.nextLine();
                System.out.print("Apellido: "); nc.last_name = sc.nextLine();
                System.out.print("Email: "); nc.email = sc.nextLine();
                System.out.print("ID Direccion: "); nc.address_id = Integer.parseInt(sc.nextLine());
                nc.active = Boolean.TRUE;
                Integer cid = customers.create(nc);
                System.out.println("ID Cliente Creada!!!" + cid);
                break;
            case "d":
                System.out.print("ID Cliente: "); Integer uid = Integer.parseInt(sc.nextLine());
                Optional<com.sakila.models.Customer> oc = customers.get(uid);
                if (oc.isEmpty()) { System.out.println("No existe Cliente!"); break; }
                com.sakila.models.Customer cu = oc.get();
                System.out.print("Nuevo Email: "); cu.email = sc.nextLine();
                System.out.println(customers.update(uid, cu) ? "Actualizada!" : "Sin Cambios.");
                break;
            case "e":
                System.out.print("ID Cliente: "); Integer did = Integer.parseInt(sc.nextLine());
                System.out.println(customers.remove(did) ? "Eliminada!" : "No se puede Eliminar!"); //(FK o inexistente)
                break;
            default:
                System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
        }
    }
    static void paymentMenu(Scanner sc, PaymentController payments) {
        System.out.println("-- Menu de Pagos --");
        System.out.println("a. Listar Pagos");
        System.out.println("b. Buscar por ID Cliente");
        System.out.println("c. Crear Pago");
        System.out.println("d. Eliminar Pago");
        System.out.print("Seleccionar Opcion: ");
        String op = sc.nextLine();
        switch (op) {
            case "a":
                payments.listAll().forEach(p ->
                    System.out.println(p.payment_id + " Cliente = " + p.customer_id + " Monto = " + p.amount));
                break;
            case "b":
                System.out.print("ID Cliente: "); Integer cid = Integer.parseInt(sc.nextLine());
                payments.byCustomer(cid).forEach(p ->
                    System.out.println(p.payment_id + " Renta = " + p.rental_id + " Monto = " + p.amount));
                break;
            case "c":
                com.sakila.models.Payment np = new com.sakila.models.Payment();
                System.out.print("ID Cliente: "); np.customer_id = Integer.parseInt(sc.nextLine());
                System.out.print("ID Staff: "); np.staff_id = Integer.parseInt(sc.nextLine());
                System.out.print("ID Renta: "); np.rental_id = Integer.parseInt(sc.nextLine());
                System.out.print("Monto: "); np.amount = Double.parseDouble(sc.nextLine());
                np.payment_date = java.time.LocalDateTime.now();
                Integer pid = payments.create(np);
                System.out.println("ID Pago Creada!!!" + pid);
                break;
            case "d":
                System.out.print("ID Pago: "); Integer did = Integer.parseInt(sc.nextLine());
                System.out.println(payments.remove(did) ? "Eliminada!" : "No se puede Eliminar!");
                break;
            default:
                System.out.println("Opcion Invalida! Seleccione una de las Existentes.");
        }
    }
}