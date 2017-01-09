package com.example.simondahan.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mybutton = (Button) findViewById(R.id.button1);
        mybutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("OC_RSS","ça marcheee !");

                    Intent myIntend = new Intent(MainActivity.this, NextActivity.class);
                    startActivity(myIntend);
                }
        });


    }
}
