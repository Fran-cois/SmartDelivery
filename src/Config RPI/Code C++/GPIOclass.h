#include <string>
/* GPIO Class
 * n : pin number must be passed to the class's constructor
 */
class GPIOClass
{
public:
    GPIOClass(string n); // Makes a GPIO object to control the gpio n
    int export_gpio(); // exports GPIO
    int unexport_gpio(); // unexport GPIO
    int setval_gpio(string val); // Set GPIO Value
    int getval_gpio(string val); // Get GPIO Value
};
