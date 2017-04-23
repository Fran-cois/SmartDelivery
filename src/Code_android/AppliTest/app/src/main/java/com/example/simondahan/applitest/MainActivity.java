package com.example.simondahan.applitest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        number = (TextView) findViewById(R.id.number);

        setContentView(R.layout.activity_main);
        new asynkTest(MainActivity.this).execute();
        /*Button mybutton1 = (Button) findViewById(R.id.button1);
        mybutton1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                new asynkTest(MainActivity.this).execute();


            }

        });*/



    }
}

class asynkTest extends AsyncTask<Object,Object,Boolean>{

    private AppCompatActivity Activity;

    public asynkTest (AppCompatActivity activity ){

        this.Activity = activity;
    }




    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);




    }

    @Override
    protected Boolean doInBackground(Object... objects) {

        //for (int j=0; j<10; j++){

            this.publishProgress("test");

        //}

        return null;

    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        TextView number = (TextView) Activity.findViewById(R.id.number);
        int i = 0;
        i =i+1;
        String s =String.valueOf(i);
        number.setText(s);




    }

}
