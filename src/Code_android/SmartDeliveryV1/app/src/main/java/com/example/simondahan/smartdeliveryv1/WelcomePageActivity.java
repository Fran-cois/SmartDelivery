package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.io.*;
import java.net.*;
import java.util.logging.*;

public class WelcomePageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        // Création de l'intent pour le bouton1
        Button mybutton1 = (Button) findViewById(R.id.button1);
        mybutton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntend1 = new Intent(WelcomePageActivity.this, PlanPageActivity.class);
                startActivity(myIntend1);
                WelcomePageActivity.this.finish();
                }

        });
    }
    public class KnockKnockClient extends AsyncTask<String,Void,Void>{
        public static Logger logger = Logger.getLogger(KnockKnockClient.class.getName());
         static Handler fh;

        @Override
        protected Void doInBackground(String... args) {
            try {
                fh= new FileHandler("logfileClientServer_%u.log");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            logger.addHandler(fh);
            SimpleFormatter formatter=new SimpleFormatter();
            fh.setFormatter(formatter);
            int period =2*1000;

            if (args.length != 2) {
                logger.severe("Usage: java EchoClient <host name> <port number>");
                System.exit(1);
            }

            String hostName = args[0];
            int portNumber = Integer.parseInt(args[1]);

            try (
                    Socket kkSocket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(kkSocket.getInputStream()));

            ) {

                String fromServer;

                while ((fromServer = in.readLine()) != null) {
                    if (fromServer.equals("I'm listening.")){
                        out.println("I would like to make a delivery. Is the drone available?");
                        logger.info("Server: " + fromServer);
                        logger.info("Client: I would like to make a delivery. Is the drone available?");
                    }
                    if(fromServer.equals("try again")){
                        out.println("I would like to make a delivery. Is the drone available?");
                        logger.warning("Server: " + fromServer);
                        logger.info("Client: I would like to make a delivery. Is the drone available?");
                    }
                    if (fromServer.equals("yes")){
                        ProtocolClient.available=true;
                        out.println("go to the room "+View.getFinalRoom()); //Application android
                        logger.info("Server: " + fromServer);
                        logger.info("Client: go to the room "+View.getFinalRoom());
                    }
                    if(fromServer.equals("no")){
                        out.println("I would like to make a delivery. Is the drone available?");
                        logger.info("Server: " + fromServer);
                        logger.info("Client: I would like to make a delivery. Is the drone available?");
                    }
                    if(fromServer.equals("I didn't get the room. Where do you want me to go?")){
                        out.println(""+View.getFinalRoom()); //Application android
                        logger.warning("Server: " + fromServer);
                        logger.info("Client: go to the room "+View.getFinalRoom());
                    }
                    if (fromServer.equals("I'm going.")||fromServer.contains("je suis � la salle") ||fromServer.contains("I didn't get your question. Can you repeat?")){
                        try {
                            Thread.sleep(period);   // Le client patiente avant de demander o� le drone est.
                            out.println("where are you?");
                            if(fromServer.contains("je suis � la salle ")){
                                ProtocolClient.localisation=fromServer.substring(19);
                                logger.info("Server: " + fromServer);
                                logger.info("localisation "+ProtocolClient.localisation);
                            }
                            if (fromServer.contains("I didn't get your question. Can you repeat?")){
                                logger.warning("Server: " + fromServer);
                                logger.info("Client: where are you?");
                            }
                            else{
                                logger.info("Server: " + fromServer);
                                logger.info("Client: where are you?");}
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fromServer.equals("Bye. I arrived.")){
                        logger.info("Server: " + fromServer);
                        break;}
                }
                kkSocket.close();
            } catch (UnknownHostException e) {
                logger.severe("Don't know about host "+ hostName);
                System.exit(1);
            } catch (IOException e) {
                logger.severe("Couldn't get I/O for the connection to " + hostName);
                System.exit(1);
            }
        }
    }

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
                finalRoom=""+View.getFinalRoom();
                if(theInput.length()==3){
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
                        theOutput="je suis à la salle "+localisation;
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

}