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
	string exp = "/sys/class/gpio/export";// ouvrir le répértoire d'export comme dans le terminal et l'affecter a exp
	ofstream exportgpio(exp.c_str()); // ofstream : classe adaptée I/O qui prend exp et renvoie sa valeur quand on va la changer
	exportgpio << this->gpion ; //on change exportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	exportgpio.close(); // on ferme le fichier export
	return 0;
}

int GPIOClass::unexport_gpio()
{
	string imp = "/sys/class/gpio/unexport";// ouvrir le répértoire d'unexport comme dans le terminal et l'affecter a unexp
	ofstream unexportgpio(unexp.c_str()); // ofstream : classe adaptée I/O
	unexportgpio << this->gpion ; //on change unexportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	unexportgpio.close(); // on ferme le fichier unexport
	return 0;
}
