package com.example.benja.ressourcestest;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        TextView b1 = (TextView) findViewById(R.id.text1);
        String text = res.getString(R.string.text, "Benjamin",20);
        b1.setText(text);
    }
}
