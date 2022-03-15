package com.enigmacamp.betatestapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TambahActivity extends Activity {
    EditText editTextRencana;
    CheckBox checkBoxSifat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah);

        editTextRencana = (EditText) findViewById(R.id.editTextRencana);
        checkBoxSifat = (CheckBox) findViewById(R.id.checkBoxSifatRencana);

        Button tombolSimpanPenambahan = (Button) findViewById(R.id.buttonSimpanPenambahan);
        tombolSimpanPenambahan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String namaRencana = editTextRencana.getText().toString().trim();
                if (!namaRencana.equals("")) {
                    ContentValues data = new ContentValues();
                    data.put("nama", namaRencana);
                    if (checkBoxSifat.isChecked())
                        data.put("sifat", "Penting");
                    else
                        data.put("sifat", "Biasa");

                    Uri uri = Uri.parse("content://com.example.rencana/rencana");
                    getContentResolver().insert(uri, data);
                }

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button tombolBatal = (Button) findViewById(R.id.buttonBatal);
        tombolBatal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
