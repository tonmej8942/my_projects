package com.sakila.console;
import java.util.List;
import com.sakila.data.ConnectionProvider;
import com.sakila.reports.AggregateReportService;
import com.sakila.reports.ReportService;
public class ReportsConsole {
	public static void menu(java.util.Scanner sc, ConnectionProvider provider) {
		ReportService basic = new ReportService(
				new com.sakila.data.FilmRepo(provider),
				new com.sakila.data.InventoryRepo(provider),
				new com.sakila.data.RentalRepo(provider)
		);
		AggregateReportService agg = new AggregateReportService(provider);
		System.out.println("-- Menu de Reportes --");
		System.out.println("a. Exportar Tablas a CSV"); //JSON
		System.out.println("b. Estadística Inventario por Pelicula");
		System.out.println("c. Aging de Cuentas por Cobrar");
		System.out.println("d. Rentas por Tienda/Ciudad/País/Cliente");
		System.out.println("e. Pagos por Tienda/Ciudad/País");
		System.out.print("Selecconar Opcion: ");
		String op = sc.nextLine();
		switch (op) {
			case "a":
				List<com.sakila.models.Film> films = basic.films.getAll();
     			List<com.sakila.models.Inventory> inventory = basic.inventory.getAll();
     			List<com.sakila.models.Rental> rental = basic.rentals.getAll();
     			basic.exportCsv(films, new String[]{"film_id","title","language_id"},
     			        f -> java.util.Arrays.asList(String.valueOf(f.film_id), f.title, String.valueOf(f.language_id)), new java.io.File("films.csv"));
     			basic.exportCsv(rental, new String[]{"rental_id","customer_id","inventory_id","rental_date"},
     					r -> java.util.Arrays.asList(String.valueOf(r.rental_id), String.valueOf(r.customer_id), String.valueOf(r.inventory_id), String.valueOf(r.rental_date)), new java.io.File("rental.csv"));
     			basic.exportCsv(inventory, new String[]{"inventory_id","film_id","store_id"},
     					i -> java.util.Arrays.asList(String.valueOf(i.inventory_id), String.valueOf(i.film_id), String.valueOf(i.store_id)), new java.io.File("inventory.csv"));
     			/*basic.exportJson(films,
     			        f -> String.format("{\"film_id\":%d,\"title\":\"%s\",\"language_id\":%d}",
     			                f.film_id, sanitize(f.title), f.language_id), new java.io.File("films.json"));
     			basic.exportJson(rental,
     			        r -> String.format("{\"renatl_id\":%d,\"customer_id\":\"%d\",\"inventory_id\":%d,\"rental_date\":%d}",
     			        		r.rental_id, sanitize(r.rental_id), r.customer_id, r.inventory_id, r.rental_date), new java.io.File("rental.json"));
     			basic.exportJson(inventory,
     			        i -> String.format("{\"inventory_id\":%d,\"film_id\":\"%d\",\"store_id\":%d}",
     			                i.inventory_id, sanitize(i.film_id), i.store_id), new java.io.File("inventory.json"));*/
     			System.out.println("Se ha Exportado las Tablas!!!"); /*films.csv y films.json*/
     			break;
    	    case "b":
    	    	java.util.Map<String,Object> stats = basic.statsInventoryPerFilm();
    	    	System.out.println("Total items: " + stats.get("total_items"));
    	    	System.out.println("Por film: " + stats.get("by_film"));
    	    	break;
    	    case "c":
    	    	java.util.Map<String, Long> aging = basic.agingAccountsReceivable(basic.rentals.getAll());
    	    	System.out.println("Aging: " + aging);
    	    	break;
    	    case "d":
    	    	System.out.println("Rentas por tienda: " + agg.rentalsByStore());
    	    	System.out.println("Rentas por ciudad: " + agg.rentalsByCity());
    	    	System.out.println("Rentas por país: " + agg.rentalsByCountry());
    	    	System.out.println("Rentas por cliente: " + agg.rentalsByCustomer());
    	    	break;
    	    case "e":
    	    	System.out.println("Pagos por tienda: " + agg.paymentsByStore());
    	    	System.out.println("Pagos por ciudad: " + agg.paymentsByCity());
    	    	System.out.println("Pagos por país: " + agg.paymentsByCountry());
    	    	break;
    	}
	}
	/*private static String sanitize(String s) { return s == null ? "" : s.replace("\"","\\\""); }*/
	/*private static Integer sanitize(Integer i) { return i == null ? "" : i.replace("\"","\\\""); }*/
}

/*case "a":
	System.out.println("Exportando Tablas...");*/
	/*exportAll(new FilmRepo(provider), "films", new String[]{"film_id","title","language_id"},
f -> java.util.Arrays.asList(String.valueOf(((Film)f).film_id), ((Film)f).title, String.valueOf(((Film)f).language_id)));
	exportAll(new ActorRepo(provider), "actors", new String[]{"actor_id","first_name","last_name"},
a -> java.util.Arrays.asList(String.valueOf(((Actor)a).actor_id), ((Actor)a).first_name, ((Actor)a).last_name));
	exportAll(new CustomerRepo(provider), "customers", new String[]{"customer_id","first_name","last_name","email"},
c -> java.util.Arrays.asList(String.valueOf(((Customer)c).customer_id), ((Customer)c).first_name, ((Customer)c).last_name, ((Customer)c).email));
	exportAll(new PaymentRepo(provider), "payments", new String[]{"payment_id","customer_id","amount","payment_date"},
p -> java.util.Arrays.asList(String.valueOf(((Payment)p).payment_id), String.valueOf(((Payment)p).customer_id), String.valueOf(((Payment)p).amount), String.valueOf(((Payment)p).payment_date)));
	exportAll(new RentalRepo(provider), "rentals", new String[]{"rental_id","customer_id","inventory_id","rental_date"},
r -> java.util.Arrays.asList(String.valueOf(((Rental)r).rental_id), String.valueOf(((Rental)r).customer_id), String.valueOf(((Rental)r).inventory_id), String.valueOf(((Rental)r).rental_date)));
	exportAll(new StoreRepo(provider), "stores", new String[]{"store_id","manager_staff_id","address_id"},
s -> java.util.Arrays.asList(String.valueOf(((Store)s).store_id), String.valueOf(((Store)s).manager_staff_id), String.valueOf(((Store)s).address_id)));
	exportAll(new AddressRepo(provider), "addresses", new String[]{"address_id","address","city_id","phone"},
a -> java.util.Arrays.asList(String.valueOf(((Address)a).address_id), ((Address)a).address, String.valueOf(((Address)a).city_id), ((Address)a).phone));
	exportAll(new CityRepo(provider), "cities", new String[]{"city_id","city","country_id"},
c -> java.util.Arrays.asList(String.valueOf(((City)c).city_id), ((City)c).city, String.valueOf(((City)c).country_id)));
	exportAll(new CountryRepo(provider), "countries", new String[]{"country_id","country"},
c -> java.util.Arrays.asList(String.valueOf(((Country)c).country_id), ((Country)c).country));
	System.out.println("Exportacion Completa!!!");
	break;*/