package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DroneArriveActivity extends AppCompatActivity {

    private static final String TAG = "DroneArriveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_arrive);

        // Création de l'intent pour le bouton5
        Button mybutton5 = (Button) findViewById(R.id.button5);
        mybutton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){
                Log.i(TAG, "Click sur le bouton suivi d'un intent.");
                Intent myIntent7= new Intent(DroneArriveActivity.this, WelcomePageActivity.class);
                startActivity(myIntent7);
                ChoixSalleActivity.freeFinalRoom();
                DroneArriveActivity.this.finish();
            }


        });



    }

}