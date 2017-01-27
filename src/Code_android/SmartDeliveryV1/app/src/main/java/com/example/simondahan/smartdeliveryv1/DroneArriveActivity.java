package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DroneArriveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_arrive);

        // récupération de l'intent pour le bouton de ConfirmationDroneActivity
        Intent myIntent4 = getIntent();

        // Création de l'intent pour le bouton5
        Button mybutton5 = (Button) findViewById(R.id.button5);
        mybutton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){

                Intent myIntent4= new Intent(DroneArriveActivity.this, Page2Activity.class);
                startActivity(myIntent4);
            }


        });



    }

}