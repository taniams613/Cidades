package com.example.a1casoprtico;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MandarSMS extends ListActivity {

    private static final int PERMISSIONS_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contactos);
        mostrarContactos();
    }

    private void mostrarContactos () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_READ_CONTACTS);
        }
        else {
            lerContactos();
        }
    }

    private void lerContactos () {
        Cursor c;
        String [] campos = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int [] views = new int[] {android.R.id.text1};
        String condicao = ContactsContract.Contacts.HAS_PHONE_NUMBER + "='1'";

        c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, condicao, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                c,
                campos,
                views,
                0);

        setListAdapter(adapter);
    }

    protected void onListItemClick (ListView l, View v, int position, long id) {
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?",
                new String[]{"" + id},
                null);
        int ntelefones = c.getCount();
        final String [] telefones = new String[ntelefones];
        int x = 0;
        while (c.moveToNext()) {
            int col = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            telefones [x++] = c.getString(col);
        }
        c.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione o número pretendido");
        builder.setItems(telefones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                SharedPreferences dados = getSharedPreferences("info", 0);
                SharedPreferences.Editor editor = dados.edit();

                String numerotelefone = telefones[item];
                editor.putString("numero", numerotelefone);
                editor.apply();

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EscreverSMS.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mostrarContactos();
            }
            else {
                Toast.makeText(this, "Não tem permissão para ler os contactos.", Toast.LENGTH_LONG).show();
                this.finish();
            }
        }
    }

}
