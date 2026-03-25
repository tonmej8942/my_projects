#include <iostream>
#include <vector>
#include <iomanip>
using namespace std;

int main() {
    vector<long>   empId        = {5658845, 4520125, 7895122, 8777541,
                                   8451277, 1302850, 7580489};
    vector<int>    horas        (7, 0);
    vector<double> tarifaPorHora(7, 0.0);
    vector<double> salarioBruto (7, 0.0);
    int numEmplds = (int)empId.size();
    cout << fixed << setprecision(2);
    cout << "=============================================\n";
    cout << "      SISTEMA DE NOMINA DE EMPLEADOS        \n";
    cout << "=============================================\n";
    for (int i = 0; i < numEmplds; i++) {
        cout << "\nEmpleado ID: " << empId[i] << "\n";
        while (true) {
            cout << "  Horas trabajadas: ";
            cin >> horas[i];
            if (horas[i] < 0) {
                cout << "  Error: Las horas no pueden ser negativas.\n";
            } else {
                break;
            }
        }
        while (true) {
            cout << "  Tarifa por hora ($): ";
            cin >> tarifaPorHora[i];
            if (tarifaPorHora[i] < 50.00) {
                cout << "  Error: La tarifa minima por hora es $50.00.\n";
            } else {
                break;
            }
        }
        salarioBruto[i] = horas[i] * tarifaPorHora[i];
    }
    cout << "\n=============================================\n";
    cout << "           REPORTE DE SALARIOS              \n";
    cout << "=============================================\n";
    cout << left
         << setw(15) << "ID Empleado"
         << setw(10) << "Horas"
         << setw(15) << "Tarifa/Hora"
         << "Salario Bruto\n";
    cout << "---------------------------------------------\n";
    for (int i = 0; i < numEmplds; i++) {
        cout << setw(15) << empId[i]
             << setw(10) << horas[i]
             << "$" << setw(14) << tarifaPorHora[i]
             << "$" << salarioBruto[i] << "\n";
    }
    cout << "=============================================\n";
    return 0;
}