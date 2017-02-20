// squelette des fonctions
/*****************************************************************************
Libraries imported
*****************************************************************************/

/*****************************************************************************
Global Variables
*****************************************************************************/
#define SPEED 100


/****************************************************************************
Classes defined
*****************************************************************************/



/****************************************************************************
Prototypes of this file's Functions (to remove if .h created)
*****************************************************************************/
int initDrone(int height);
int landDrone(int Urgent);
int moveDrone(int distance, String direction,float angle);
String getTarget (String File);
String getPosition (String File);
int lookForStripeIntoArea(int Time, int Height, String File);
int getShift(String File);

/****************************************************************************
Main Function
*****************************************************************************/
int main(){
/*
  input:
  output:
  quick description :
  */

}


/****************************************************************************
Others functions
*****************************************************************************/


int initDrone(int height){
  /*
    input: height
    output: bool 0 or 1
    quick description : This function initiates the drone at a specified height
    */
    return 0;
}

int landDrone(int Urgent){
  /*
    input: integer Urgent
    output: bool 0 or 1
    quick description : This function lands the drone. If it is in a critical
                        situation this function will cut the motors.
    */
    return 0;
}
int moveDrone(int distance, String direction,float angle){
  /*
    input: integer distance, String direction,float angle
    output: bool 0 or 1
    quick description : this function orders the drone to move
                        of "distance" in a specified unit at a specified
                        direction and angle (the angle must be < pi/2).
    */
   angle = angle/2
    return 0;
}

String getTarget (String File){
/*
  input: string File
  output: The Target which was written in a file
  quick description : This function reads in a file the name of the target.
                      This allows to compare the name of the target and the
                      drone position.
  */
  return NULL;

}
String getPosition (String File ){
/*
  input: string File
  output: The Position which was written in a file
  quick description :  This function reads in a file the name of the position.
                      This allows to compare the name of the target and the
                      drone position.
  */
  return NULL;
}

int lookForStripeIntoArea(int Time, int Height, String File){
/*
  input: Integer Time and Height, String File
  output: bool 0 or 1
  quick description : This function moves the drone for "Time" seconds at the
                      Height desired until the input File associate to the
                      String says that a stripe has been detected.
                      If after "Time" seconds a stripe has not been detected,
                      The drone lands and the function return 0.
                      else , the drone follow the stripe
                      (the function returns 1).
  */

  int getShift(String File){
  /*
    input: String File
    output: distance of the shift of the drone to the stripe
    quick description : This function gives the shift between the drone position
                        and the stripe (supposed to be on the floor).
    */

  }


}


/****************************************************************************
Tests functions
*****************************************************************************/


int TestinitDrone(int height){}
int TestlandDrone(int Urgent){}
int TestmoveDrone(int distance, String direction,float angle){}
String TestgetTarget (String File){}
String TestgetPosition (String File){}
int TestlLookForStripeIntoArea(int Time, int Height, String File){}
int TestgetShift(String File){

}
