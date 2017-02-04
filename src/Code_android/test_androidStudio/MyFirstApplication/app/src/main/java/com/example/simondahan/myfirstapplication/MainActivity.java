package com.example.simondahan.myfirstapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;




public class MainActivity extends Activity {

    private int data =5;
    public final static String VALUE = "MainActivity.VALUE";
    //EditText name = null;
    RadioGroup salle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        //final String test = res.getString(R.string.introduction);
        //name = (EditText) findViewById(R.id.editText);
        salle= (RadioGroup) findViewById(R.id.checkbox);
        final String SALLE = res.getString(R.string.Salle1);



        Button mybutton = (Button) findViewById(R.id.button1);
        mybutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //final String NAME = name.getText().toString();
                    if(salle.getCheckedRadioButtonId() == R.id.radioButton){
                        Log.i("OC_RSS","ça marcheee !");
                        Intent myIntend = new Intent(MainActivity.this, NextActivity.class);
                        myIntend.putExtra(VALUE,SALLE);
                        startActivity(myIntend);

                    }
                    else {
                        Log.i("OC_RSS","youhaaa !");
                    }

                    //Intent myIntend = new Intent(MainActivity.this, NextActivity.class);
                    //myIntend.putExtra(VALUE,NAME);

                    //startActivity(myIntend);
                }
        });


    }
}
