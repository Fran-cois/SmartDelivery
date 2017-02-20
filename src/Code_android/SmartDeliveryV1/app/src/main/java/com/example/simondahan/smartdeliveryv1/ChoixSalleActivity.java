<<<<<<< HEAD
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
import android.widget.Toast;


public class ChoixSalleActivity extends AppCompatActivity {

    public final static String VALUE ="ConfirmationDroneActivity.VALUE";
    RadioGroup salle = null;
    String SALLE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_salle);

        // récupération de l'intent
        Intent myIntent2 = getIntent();
        setContentView(R.layout.activity_choix_salle);

        ////transfert de la valeur de salle selectionnée
        final Resources res = getResources();
        salle = (RadioGroup) findViewById(R.id.checkbox);



        // Création de l'intent pour le bouton4
        Button mybuttonvalidate = (Button) findViewById(R.id.button_validate);
        mybuttonvalidate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v4){
                boolean isRadioButtonChecked = true;
                switch (salle.getCheckedRadioButtonId()){
                    case R.id.salle1:
                        SALLE = res.getString(R.string.Salle1);
                        break;
                    case R.id.salle2:
                        SALLE= res.getString(R.string.Salle2);
                        break;
                    case R.id.salle3:
                        SALLE= res.getString(R.string.Salle3);
                        break;
                    case R.id.salle4:
                        SALLE= res.getString(R.string.Salle4);
                        break;
                    case R.id.salle5:
                        SALLE= res.getString(R.string.Salle5);
                        break;
                    default:
                        isRadioButtonChecked = false;
                }
                    if (isRadioButtonChecked) {
                        Intent myIntent4 = new Intent(ChoixSalleActivity.this, ConfirmationDroneActivity.class);
                        myIntent4.putExtra(VALUE, SALLE);
                        startActivity(myIntent4);
                        ChoixSalleActivity.this.finish();
                    }
                    else{
                        Toast.makeText(ChoixSalleActivity.this,getString(R.string.messageErrorChoix),Toast.LENGTH_SHORT).show();
                    }

            }


        });

    }

=======
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

>>>>>>> ClientServer
}