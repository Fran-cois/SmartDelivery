#include <fstream>
#include <string>
#include <iostream>
#include <sstream>
#include "GPIOClass.h"


using namespace std; // Pour avoir GPIOClass:: avant les méthodes (je ne comprends pas trop l'utilité mais c'est utilisé dans tous les progs c++ donc bon)

GPIOClass()
{
	this->gpion = "1"; // valeur par défaut du numéro du gpio
}

GPIOClass(string n)
{
	this->gpion = n;  // On initialise l'objet GPIOClass à la valeur du pin n
}

int GPIOClass::export_gpio()
{
	string export_str = "/sys/class/gpio/export";// ouvrir le répértoire d'export comme dans le terminal
	if (exportgpio < 0){
		cout << " OPERATION FAILED: Unable to export GPIO"<< this->gpionum <<" ."<< endl;
		return -1;
	}

	exportgpio << this->gpion ; //on change exportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
    exportgpio.close(); // on ferme le fichier export
    return 0;
}
