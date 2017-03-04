package com.example.simondahan.smartdeliveryv1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DroneNotAvailableActivity extends AppCompatActivity {

    private static final String TAG = "DroneNotAvailableActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_not_available);
        Intent myIntent3 = getIntent();

        setContentView(R.layout.drone_not_available);
        Button mybutton2 = (Button) findViewById(R.id.button3);
        mybutton2.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("LongLogTag")
            public void onClick(View v3){
                Log.i(TAG, "Click sur le bouton suivi d'un intent.");
                Intent myIntent3= new Intent(DroneNotAvailableActivity.this, PlanPageActivity.class);
                startActivity(myIntent3);
                DroneNotAvailableActivity.this.finish();
            }
        });

    }
}