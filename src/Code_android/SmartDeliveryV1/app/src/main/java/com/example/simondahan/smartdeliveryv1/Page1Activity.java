package com.example.simondahan.smartdeliveryv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Page1Activity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        Button mybutton1 = (Button) findViewById(R.id.button1);
        mybutton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent myIntend1 = new Intent(Page1Activity.this, Page2Activity.class);
                startActivity(myIntend1);
                Page1Activity.this.finish();
            }

    });
}

}