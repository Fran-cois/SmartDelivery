package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SimonDahan on 28/01/2017.
 */

public class SuiviCommandePlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suivi_commande_plan);

        // récupération de l'intent pour le bouton de Confirmation_drone
        Intent myIntent5 = getIntent();
        setContentView(R.layout.suivi_commande_plan);


    }
}