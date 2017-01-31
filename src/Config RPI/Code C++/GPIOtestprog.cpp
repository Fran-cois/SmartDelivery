#include <string>
#include <sstream>
#include "GPIOClass.h"
#include "GPIOclass.cpp"
#include <fstream>
#include <iostream>


using namespace std;
int main (void)
{
  //variables locales pour la lecture des entrées utilisateur
  string gpio1
  string gpio2
  string dir1
  string dir2



  cin >> gpio1
  GPIOClass* gpio1 = new GPIOClass(gpio1);
  cin >> gpio2
  GPIOClass* gpio2 = new GPIOClass(gpio2); // Création de deux objets GPIO liés aux I/o 3 et 11

  cout << "Création des objet.." << endl;

  gpio1->export_gpio();
  gpio2->export_gpio();

  cout << "Export des gpios..." << endl;

  cout << "Entrez les directions successives des deux GPIOs" << endl;
  cin >> dir1
  gpio3->set_dir(dir1);
  cin >> dir2
  gpio11->set_dir(dir2);

  cout << "Directions réglées..." << endl;

  cout << "Entrez les valeurs des GPIOs" << endl;

  cin >> val1
  gpio3->set_val(val1);
  cin >> dir2
  gpio11->set_val(val2);

  for (int i=0; i<10; ++i)
    {
          usleep(10000000);  // On la laisse allumée 10 secondes

          cout << "changez les valeurs successives des deux GPIOs" << endl;
          cin >> val1
          gpio3->set_val(val1);
          cin >> dir2
          gpio11->set_val(val2);
    }

    cout << "Unexport des GPIOs..." << endl;
    gpio1->unexport_gpio();
    gpio2->unexport_gpio();

    cout << "Fin du programme, sortie..." << endl;
    return 0

}
