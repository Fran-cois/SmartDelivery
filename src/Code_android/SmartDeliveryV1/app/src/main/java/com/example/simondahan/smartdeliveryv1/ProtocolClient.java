package com.example.simondahan.smartdeliveryv1;

import android.util.Log;

import static com.example.simondahan.smartdeliveryv1.ChoixSalleActivity.getFinalRoom;

/**
 * Created by benja on 26/02/2017.
 */

public class ProtocolClient {
    private static final int WAITING = 3;
    private static final int START = 0;
    private static final int DEPARTURE = 1;
    private static final int INFLIGHT = 2;
    //private static final int period=2*1000;
    public static String localisation;
    private String finalRoom;
    public static boolean available=false;

    private int state = WAITING;


    public String processInput(String theInput) {
        String theOutput = null;
        if (state == WAITING) {
            theOutput = "I'm listening.";
            state = START;
        } else if (state == START) {
            if(theInput.equals( "I would like to make a delivery. Is the drone available?")){
                /**if (){
                 theOutput="yes";
                 state = DEPARTURE;
                 }
                 else
                 { theOutput="no";
                 state=WAITING;
                 }*/
                state=DEPARTURE;
            }
            else{
                theOutput="try again";
                state=WAITING;
            }
        } else if (state == DEPARTURE) {
            finalRoom=""+ getFinalRoom();
            Log.i("Protocole", "Client: "+theInput);
            if(theInput.length()<=21){
                theOutput="I'm going.";
                state = INFLIGHT;}
            else{
                theOutput="I didn't get the room. Where do you want me to go?";
                state=DEPARTURE;
            }
        } else if (state == INFLIGHT) {
            if(theInput.equals("where are you?")){
                if(finalRoom.equals(localisation)) {
                    theOutput = "Bye. I arrived.";
                    state = START;
                }
                else{
                    theOutput="I'm in the room "+localisation;
                    state=INFLIGHT;
                }
            }
            else{
                theOutput= "I didn't get your question. Can you repeat?";
                state=INFLIGHT;
            }
        }
        return theOutput;
    }
}
