package com.example.simondahan.myfirstapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by SimonDahan on 04/12/2016.
 */

public class NextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent myIntend = getIntent();
        setContentView(R.layout.frame);


        String dataReceived = myIntend.getStringExtra(MainActivity.VALUE);
        TextView receiver = (TextView) findViewById(R.id.textView6);
        receiver.setText(dataReceived);


    }
}
