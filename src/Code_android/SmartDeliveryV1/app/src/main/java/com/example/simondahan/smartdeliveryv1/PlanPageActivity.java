package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PlanPageActivity extends AppCompatActivity {

    private static final String TAG = "PlanPageActivity";
    private Intent myIntent2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_page);

        this.getSupportActionBar().setTitle("Plan des lieux");
        // récupération de l'intent pour la commande du bouton depuis la page 1 ou la page 3
        Intent myIntent1 = getIntent();
        setContentView(R.layout.plan_page);

        // Création de l'intent pour le bouton2
        Button mybutton2 = (Button) findViewById(R.id.button2);
        Log.i(TAG, "Click sur le bouton suivi d'un intent.");
        mybutton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v2){
                if (ProtocolClient.available) {
                    Log.i(TAG, "Click sur le bouton, drone available.");
                    myIntent2 = new Intent(PlanPageActivity.this, ChoixSalleActivity.class);
                    startActivity(myIntent2);
                } else {
                    Log.i(TAG, "Click sur le bouton, drone not available.");
                    myIntent2 = new Intent(PlanPageActivity.this, DroneNotAvailableActivity.class);
                    startActivity(myIntent2);
                }
            }
        });



    }
}