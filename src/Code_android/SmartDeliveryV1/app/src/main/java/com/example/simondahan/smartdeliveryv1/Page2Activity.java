package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Page2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        // récupération de l'intent pour la commande du bouton depuis la page 1 ou la page 3
        Intent myIntent1 = getIntent();
        setContentView(R.layout.page2);

        // Création de l'intent pour le bouton2
        Button mybutton2 = (Button) findViewById(R.id.button2);
        mybutton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v2){
                Intent myIntent2= new Intent(Page2Activity.this, ChoixSalleActivity.class);
                startActivity(myIntent2);
            }
        });



    }
}