#include <iostream>
using namespace std;

int main() {
    long long a, b;
    cout << "Introducir primer numero: ";
    if (!(cin >> a)) {
        cerr << "Valor invalido!\n";
        return 1;
    }

    cout << "Introducir segundo numero: ";
    if (!(cin >> b)) {
        cerr << "Valor invalido!.\n";
        return 1;
    }

    cout << "\nResultados:\n";
    cout << "Suma: " << (a + b) << '\n';
    cout << "Resta: " << (a - b) << '\n';
    cout << "Multiplicacion: " << (a * b) << '\n';

    if (b != 0) {
        cout << "Division: " << (a / b) << '\n';
        cout << "Residuo: " << (a % b) << '\n';
    } else {
        cout << "Division: Error - La division no se puede hacer por cero!\n";
        cout << "Residuo: Error - La division no se puede hacer por cero!\n";
    }

    return 0;
}