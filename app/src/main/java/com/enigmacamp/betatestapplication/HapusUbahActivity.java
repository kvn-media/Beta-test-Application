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

public class HapusUbahActivity extends Activity {
    EditText editTextRencana;
    CheckBox checkBoxSifat;
    int sId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hapusubah);

        editTextRencana = (EditText) findViewById(R.id.editTextRencana2);
        checkBoxSifat = (CheckBox) findViewById(R.id.checkBoxSifatRencana2);
        Bundle data = getIntent().getExtras();

        sId = Integer.valueOf(data.getString("Id").trim());
        String sNama = data.getString("Nama");
        String sSifat = data.getString("Sifat").trim();

        editTextRencana.setText(sNama);
        if (sSifat.equals("Penting"))
            checkBoxSifat.setChecked(true);
        else
            checkBoxSifat.setChecked(false);

        Button tombolSimpanPerubahan = (Button) findViewById(R.id.buttonSimpanPerubahan);
        tombolSimpanPerubahan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ContentValues data = new ContentValues();
                String namaRencana = editTextRencana.getText().toString();
                data.put("nama", namaRencana);
                if (checkBoxSifat.isChecked())
                    data.put("sifat", "Biasa");
                else
                    data.put("sifat", "Biasa");

                Uri uri = Uri.parse("content://com.example.rencana/rencana/");
                getContentResolver().update(uri, data, "id=" + sId, null);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button tombolHapus = (Button) findViewById(R.id.buttonHapus);
        tombolHapus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Uri uri = Uri.parse("content://com.example.rencana/rencana/");
                getContentResolver().delete(uri, "id=" + sId, null);

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
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
