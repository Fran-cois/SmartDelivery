package com.example.simondahan.smartdeliveryv1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.example.simondahan.smartdeliveryv1.ChoixSalleActivity.*;

/**
 * Created by benja on 26/02/2017.
 */

public class KnockKnockClient extends AsyncTask<Object, Object, Boolean> {

        private boolean isDroneAvailable = false;
        private WeakReference<AppCompatActivity> mActivity = null;
        public static Logger logger = Logger.getLogger(KnockKnockClient.class.getName());
        static Handler fh;

        public boolean getIsDroneAvailable() {
            return isDroneAvailable;
        }
        // We always keep a reference to the current activity
        public KnockKnockClient (AppCompatActivity activity) {
            mActivity = new WeakReference<AppCompatActivity>(activity);
        }

        //when an intent changes the current activity,
        //it changes the reference to the new activity onResume
        public void changeActivity(AppCompatActivity activity) {
            mActivity = new WeakReference<AppCompatActivity>(activity);
        }

        @Override
        protected Boolean doInBackground(Object... args) {
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

            Object hostName = args[0];
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
                        isDroneAvailable = true;
                        out.println("go to the room "+ getFinalRoom()); //Application android
                        logger.info("Server: " + fromServer);
                        logger.info("Client: go to the room "+getFinalRoom());
                    }
                    if(fromServer.equals("no")){
                        out.println("I would like to make a delivery. Is the drone available?");
                        logger.info("Server: " + fromServer);
                        logger.info("Client: I would like to make a delivery. Is the drone available?");
                    }
                    if(fromServer.equals("I didn't get the room. Where do you want me to go?")){
                        out.println(""+ getFinalRoom()); //Application android
                        logger.warning("Server: " + fromServer);
                        logger.info("Client: go to the room "+getFinalRoom());
                    }
                    if (fromServer.equals("I'm going.")||fromServer.contains("je suis à la salle") ||fromServer.contains("I didn't get your question. Can you repeat?")){
                        try {
                            Thread.sleep(period);   // Le client patiente avant de demander où le drone est.
                            out.println("where are you?");
                            if(fromServer.contains("je suis à la salle ")){
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
        return null;
        }

    }




