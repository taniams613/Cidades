package com.example.a1casoprtico;


import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class AdicionarCidades extends ListActivity {

    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_cidades);


        final CidadesSQLite dbHelper = new CidadesSQLite(AdicionarCidades.this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String [] camposDb = {"cidade", BaseColumns._ID};

        c = db.query("cidades", camposDb, null, null, null, null, "cidade ASC");

        int[] camposView = new int [] { android.R.id.text1 };

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter (this, android.R.layout.simple_list_item_1, c, new String[] { "cidade" }, camposView, 0);
        setListAdapter(adapter);

        final EditText texto = findViewById(R.id.editText2);
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = texto.getText().toString();
                texto.setText("");

                dbHelper.adicionar(db, x);

                c = db.query("cidades", camposDb, null, null,null,null, "cidade ASC");
                int [] camposView = new int [] {android.R.id.text1};
                final SimpleCursorAdapter adapter = new SimpleCursorAdapter (AdicionarCidades.this, android.R.layout.two_line_list_item, c, camposDb, camposView, 0);
                setListAdapter(adapter);
            }
        });

    }
}





