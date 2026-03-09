#include <iostream>
#include <string>
#include <cctype>

int main() {
    std::string s;
    std::cout << "Introducir linea de texto:\n";
    std::getline(std::cin, s);

    int count_upper = 0;
    int count_lower = 0;
    int count_digits = 0;
    int count_punct = 0;
    int count_alnum = 0;   // letras + dígitos
    int count_alpha = 0;   // solo letras A-Z, a-z

    for (unsigned char ch : s) {
        if (std::isupper(ch)) ++count_upper;
        if (std::islower(ch)) ++count_lower;
        if (std::isdigit(ch)) ++count_digits;
        if (std::ispunct(ch)) ++count_punct;
        if (std::isalnum(ch)) ++count_alnum;
        if (std::isalpha(ch)) ++count_alpha;
    }

    std::cout << "\nResultados:\n";
    std::cout << "Mayusculas: " << count_upper << '\n';
    std::cout << "Minusculas: " << count_lower << '\n';
    std::cout << "Digitos: "    << count_digits << '\n';
    std::cout << "Signos de puntuacion: " << count_punct << '\n';
    std::cout << "Alfanumericos (letras + digitos): " << count_alnum << '\n';
    std::cout << "Alfabeticos (solo letras A-Z, a-z): " << count_alpha << '\n';

    return 0;
}