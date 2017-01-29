#include <string>
using namespace std; // Pour avoir GPIOClass:: avant les méthodes (je ne comprends pas trop l'utilité mais c'est utilisé dans tous les progs c++ donc bon)

/* GPIO Class
 * n : le numéro du pin, pris en argument par le constructeur de la classe GPIO
 */
class GPIOClass // déclaration de la classe GPIO
{
public:
    GPIOClass(string n); // Constructeur : Crée un objet de la classe GPIO de numéro n
    int export_gpio(); // exporter le GPIO
    int unexport_gpio(); // unexporter le GPIO
    int set_val(string val); // mettre la valeur du GPIO (ne marchera que pour les outputs => nécessité d'ajouter des méthodes pour savoir l'orientation du GPIO)
    int get_val(string& val); // lire la valeur du GPIO
    string get_num(); // Lire le numéro n du GPIO
    int set_dir(string dir); // Configurer la direction du GPIO dir="out" pour output & dir="in" pour input
 };
