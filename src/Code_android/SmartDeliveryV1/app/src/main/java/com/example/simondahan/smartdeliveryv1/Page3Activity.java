package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Page3Activity extends AppCompatActivity {

    private static final String TAG = "Page3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);
        Intent myIntent3 = getIntent();

        setContentView(R.layout.page3);
        Button mybutton2 = (Button) findViewById(R.id.button3);
        mybutton2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v3){
                Log.i(TAG, "Click sur le bouton suivi d'un intent.");
                Intent myIntent3= new Intent(Page3Activity.this, PlanPageActivity.class);
                startActivity(myIntent3);
                Page3Activity.this.finish();
            }
        });

    }
}