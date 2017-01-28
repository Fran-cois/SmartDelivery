#include <string>
/* GPIO Class
 * n : pin number must be passed to the class's constructor
 */
using namespace std; // Pour avoir GPIOClass:: avant les méthodes (je ne comprends pas trop l'utilité mais c'est utilisé dans tous les progs c++ donc bon)


class GPIOClass // déclaration de la classe GPIO
{
public:
    GPIOClass(string n); // Constructeur : Crée un objet de la classe GPIO de numéro n
    int export_gpio(); // exporter le GPIO
    int unexport_gpio(); // unexporter le GPIO
    int setval_gpio(string val); // mettre la valeur du GPIO
    int getval_gpio(string val); // lire la valeur du GPIO
};
