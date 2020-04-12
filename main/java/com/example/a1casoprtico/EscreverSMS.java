package com.example.a1casoprtico;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EscreverSMS extends AppCompatActivity {

    private EditText mensagem;
    private String numeropassado;
    private static final int PERMISSIONS_SMS_SEND = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.escreve_sms);

        TextView numero = findViewById(R.id.textView2);
        mensagem = findViewById(R.id.editText2);
        Button enviar = findViewById(R.id.button2);

        SharedPreferences dados = getSharedPreferences("info",0);
        numeropassado = dados.getString("numero", "");

        numero.setText(numeropassado);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mensagem.getText().toString().isEmpty())
                    enviarSMS();
                else
                    Toast.makeText(getApplicationContext(),"Por favor escreva a mensagem", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void enviarSMS(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions (new String[] {Manifest.permission.SEND_SMS}, PERMISSIONS_SMS_SEND);
        } else {
            String texto = mensagem.getText().toString();
            android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
            sms.sendTextMessage(numeropassado, null, texto, null, null);
            Toast.makeText(this, "Mensagem enviada.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void onRequestPermissionsResult (int requestCode, String [] permissions, int [] grantResults) {
        if (requestCode == PERMISSIONS_SMS_SEND) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enviarSMS();
            } else {
                Toast.makeText(this, "Não tem permissão para enviar SMS.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
