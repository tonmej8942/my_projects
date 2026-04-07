#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <iomanip>
using namespace std;
const char PIEDRA = 'P';
const char PAPEL  = 'A';
const char TIJERA = 'T';
const char SALIR  = 'S';
void mostrarMenu() {
    cout << "PIEDRA, PAPEL O TIJERA \n";
    cout << "P | Piedra \n";
    cout << "A | Papel  \n";
    cout << "T | Tijera \n";
    cout << "S | Salir  \n";
    cout << "Opcion: ";
}
string nombreOpcion(char opcion)
{
    switch (opcion)
    {
        case PIEDRA: return "Piedra";
        case PAPEL:  return "Papel";
        case TIJERA: return "Tijera";
        default:     return "Desconocido";
    }
}
char opcionComputador()
{
    int aleatorio = rand() % 3;
    switch (aleatorio)
    {
        case 0:  return PIEDRA;
        case 1:  return PAPEL;
        default: return TIJERA;
    }
}
string determinarResultado(char usuario, char computador)
{
    if (usuario == computador)
        return "Empate... Ambos eligieron " + nombreOpcion(usuario) + ".";
    if ((usuario == PIEDRA && computador == TIJERA) ||
        (usuario == PAPEL  && computador == PIEDRA) ||
        (usuario == TIJERA && computador == PAPEL))
    {
        return "Ganaste... " + nombreOpcion(usuario) +
               " vence a " + nombreOpcion(computador) + ".";
    }
    return "Perdiste... " + nombreOpcion(computador) +
           " vence a " + nombreOpcion(usuario) + ".";
}
int main() {
    srand(static_cast<unsigned int>(time(nullptr)));
    char opcionUsuario;
    int  victorias = 0, derrotas = 0, empates = 0;
    do
    {
        mostrarMenu();
        cin >> opcionUsuario;
        opcionUsuario = toupper(opcionUsuario);
        if (opcionUsuario != PIEDRA &&
            opcionUsuario != PAPEL  &&
            opcionUsuario != TIJERA &&
            opcionUsuario != SALIR)
        {
            cout << "Opcion invalida. Por favor ingrese P, A, T o S.\n";
            continue;
        }
        if (opcionUsuario == SALIR)
            break;
        char compOpcion = opcionComputador();
        cout << "Tu elegiste: " << left << setw(20)
             << nombreOpcion(opcionUsuario) << "\n";
        cout << "PC eligio: " << setw(20)
             << nombreOpcion(compOpcion)   << "\n";
        string resultado = determinarResultado(opcionUsuario, compOpcion);
        cout << "" << left << setw(38) << resultado << "\n";
        if (resultado.find("Ganaste") != string::npos) victorias++;
        else if (resultado.find("Perdiste") != string::npos) derrotas++;
        else empates++;
    } while (opcionUsuario != SALIR);
    cout << "MARCADOR FINAL \n";
    cout << "Victorias: " /*<< setw(13) */<< victorias << "\n";
    cout << "Derrotas: " /*<< setw(13) */<< derrotas << "\n";
    cout << "Empates: " /*<< setw(13) */<< empates << "\n";
    cout << "Hasta la proxima!\n\n";
    return 0;
}