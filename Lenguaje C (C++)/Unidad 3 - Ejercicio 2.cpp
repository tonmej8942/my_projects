#include <iostream>
#include <string>

int main() {
    std::string nombComplt;
    std::cout << "Ingrese su nombre y apellido: ";
    std::getline(std::cin, nombComplt);
    std::cout << "\nMi nombre es:\n";
    std::cout << "---------------------------------------\n";
    for (std::string::iterator it = nombComplt.begin(); it != nombComplt.end(); ++it) {
        std::cout << *it << std::endl;
    }
    return 0;
}