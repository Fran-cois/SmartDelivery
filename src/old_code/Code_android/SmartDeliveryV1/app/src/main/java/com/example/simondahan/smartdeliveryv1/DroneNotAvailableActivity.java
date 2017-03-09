package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DroneNotAvailableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_not_available);
        Intent myIntent3 = getIntent();


        setContentView(R.layout.drone_not_available);
        Button mybutton2 = (Button) findViewById(R.id.button3);
        mybutton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v3){
                Intent myIntent3= new Intent(DroneNotAvailableActivity.this, PlanPageActivity.class);
                startActivity(myIntent3);
                DroneNotAvailableActivity.this.finish();
            }
        });

    }
}