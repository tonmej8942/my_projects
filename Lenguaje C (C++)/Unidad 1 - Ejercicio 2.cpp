#include <iostream>
#include <iomanip>

int main() {
    double x, y, z;

    std::cout << "Introducir primer numero real: ";
    if (!(std::cin >> x)) {
        std::cerr << "Valor invalido!\n";
        return 1;
    }

    std::cout << "Introducir segundo numero real: ";
    if (!(std::cin >> y)) {
        std::cerr << "Valor invalido!\n";
        return 1;
    }

    std::cout << "Introducir tercer numero real: ";
    if (!(std::cin >> z)) {
        std::cerr << "Valor invalido!\n";
        return 1;
    }

    double promedio = (x + y + z) / 3.0;

    std::cout << std::fixed << std::setprecision(6);
    std::cout << "\nPromedio (Media Aritmetica): " << promedio << '\n';

    return 0;
}