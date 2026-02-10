#include <iostream>
#include <limits>

int main() {
    char nombre[30];
    int edad;

    std::cout << "Introducir nombre: ";
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
    if (!std::cin.getline(nombre, sizeof(nombre))) {
        std::cerr << "Error: nombre invalido!\n";
        return 1;
    }

    std::cout << "Introducir edad: ";
    if (!(std::cin >> edad)) {
        std::cerr << "Error: edad invalida!\n";
        return 1;
    }

    std::cout << "Hola \"" << nombre << "\", usted tiene \"" << edad << "\" años\n";
    
    return 0;
}