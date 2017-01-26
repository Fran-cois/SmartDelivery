package com.example.simondahan.myfirstapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.w3c.dom.Text;

/**
 * Created by SimonDahan on 04/12/2016.
 */

public class NextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntend = getIntent();
        String value = myIntend.getStringExtra("value");
        setContentView(R.layout.frame);

    }
}
