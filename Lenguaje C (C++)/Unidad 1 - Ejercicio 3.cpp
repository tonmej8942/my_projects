#include <iostream>
#include <string>

using namespace std;

int main() {
    string nombre;
    int edad;

    cout << "Introducir nombre completo: ";
    getline(cin, nombre);

    cout << "Introducir edad: ";
    cin >> edad;

    cout << "Hola " << nombre << ", usted tiene " << edad << " años" << endl;

    return 0;
}