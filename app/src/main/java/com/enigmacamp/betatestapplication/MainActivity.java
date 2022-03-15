package com.enigmacamp.betatestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends Activity {

    ListView listViewRencana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTambah = (Button) findViewById(R.id.buttonTambah);
        buttonTambah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(arg0.getContext(), TambahActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listViewRencana = (ListView) findViewById(R.id.listViewRencana);
        listViewRencana.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String terpilih = listViewRencana.getItemAtPosition(position).toString();

                ArrayList<String> baris = new ArrayList<String>();
                StringTokenizer token = new StringTokenizer(terpilih,"\n:");

                while (token.hasMoreElements()) {
                    baris.add(token.nextElement().toString());
                }

                Intent intent = new Intent(MainActivity.this,HapusUbahActivity.class);

                Bundle data = new Bundle();
                data.putString("Id", baris.get(1));
                data.putString("Nama", baris.get(2));
                data.putString("Sifat", baris.get(4));

                intent.putExtras(data);
                startActivityForResult(intent, 2);
            }
        });

        ArrayList<String> listData = perolehData();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listData);
        listViewRencana.setAdapter(adapter);
    }

    private ArrayList<String> perolehData() {
        ArrayList<String> hasil = new ArrayList<String>();
        Uri semuaRencana = Uri.parse("content://com.example.rencana");

        Cursor c = getContentResolver().query(semuaRencana, null, null,
                null, "id asc");
        if (c.moveToFirst()) {
            do {
                int idRencana = c.getInt(c.getColumnIndex("id"));
                String namaRencana = c.getString(
                        c.getColumnIndex("nama"));
                String sifatRencana = c.getString(
                        c.getColumnIndex("sifat"));

                hasil.add("ID: " + idRencana + "\n" + namaRencana + "\nSifat: " + sifatRencana);

            } while (c.moveToNext());
        }
        return hasil;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 || requestCode == 2) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> listData = perolehData();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, listData);
                listViewRencana.setAdapter(adapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}