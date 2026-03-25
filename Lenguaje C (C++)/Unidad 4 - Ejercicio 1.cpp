#include <iostream>
#include <vector>
#include <string>
#include <iomanip>
using namespace std;

struct Bebida {
    string nombreBebida;
    double precioBebida;
    int cantidadEnMaquina;
};
int main() {
    vector<Bebida> maqn = {
        {"Botella de Agua", 20.00, 20},
        {"Coca Cola",       25.00, 20},
        {"Seven Up",        25.00, 20},
        {"Mountain Dew",    30.00, 20},
        {"Jugo V8",         50.00, 20}
    };
    double totlGand = 0.0;
    int opcn;
    cout << fixed << setprecision(2);
    while (true) {
        cout << "\n========================================\n";
        cout << "       MAQUINA DE VENTA DE BEBIDAS      \n";
        cout << "========================================\n";
        cout << left << setw(4)  << "No."
             << setw(20) << "Bebida"
             << setw(10) << "Precio"
             << "Disponibles\n";
        cout << "----------------------------------------\n";
        for (int i = 0; i < (int)maqn.size(); i++) {
            cout << setw(4)  << (i + 1)
                 << setw(20) << maqn[i].nombreBebida
                 << "$" << setw(9) << maqn[i].precioBebida
                 << maqn[i].cantidadEnMaquina << "\n";
        }
        cout << "----------------------------------------\n";
        cout << "0. Salir\n";
        cout << "Elige una opcion: ";
        cin >> opcn;
        if (opcn == 0) {
            break;
        }
        if (opcn < 1 || opcn > (int)maqn.size()) {
            cout << "Opcion invalida! Intente de nuevo.\n";
            continue;
        }
        int idx = opcn - 1;
        if (maqn[idx].cantidadEnMaquina == 0) {
            cout << "\nLo sentimos, la bebida \""
                 << maqn[idx].nombreBebida
                 << "\" esta agotada.\n";
            continue;
        }
        double montInsrtd;
        while (true) {
            cout << "Precio de \"" << maqn[idx].nombreBebida
                 << "\": $" << maqn[idx].precioBebida << "\n";
            cout << "Inserte su dinero: $";
            cin >> montInsrtd;
            if (montInsrtd < 0) {
                cout << "Error: No se aceptan valores negativos.\n";
            } else if (montInsrtd < 20.00) {
                cout << "Error: El monto minimo aceptado es $20.00.\n";
            } else if (montInsrtd < maqn[idx].precioBebida) {
                cout << "Error: Monto insuficiente para esta bebida. "
                     << "Necesita al menos $"
                     << maqn[idx].precioBebida << ".\n";
            } else {
                break;
            }
        }
        double cambio = montInsrtd - maqn[idx].precioBebida;
        maqn[idx].cantidadEnMaquina--;
        totlGand += maqn[idx].precioBebida;
        cout << "\n>>> Dispensando: " << maqn[idx].nombreBebida << "\n";
        cout << ">>> Cambio a devolver: $" << cambio << "\n";
    }
    cout << "\n========================================\n";
    cout << "  REPORTE FINAL DE LA MAQUINA\n";
    cout << "========================================\n";
    cout << "Total ganado por la maquina: $" << totlGand << "\n";
    cout << "Gracias por usar la maquina de bebidas!\n";
    return 0;
}