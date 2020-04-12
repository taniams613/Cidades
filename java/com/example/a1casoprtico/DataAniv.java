package com.example.a1casoprtico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DataAniv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_aniv);

        final DatePicker data = findViewById(R.id.datePicker);
        final SharedPreferences dados = getSharedPreferences("info",0);
        final int dia = dados.getInt("dia", 1);
        final int mes = dados.getInt("mes", 0);
        final int ano = dados.getInt("ano", 1990);
        data.init(ano, mes, dia, null);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences dados = getSharedPreferences("info",0);
                SharedPreferences.Editor editor = dados.edit();

                final int dia = data.getDayOfMonth();
                editor.putInt("dia",dia);

                final int mes = data.getMonth();
                editor.putInt("mes",mes);

                final int ano = data.getYear();
                editor.putInt("ano", ano);
                editor.apply();

                Toast toast = Toast.makeText(getApplicationContext(), "Data de nascimento guardada", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });
    }

}
