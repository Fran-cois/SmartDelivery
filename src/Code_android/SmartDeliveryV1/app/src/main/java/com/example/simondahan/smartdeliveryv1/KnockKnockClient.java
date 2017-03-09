package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



import static com.example.simondahan.smartdeliveryv1.ChoixSalleActivity.*;

/**
 * Created by benja on 26/02/2017.
 */

public class KnockKnockClient extends AsyncTask<Object, Object, Boolean> {

        private String[] args;
        private Activity mActivity;


        public KnockKnockClient (String[] args, Activity mActivity) {
            this.args = args;       //contains the ip address
            this.mActivity = mActivity;          //necessary to start the intent when the drone arrived
        }

        @Override
        protected Boolean doInBackground(Object... arg) {

            Log.i("Client", "doInBackground");
            int period =2*1000;
            Socket kkSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            if (args.length != 2) {
                Log.e("Client",""+args.length);
                Log.e("Client","Usage: java EchoClient <host name> <port number>");
                System.exit(1);

            }

            String hostName = (String) args[0];
            Log.i("Client","hostame "+hostName);
            int portNumber = Integer.parseInt(args[1]);
            Log.i("Client","portNumber "+portNumber);

            try {
                kkSocket = new Socket(hostName, portNumber);
                Log.i("Client", "Le socket est cree");
                out = new PrintWriter(kkSocket.getOutputStream(), true);
                Log.i("Client", "Le printwriter est cree");
                in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));

            }
            catch (UnknownHostException e) {
                Log.w("Client","Don't know about host "+ hostName);
                System.exit(1);
            } catch (IOException e) {
                Log.w("Client","Couldn't get I/O for the connection to " + hostName);
                System.exit(1);
            }

                    Log.i("Client", "doInBackground");
                    String fromServer;

            try {
                while ((fromServer = in.readLine()) != null) {
                    Log.i("Client","Debut du while");
                    if (fromServer.equals("I'm listening.")){
                        out.println("I would like to make a delivery. Is the drone available?");
                        Log.i("Client","Server: " + fromServer);
                        Log.i("Client","Client: I would like to make a delivery. Is the drone available?");
                    }
                    if(fromServer.equals("try again")){
                        Thread.sleep(period);   // Le client patiente avant de demander une autre connection.
                        out.println("I would like to make a delivery. Is the drone available?");
                        Log.w("Client","Server: " + fromServer);
                        Log.i("Client","Client: I would like to make a delivery. Is the drone available?");
                    }
                    if (fromServer.equals("yes")){
                        ProtocolClient.available=true;
                        out.println("go to the room "+ getFinalRoom()); //Application android
                        Log.i("Client","Server: " + fromServer);
                        Log.i("Client","Client: go to the room "+getFinalRoom());
                    }
                    if(fromServer.equals("no")){
                        Thread.sleep(period);   // Le client patiente avant de demander une autre livraison
                        out.println("I would like to make a delivery. Is the drone available?");
                        Log.i("Client","Server: " + fromServer);
                        Log.i("Client","Client: I would like to make a delivery. Is the drone available?");
                    }
                    if(fromServer.equals("I didn't get the room. Where do you want me to go?")){
                        Thread.sleep(period);   // Le client patiente avant de redemander la salle.
                        out.println("go to the room "+ getFinalRoom()); //Application android
                        Log.w("Client","Server: " + fromServer);
                        Log.i("Client","Client: go to the room "+getFinalRoom());
                    }
                    Log.i("Client","fromServer egale : "+fromServer);
                    if (fromServer.equals("I'm going.")||fromServer.contains("I'm in the room ") ||fromServer.contains("I didn't get your question. Can you repeat?")){
                        try {
                            Thread.sleep(period);   // Le client patiente avant de demander où le drone est.
                            out.println("where are you?");
                            if(fromServer.contains("I'm in the room ")){
                                ProtocolClient.localisation=fromServer.substring(16);
                                Log.i("Client","Server: " + fromServer);
                                Log.i("Client","localisation "+ProtocolClient.localisation);
                            }
                            if (fromServer.contains("I didn't get your question. Can you repeat?")){
                                Log.w("Client","Server: " + fromServer);
                                Log.i("Client","Client: where are you?");
                            }
                            else{
                                Log.i("Client","Server: " + fromServer);
                                Log.i("Client","Client: where are you?");}
                        } catch (InterruptedException e) {
                            Log.w("Client","erreur try line 101");
                        }
                    }
                    if (fromServer.equals("Bye. I arrived.")){
                        Log.i("Client","Server: " + fromServer);
                        break;}
                }
            } catch (IOException e) {
                Log.w("Client","Impossible de lire les lignes");
            } catch (InterruptedException e) {
                Log.w("Client","Thread sleep ne s'execute pas.");
            }
            try {
                kkSocket.close();
            } catch (IOException e) {
                Log.w("Client","Impossible de fermer le socket");
            }

            ProtocolClient.available = false;  //we initialize the values
        return null;
        }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Intent myIntent6= new Intent(mActivity, DroneArriveActivity.class);
        Log.i("Client","Lancement de l'activité quand le drone est arrivé" );
        mActivity.startActivity(myIntent6);
        super.onPostExecute(aBoolean);
    }
}




