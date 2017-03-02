package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class WelcomePageActivity extends AppCompatActivity{

    private static KnockKnockClient mClient = null;
    private String[] args = {"137.194.22.216", "4444"}; //changer adresse ip si necessaire
    private static final String TAG = "WelcomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        // Création de l'intent pour le bouton1
        final Button mybutton1 = (Button) findViewById(R.id.button1);
        mybutton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.i(TAG, "Click sur le bouton suivi d'un intent.");
                mClient = new KnockKnockClient(args);
                mClient.execute();
                Intent myIntend1 = new Intent(WelcomePageActivity.this, PlanPageActivity.class);
                startActivity(myIntend1);
                WelcomePageActivity.this.finish();
                }

        });
    }
}