package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationDroneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_drone);

        // récupération de l'intent pour le bouton de ChoixSalleActivity
        Intent myIntent4 = getIntent();
        setContentView(R.layout.confirmation_drone);

        // Création de l'intent pour le bouton4
        Button mybutton4 = (Button) findViewById(R.id.button4);
        mybutton4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){


                Intent myIntent5= new Intent(ConfirmationDroneActivity.this,SuiviCommandePlanActivity.class);
                startActivity(myIntent5);
                ConfirmationDroneActivity.this.finish();
            }


        });
    }
}