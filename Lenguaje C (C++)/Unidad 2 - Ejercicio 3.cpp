#include <iostream>
using namespace std;

int main() {
    double base;
    int exponente;
    double result = 1.0;
    cout << "--- Calculadora de Potencia ---" << endl;
    cout << "Ingresar base (double): ";
    cin >> base;
    cout << "Ingresar exponente (int): ";
    cin >> exponente;
    int expAbsol = exponente;
    if (exponente < 0) {
        expAbsol = -exponente;
    }
    int i = 0;
    while (i < expAbsol) {
        result *= base;
        i++;
    }
    if (exponente < 0) {
        if (base == 0) {
            cout << "Error: No se puede elevar 0 a un exponente negativo (division por cero)." << endl;
            return 1; 
        }
        result = 1.0 / result;
    }
    cout << "\nResultado: " << base << " elevado a " << exponente << " es igual a: " << result << endl;
    return 0;
}