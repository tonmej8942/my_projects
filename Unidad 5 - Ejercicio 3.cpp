#include <iostream>
#include <string>
#include <sstream>
#include <cmath>
#include <iomanip>
#include <limits>
using namespace std;
const char OP_SUMA      = '+';
const char OP_RESTA     = '-';
const char OP_MULT_MAY  = 'X';
const char OP_MULT_MIN  = 'x';
const char OP_DIV       = '/';
const char OP_RESIDUO   = '%';
const char OP_POTENCIA  = '^';
struct Operacion
{
    double operando1;
    char   operador;
    double operando2;
};
bool esOperadorValido(char op)
{
    return (op == OP_SUMA     || op == OP_RESTA    ||
            op == OP_MULT_MAY || op == OP_MULT_MIN ||
            op == OP_DIV      || op == OP_RESIDUO  ||
            op == OP_POTENCIA);
}
bool esNumero(const string& str, double& valorSalida)
{
    if (str.empty()) return false;
    istringstream iss(str);
    iss >> valorSalida;
    return !iss.fail() && iss.eof();
}
Operacion pedirYValidarOperacion()
{
    Operacion datos;
    string entrada;
    double valor;
    cout << "CALCULADORA\n";
    cout << "Operadores: + - X x / % ^ \n";
    while (true)
    {
        cout << "Ingresar primer operando: ";
        cin  >> entrada;
        if (esNumero(entrada, valor)) { datos.operando1 = valor; break; }
        cout << " ! \"" << entrada << "\" no es un numero valido.\n";
    }
    while (true)
    {
        cout << "Ingresar operador: ";
        cin  >> entrada;
        if (entrada.size() == 1 && esOperadorValido(entrada[0]))
        {
            datos.operador = (entrada[0] == OP_MULT_MIN) ? OP_MULT_MAY : entrada[0];
            break;
        }
        cout << "Operador invalido. Use uno de: + - X x / % ^\n";
    }
    while (true)
    {
        cout << "Ingresar segundo operando: ";
        cin  >> entrada;
        if (esNumero(entrada, valor))
        {
            if ((datos.operador == OP_DIV || datos.operador == OP_RESIDUO) && valor == 0.0)
            { cout << "El divisor no puede ser cero.\n"; continue; }
            datos.operando2 = valor;
            break;
        }
        cout << "\"" << entrada << "\" no es un numero valido.\n";
    }
    return datos;
}
string nombreOperacion(char op)
{
    switch (op)
    {
        case OP_SUMA: return "Suma";
        case OP_RESTA: return "Resta";
        case OP_MULT_MAY: return "Multiplicacion";
        case OP_DIV: return "Division";
        case OP_RESIDUO: return "Residuo";
        case OP_POTENCIA: return "Potencia";
        default: return "Desconocida";
    }
}
double calcular(const Operacion& op)
{
    switch (op.operador)
    {
        case OP_SUMA: return op.operando1 + op.operando2;
        case OP_RESTA: return op.operando1 - op.operando2;
        case OP_MULT_MAY: return op.operando1 * op.operando2;
        case OP_DIV: return op.operando1 / op.operando2;
        case OP_RESIDUO: return fmod(op.operando1, op.operando2);
        case OP_POTENCIA: return pow(op.operando1, op.operando2);
        default:
            cerr << "Error interno: operador no manejado.\n";
            exit(EXIT_FAILURE);
    }
}
void imprimirResultado(const Operacion& op, double resultado)
{
    bool esEntero = (resultado == static_cast<long long>(resultado) && op.operador != OP_DIV);
    ostringstream expr;
    expr << op.operando1 << " " << op.operador << " " << op.operando2 << " = ";
    if (esEntero) expr << static_cast<long long>(resultado);
    else expr << fixed << setprecision(6) << resultado;
    cout << "Operacion: " << left << setw(29) << nombreOperacion(op.operador) << "\n";
    cout << "" << left << setw(42) << expr.str() << "\n";
}
int main()
{
    char continuar;
    do {
        Operacion operacion = pedirYValidarOperacion();
        double resultado = calcular(operacion);
        imprimirResultado(operacion, resultado);
        cout << "Desea realizar otra operacion? (s/n): ";
        cin  >> continuar;
        cout << "\n";
    } while (tolower(continuar) == 's');
    cout << "Hasta luego!\n\n";
    return EXIT_SUCCESS;
}