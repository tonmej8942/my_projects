#include <iostream>
using namespace std;

int main() {
    int numDesde, numHasta;
    int sum = 0;
    int cant = 0;
    cout << "Ingresar primer numero: ";
    cin >> numDesde;
    cout << "Ingresar segundo numero: ";
    cin >> numHasta;
    while (numDesde >= numHasta) {
        cout << "\nError: 'primer numero' debe ser estrictamente menor que 'segundo numero'." << endl;
        cout << "Ingrese primer numero nuevamente: ";
        cin >> numDesde;
        cout << "Ingrese segundo numero nuevamente: ";
        cin >> numHasta;
    }
    int actual = numDesde;
    while (actual <= numHasta) {
        if (actual % 7 == 0) {
            sum += actual;
            cant++;
        }
        actual++;
    }
    cout << "\n--- Resultados ---" << endl;
    if (cant > 0) {
        double prom = static_cast<double>(sum) / cant;
        cout << "La cantidad de multiplos de 7 encontrados son: " << cant << endl;
        cout << "La suma de los multiplos es: " << sum << endl;
        cout << "El promedio de los multiplos de 7 es: " << prom << endl;
    } else {
        cout << "No hay multiplos de 7 en el rango de " << numDesde << " al " << numHasta << "." << endl;
    }
    return 0;
}