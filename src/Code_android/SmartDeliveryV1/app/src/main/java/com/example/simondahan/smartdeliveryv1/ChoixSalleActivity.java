package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChoixSalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_salle);

        // récupération de l'intent
        Intent myIntent2 = getIntent();
        setContentView(R.layout.activity_choix_salle);


        // Création de l'intent pour le bouton4
        Button mybuttonvalidate = (Button) findViewById(R.id.button_validate);
        mybuttonvalidate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){

                Intent myIntent4= new Intent(ChoixSalleActivity.this, ConfirmationDroneActivity.class);
                startActivity(myIntent4);
                ChoixSalleActivity.this.finish();
            }


        });

    }

}