package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ChoixSalleActivity extends AppCompatActivity {

    public final static String VALUE ="ConfirmationDroneActivity.VALUE";
    RadioGroup salle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_salle);

        // récupération de l'intent
        Intent myIntent2 = getIntent();
        setContentView(R.layout.activity_choix_salle);

        ////transfert de la valeur de salle selectionnée
        Resources res = getResources();
        salle = (RadioGroup) findViewById(R.id.checkbox);
        final String SALLE = res.getString(R.string.Salle1);



        // Création de l'intent pour le bouton4
        Button mybuttonvalidate = (Button) findViewById(R.id.button_validate);
        mybuttonvalidate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){
                if (salle.getCheckedRadioButtonId()==R.id.salle1){

                    Intent myIntent4= new Intent(ChoixSalleActivity.this, ConfirmationDroneActivity.class);
                    myIntent4.putExtra(VALUE, SALLE);
                    startActivity(myIntent4);
                    ChoixSalleActivity.this.finish();}
            }


        });

    }

}