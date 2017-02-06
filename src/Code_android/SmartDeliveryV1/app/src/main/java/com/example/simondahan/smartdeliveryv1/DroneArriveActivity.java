package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DroneArriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_arrive);


        // Création de l'intent pour le bouton5
        Button mybutton5 = (Button) findViewById(R.id.button5);
        mybutton5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){

                Intent myIntent7= new Intent(DroneArriveActivity.this, PlanPageActivity.class);
                startActivity(myIntent7);
                DroneArriveActivity.this.finish();
            }


        });



    }

}