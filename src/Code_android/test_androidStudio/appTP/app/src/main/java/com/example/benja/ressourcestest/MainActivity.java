package com.example.benja.ressourcestest;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView b1 = (TextView) findViewById(R.id.textView1);
        String text = getString(R.string.text, "Benjamin",20);

        b1.setText(text);
        Button button1 = (Button) findViewById(R.id.button1);

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        final String string1 = editText1.getText().toString();
        Log.v("EtiquetteCommun","Message commun");
        Log.d("EtiquetteDebug","Message debug");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, SecondActivity.class);
                intent1.putExtra("text_du_EditText",string1);
                startActivity(intent1);
            }
        });
    }
}
