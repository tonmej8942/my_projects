#include <iostream>
#include <string>
#include <iomanip>
using namespace std;

struct Factura {
    long   factNumero;
    string factCliente;
    string factFecha;
    double factMonto;
    double factDescuento;
    double factNeto;
};

int main() {
    Factura f;
    cout << "========================================" << endl;
    cout << "        INGRESO DE FACTURA              " << endl;
    cout << "========================================" << endl;
    cout << "Numero de factura: ";
    cin >> f.factNumero;
    cin.ignore();
    cout << "Nombre del cliente: ";
    getline(cin, f.factCliente);
    cout << "Fecha (dd/mm/aaaa): ";
    getline(cin, f.factFecha);
    cout << "Monto bruto ($): ";
    cin >> f.factMonto;
    if (f.factMonto >= 50000.0) {
        f.factDescuento = f.factMonto * 0.14;
    } else if (f.factMonto >= 30000.0) {
        f.factDescuento = f.factMonto * 0.10;
    } else if (f.factMonto >= 20000.0) {
        f.factDescuento = f.factMonto * 0.07;
    } else {
        f.factDescuento = 0.0;
    }
    f.factNeto = f.factMonto - f.factDescuento;
    cout << endl;
    cout << "========================================" << endl;
    cout << "           DETALLE DE FACTURA           " << endl;
    cout << "========================================" << endl;
    cout << fixed << setprecision(2);
    cout << "Numero de factura: " << f.factNumero << endl;
    cout << "Cliente: " << f.factCliente << endl;
    cout << "Fecha: " << f.factFecha << endl;
    cout << "Monto bruto: $ " << setw(1) << f.factMonto << endl;
    cout << "Descuento: $ " << setw(1) << f.factDescuento << endl;
    cout << "----------------------------------------" << endl;
    cout << "Monto neto: $ " << setw(1) << f.factNeto << endl;
    cout << "========================================" << endl;
    return 0;
}