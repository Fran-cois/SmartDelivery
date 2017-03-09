package com.example.simondahan.smartdeliveryv1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by benja on 09/03/2017.
 */

public class testAsync extends AsyncTask{

    private static AppCompatActivity mActivity = null;

    public testAsync(AppCompatActivity activity) {

        mActivity = activity;         // We always keep a reference to the current activity
    }

    public static int SetActivity(AppCompatActivity activity) {
        mActivity = activity;
        return 1;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        Intent myIntent6= new Intent(mActivity, DroneArriveActivity.class);
        mActivity.startActivity(myIntent6);

        super.onPostExecute(o);
    }
}
