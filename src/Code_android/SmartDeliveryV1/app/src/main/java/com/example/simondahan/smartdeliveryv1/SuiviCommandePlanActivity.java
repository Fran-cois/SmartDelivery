package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by SimonDahan on 28/01/2017.
 */

public class SuiviCommandePlanActivity extends AppCompatActivity {

    private static final String TAG = "SuiviCommandePlanActivity";
    private TextView textUpdate;
    private final String PROGRESS_BAR_INCREMENT="ProgreesBarIncrementId";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress=msg.getData().getInt(PROGRESS_BAR_INCREMENT);
            textUpdate.setText(String.valueOf(KnockKnockClient.localisation));
        }
    };
    /**     * L'AtomicBoolean qui gère la destruction de la Thread de background     */
    AtomicBoolean isRunning = new AtomicBoolean(false);
    /**     * L'AtomicBoolean qui gère la mise en pause de la Thread de background     */
    AtomicBoolean isPausing = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suivi_commande_plan);
        Intent intent2 = getIntent();
        textUpdate = (TextView) findViewById(R.id.textUpdate);
        this.getSupportActionBar().setTitle("Plan des lieux");
        // récupération de l'intent pour le bouton de Confirmation_drone
        Intent myIntent5 = getIntent();



    }
    public void onStart() {
        super.onStart();
        Thread background = new Thread(new Runnable() {
            /**
             * Le Bundle qui porte les données du Message et sera transmis au Handler
             */
            Bundle messageBundle=new Bundle();
            /**
             * Le message échangé entre la Thread et le Handler
             */
            Message myMessage;
            // Surcharge de la méthode run
            public void run() {
                try {
                    // Si isRunning est à false, la méthode run doit s'arrêter
                    for (int i = 0; i < 20 && isRunning.get(); i++) {
                        // Si l'activité est en pause mais pas morte
                        while (isPausing.get() && (isRunning.get())) {
                            // Faire une pause ou un truc qui soulage le CPU (dépend du traitement)
                            Thread.sleep(2000);
                        }
                        // Effectuer le traitement, pour l'exemple je dors une seconde
                        Thread.sleep(1000);
                        // Envoyer le message au Handler (la méthode handler.obtainMessage est plus efficace
                        // que créer un message à partir de rien, optimisation du pool de message du Handler)
                        //Instanciation du message (la bonne méthode):
                        myMessage=handler.obtainMessage();
                        //Ajouter des données à transmettre au Handler via le Bundle
                        messageBundle.putInt(PROGRESS_BAR_INCREMENT, 1);
                        //Ajouter le Bundle au message
                        myMessage.setData(messageBundle);
                        //Envoyer le message
                        handler.sendMessage(myMessage);
                    }
                } catch (Throwable t) {
                    // gérer l'exception et arrêter le traitement
                }
            }
        });
        //Initialisation des AtomicBooleans
        isRunning.set(true);
        isPausing.set(false);
        //Lancement de la Thread
        background.start();
    }

    /************************************************************************************/
/** Gestion du cycle de vie *******************************************************************/
    /**************************************************************************************/
    //Méthode appelée quand l'activité s'arrête
    public void onStop() {
        super.onStop();
        //Mise-à-jour du booléen pour détruire la Thread de background
        isRunning.set(false);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Mise-à-jour du booléen pour mettre en pause la Thread de background
        isPausing.set(true);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Mise-à-jour du booléen pour relancer la Thread de background
        isPausing.set(false);
    }

}