package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by SimonDahan on 28/01/2017.
 */

public class SuiviCommandePlanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suivi_commande_plan);

        // récupération de l'intent pour le bouton de Confirmation_drone
        Intent myIntent5 = getIntent();
        setContentView(R.layout.suivi_commande_plan);

        //Ajout d'un bouton (temporaire) pour passer à la page arrivée
        Button mybuttoncarte = (Button) findViewById(R.id.button6);
        mybuttoncarte.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){


                Intent myIntent6= new Intent(SuiviCommandePlanActivity.this,DroneArriveActivity.class);
                startActivity(myIntent6);
                SuiviCommandePlanActivity.this.finish();
            }


        });



    }
}
