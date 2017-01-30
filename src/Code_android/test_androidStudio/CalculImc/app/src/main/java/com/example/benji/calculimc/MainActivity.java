package com.example.benji.calculimc;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    // La chaîne de caractères par défaut
    private final String defaut = "Vous devez cliquer sur le bouton « Calculer l'IMC » pour obtenir un résultat.";
    // La chaîne de caractères de la megafonction
    private final String megaString = "Vous êtes parfait comme vous êtes, surtout ne changez pas !";

    Button envoyer = null;
    Button raz = null;
    EditText poids = null;
    EditText taille = null;
    RadioGroup group = null;
    TextView result = null; 
    CheckBox mega = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        envoyer = (Button)findViewById(R.id.calcul);

        raz = (Button)findViewById(R.id.raz);

        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);

        mega = (CheckBox)findViewById(R.id.mega);

        group = (RadioGroup)findViewById(R.id.group);

        result = (TextView)findViewById(R.id.result);

        envoyer.setOnClickListener(envoyerListener);
        raz.setOnClickListener(razListener);
        taille.addTextChangedListener(textWatcher);
        poids.addTextChangedListener(textWatcher);

        mega.setOnClickListener(checkedListener);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(defaut);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private OnClickListener envoyerListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!mega.isChecked()) {
                String t = taille.getText().toString();
                String p = poids.getText().toString();

                float tValue = Float.valueOf(t);
                if(tValue == 0)
                    Toast.makeText(MainActivity.this, "Allez, vas-y, rentre un poids sérieux pour avancer?", Toast.LENGTH_SHORT).show();
                else {
                    float pValue = Float.valueOf(p);
                    if(group.getCheckedRadioButtonId() == R.id.radio2)
                        tValue = tValue / 100;

                    tValue = (float)Math.pow(tValue, 2);
                    float imc = pValue / tValue;
                    result.setText("Votre IMC est " + String.valueOf(imc));
                }
            } else
                result.setText(megaString);
        }
    };

    private OnClickListener razListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(defaut);
        }
    };

    private OnClickListener checkedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!((CheckBox)v).isChecked() && result.getText().equals(megaString))
                result.setText(defaut);
        }
    };
}

