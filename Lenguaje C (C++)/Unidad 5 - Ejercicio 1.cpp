#include <iostream>
#include <iomanip>
#include <string>
using namespace std;
long fibonacci(long n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

int main() {
    long termn;
    cout << "SUCESION DE FIBONACCI | CONEJOS\n";
    cout << "Tabla de parejas por mes (genealogia de conejos)\n";
    cout << "" << string(46, '-') << "\n";
    cout << "" << left  << setw(10) << "Mes"
                 << setw(20) << "Parejas totales"
                 << "Suma \n"; //Formula de Calculo: (F(n-1)+F(n-2))
    cout << "" << string(46, '-') << "\n";
    for (long i = 0; i <= 12; i++)
    {
        long val = fibonacci(i);
        cout << "" << setw(10) << i << setw(20) << val;
        if (i >= 2)
            cout << fibonacci(i-1) << " + " << fibonacci(i-2)
                 << " = " << val;
        else
            cout << "(caso base)";
        cout << "\n";
    }
    cout << "" << string(46, '-') << "\n\n";
    cout << "Ingresar numero de mes para obtencion de parejas a fin de mes: "; //(n >= 0)
    cin  >> termn;
    if (termn < 0)
    {
        cout << "Error: el mes no puede ser negativo ";
        return 1;
    }
    long resltd = fibonacci(termn);
    cout << "Al fin del mes " /*<< setw(4) */<< termn << " hay " /*<< setw(8) */<< resltd << " pareja(s) \n";
    return 0;
}