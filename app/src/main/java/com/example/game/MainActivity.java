package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button bOk;
    private EditText editText;
    private TextView txtIndicator;
    private ProgressBar progressBar;
    private TextView txtTentative;
    private ListView listView;
    private TextView txtScore;

    private int secret;
    private int counter;
    private int score;
    private List<String> listHistorique= new ArrayList<>();
    private int maxtentative=6;
    private ArrayAdapter<String> model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bOk= findViewById(R.id.button);
        editText=findViewById(R.id.editTextNumber);
        txtIndicator=findViewById(R.id.textView6);
        progressBar=findViewById(R.id.progressBar);
        txtTentative=findViewById(R.id.textView5);
        listView=findViewById(R.id.listView);
        txtScore=findViewById(R.id.textView4);


        model=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listHistorique);
        listView.setAdapter(model);
        initialiser();
        bOk.setOnClickListener((evt)->{
            int number = Integer.parseInt(editText.getText().toString());
            if(number > secret){
                txtIndicator.setText(R.string.val_sup);


            }else if(number<secret){
                txtIndicator.setText(R.string.val_inf);
            }else{
                txtIndicator.setText(R.string.bravo);
                score+=5;
                txtScore.setText(String.valueOf(score));
                rejouer();
            }
            listHistorique.add(counter+"=>"+number);
            model.notifyDataSetChanged();
            ++counter;
            txtTentative.setText(String.valueOf(counter));
            progressBar.setProgress(counter);
            if(counter>maxtentative){
                rejouer();
            }
        });

    }

    private void rejouer() {
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.rejouer));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialiser();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.quitter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialiser();
            }
        });
        alertDialog.show();
    }

    private void initialiser() {
        secret = 1+((int)(Math.random()*100));
        counter=1;
        listHistorique.clear();
        model.notifyDataSetChanged();
        txtTentative.setText(String.valueOf(counter));
        progressBar.setProgress(counter);
        progressBar.setMax(maxtentative);
        txtScore.setText(String.valueOf(score));
    }
}