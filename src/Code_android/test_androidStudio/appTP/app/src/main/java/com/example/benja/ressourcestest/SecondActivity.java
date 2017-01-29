package com.example.benja.ressourcestest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(getIntent().getStringExtra("text_du_EditText"));

        final String  isChecked = null  ;

        final CheckBox box1 = (CheckBox) findViewById(R.id.checkBox2);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.putExtra(isChecked,box1.isChecked());
                SecondActivity.this.finish();
            }
        });

    }
}
