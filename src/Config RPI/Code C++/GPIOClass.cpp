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

int export_gpio() // exporter la gpio créé afin d'éviter des problèmes de sudo (superutilisateur)
{
	string exp = "/sys/class/gpio/export";// ouvrir le répértoire d'export comme dans le terminal et l'affecter a exp
	ofstream expgpio(exp.c_str()); // ofstream : classe adaptée I/O qui prend exp et renvoie sa valeur quand on va la changer .c_str pour convertir le string c++ en string c par souci de compatibilité Linux
	expgpio << this->gpion ; //on change exportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	expgpio.close(); // on ferme le fichier export
	return 0;
}

int unexport_gpio()
{
	string unexp = "/sys/class/gpio/unexport";// ouvrir le répértoire d'unexport comme dans le terminal et l'affecter a unexp
	ofstream unexpgpio(unexp.c_str()); // ofstream : classe adaptée I/O .c_str pour convertir le string c++ en string c par souci de compatibilité Linux
	unexpgpio << this->gpion ; //on change unexportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	unexpgpio.close(); // on ferme le fichier unexport
	return 0;
}

int set_val(string val)
{

	string setval = "/sys/class/gpio/gpio" + this->gpion + "/value"; //ouvrir le répértoire de la valeur du gpio, syntaxe proche du java,que du c.
	ofstream setgpio(setval.c_str()); // ofstream : classe adaptée I/O .c_str pour convertir le string c++ en string c par souci de compatibilité Linux
	setgpio << val ; //affecter la valeur val au fichier valeur gpio
	setgpio.close();// on ferme le fichier valeur
	return 0;
}

int get_val(string& val)
{

	string getval = "/sys/class/gpio/gpio" + this->gpion + "/value";
	ifstream getgpio(getval.c_str());// ouvrir le répértoire de la valeur du gpio .c_str pour convertir le string c++ en string c par souci de compatibilité Linux
	getgpio >> val ;  // lecture de la valeur du gpio
	if(val = "0") //soit la valeur du gpio est nulle (0V) ou non nulle (3.3V), on effectue le test:
		val = "0";
	else
		val = "1";
	getgpio.close(); //on ferme le fichier valeur
    return 0;
}

string get_gpion()
{
	return this->gpion;
}

int set_dir(string dir)
{

	string setdir ="/sys/class/gpio/gpio" + this->gpionum + "/direction";
	ofstream setdirgpio(setdir.c_str()); // ouvrir le répértoire de la direction du gpio .c_str pour convertir le string c++ en string c par souci de compatibilité Linux
	setdirgpio << dir; // affecter la direction dir au fichier direction gpio
	setdirgpio.close(); // on ferme le fichier direction
	return 0;
}
