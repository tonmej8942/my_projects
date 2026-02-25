#include <iostream>
using namespace std;

int main() {
    cout << "--- Numeros primos entre 30 y 300 ---" << endl;
    for (int num = 30; num <= 300; num++) {
        bool esPrim = true;
        for (int div = 2; div * div <= num; div++) {
            if (num % div == 0) {
                esPrim = false;
                break;
            }
        }
        if (esPrim) {
            cout << num << " ";
        }
    }
    cout << "\n\nCalculo finalizado!" << endl;
    return 0;
}