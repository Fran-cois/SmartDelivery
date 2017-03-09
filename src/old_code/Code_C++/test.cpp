#include<stdio.h> 
#include<stdlib.h>
#include<string.h>
#include<Cserial.h> 
bool deplacer_antenne(int angle)
{
    int n;
    CSerial arduinoport;
    if (arduinoport.Open(3, 9600))
        {
            static char* szMessage;
            n= sprintf ( szMessage, "%u", angle );
            cout<<"Port opened successfully";
            int nBytesSent = arduinoport.SendData(szMessage, strlen(szMessage));
 
        }
    else
        cout<<"Failed to open port!";
}
