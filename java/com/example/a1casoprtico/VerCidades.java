package com.example.a1casoprtico;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class VerCidades extends ListActivity {
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_cidades);
        mostrarCidades();
    }

    private void mostrarCidades() {
        final CidadesSQLite dbHelper = new CidadesSQLite(this);
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        final String [] camposDb = {"cidade", BaseColumns._ID};

        c = db.query("cidades", camposDb, null, null, null, null, "cidade ASC");

        int[] camposView = new int [] { android.R.id.text1 };

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter (this, android.R.layout.simple_list_item_1, c, new String[] { "cidade" }, camposView, 0);
        setListAdapter(adapter);
    }

    protected void onListItemClick (ListView l, View v, int position, long id) {
        SharedPreferences dados = getSharedPreferences("info",0);
        SharedPreferences.Editor editor = dados.edit();

        c.moveToPosition(position);
        String cidade = c.getString(c.getColumnIndex("cidade"));

        editor.putString("cidade", cidade);
        editor.apply();

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MapsActivity.class);
        startActivity(intent);

    }
}
