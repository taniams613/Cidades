package com.example.a1casoprtico;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imgview = findViewById(R.id.imageView);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgview.animate().rotation(360).start();

            }
        });

            // Adicionar Cidade
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AdicionarCidades.class);
                startActivity(intent);
            }
        });

            // VER MAPA
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, VerCidades.class);
                startActivity(intent);
            }
        });

            // DATA ANIVERSARIO
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DataAniv.class);
                startActivity(intent);
            }
        });

            //ACTIVITY DA CHAMADA
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FazerChamadas.class);
                startActivity(intent);
            }
        });


            // ACTIVITY DOS SMS
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MandarSMS.class);
                startActivity(intent);
            }
        });

            //ACTIVITY SOBRE
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Sobre.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences dados = getSharedPreferences("info",0);
        int dia = dados.getInt("dia", 1);
        int mes = dados.getInt("mes", 0);
        int ano = dados.getInt("ano", 1990);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        int anoatual = cal2.get(Calendar.YEAR);
        int mesatual = cal2.get(Calendar.MONTH);
        int diaatual = cal2.get(Calendar.DAY_OF_MONTH);


        if (mesatual > mes) {
            anoatual = anoatual + 1;
        }

        if (mesatual == mes && diaatual > dia) {
            anoatual = anoatual + 1;
        }

        cal1.set(anoatual, mes, dia);

        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal2.getTimeInMillis();

        long diff = milis1 - milis2;

        long diffDays = diff / (24 * 60 * 60 * 1000);
        int diffAnos = anoatual - ano;

        TextView textAniv = findViewById(R.id.textView2);
        textAniv.setText(getApplicationContext().getString(R.string.aniversario, diffDays, diffAnos));
    }
}
