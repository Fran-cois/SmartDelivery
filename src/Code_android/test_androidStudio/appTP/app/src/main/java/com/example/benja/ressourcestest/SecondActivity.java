package com.example.benja.ressourcestest;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(getIntent().getStringExtra("text_du_EditText"));

    }
}
