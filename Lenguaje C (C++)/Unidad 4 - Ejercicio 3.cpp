#include <iostream>
#include <iomanip>
#include <climits>
#include <string>
using namespace std;

int main() {
    const int NUM_MONOS = 3;
    const int NUM_DIAS  = 7;
    string diasSemana[NUM_DIAS] = {"Lunes", "Martes", "Miercoles",
                                    "Jueves", "Viernes", "Sabado", "Domingo"};

    double alimento[NUM_MONOS][NUM_DIAS];
    cout << fixed << setprecision(2);
    cout << "=============================================\n";
    cout << "    REGISTRO DE ALIMENTO - ZOOLOGICO        \n";
    cout << "=============================================\n";
    for (int mono = 0; mono < NUM_MONOS; mono++) {
        cout << "\n--- Mono #" << (mono + 1) << " ---\n";
        for (int dia = 0; dia < NUM_DIAS; dia++) {
            while (true) {
                cout << "  Libras de comida el " << diasSemana[dia] << ": ";
                cin >> alimento[mono][dia];
                if (alimento[mono][dia] < 0) {
                    cout << "  Error: No se aceptan valores negativos.\n";
                } else {
                    break;
                }
            }
        }
    }
    double promedioPorDia[NUM_DIAS] = {0};
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        double suma = 0;
        for (int mono = 0; mono < NUM_MONOS; mono++) {
            suma += alimento[mono][dia];
        }
        promedioPorDia[dia] = suma / NUM_MONOS;
    }
    double minComida = alimento[0][0];
    double maxComida = alimento[0][0];
    for (int mono = 0; mono < NUM_MONOS; mono++) {
        for (int dia = 0; dia < NUM_DIAS; dia++) {
            if (alimento[mono][dia] < minComida) minComida = alimento[mono][dia];
            if (alimento[mono][dia] > maxComida) maxComida = alimento[mono][dia];
        }
    }
    cout << "\n=============================================\n";
    cout << "              REPORTE SEMANAL               \n";
    cout << "=============================================\n";
    cout << left << setw(14) << "Dia";
    for (int mono = 0; mono < NUM_MONOS; mono++) {
        cout << setw(12) << ("Mono " + to_string(mono + 1));
    }
    cout << "Promedio\n";
    cout << "---------------------------------------------\n";
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        cout << setw(14) << diasSemana[dia];
        for (int mono = 0; mono < NUM_MONOS; mono++) {
            cout << setw(12) << alimento[mono][dia];
        }
        cout << promedioPorDia[dia] << " lbs\n";
    }
    cout << "---------------------------------------------\n";
    cout << "\nResumen general:\n";
    cout << "  Promedio de comida por dia (familia completa):\n";
    for (int dia = 0; dia < NUM_DIAS; dia++) {
        cout << "    " << left << setw(12) << diasSemana[dia]
             << ": " << promedioPorDia[dia] << " lbs\n";
    }
    cout << "\n  Minima cantidad ingerida en la semana: "
         << minComida << " lbs\n";
    cout << "  Maxima cantidad ingerida en la semana: "
         << maxComida << " lbs\n";
    cout << "=============================================\n";
    return 0;
}