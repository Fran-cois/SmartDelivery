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
	ofstream exportgpio(exp.c_str()); // ofstream : classe adaptée I/O qui prend exp et renvoie sa valeur quand on va la changer
	exportgpio << this->gpion ; //on change exportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	exportgpio.close(); // on ferme le fichier export
	return 0;
}

int unexport_gpio()
{
	string imp = "/sys/class/gpio/unexport";// ouvrir le répértoire d'unexport comme dans le terminal et l'affecter a unexp
	ofstream unexportgpio(unexp.c_str()); // ofstream : classe adaptée I/O
	unexportgpio << this->gpion ; //on change unexportgpu du répértoire en gpion le numéro du pin du gpio qu'on veut exporter, comme dans le terminal
	unexportgpio.close(); // on ferme le fichier unexport
	return 0;
}

int setval_gpio(string val)
{

	string setval_str = "/sys/class/gpio/gpio" + this->gpion + "/value"; //ouvrir le répértoire de la valeur du gpio, syntaxe proche du java,que du c.
	ofstream setvalgpio(setval_str.c_str()); // ofstream : classe adaptée I/O
	setvalgpio << val ; //affecter la valeur val au fichier valeur gpio
	setvalgpio.close();// on ferme le fichier valeur
	return 0;
}

int getval_gpio(string& val){

	string getval_str = "/sys/class/gpio/gpio" + this->gpion + "/value";
	ifstream getvalgpio(getval_str.c_str());// ouvrir le répértoire de la valeur du gpio
	getvalgpio >> val ;  // lecture de la valeur du gpio
	if(val = "0")
		val = "0";
	else
		val = "1";
	getvalgpio.close(); //on ferme le fichier valeur
    return 0;
}
