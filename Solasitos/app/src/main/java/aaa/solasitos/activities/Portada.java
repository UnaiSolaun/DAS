package aaa.solasitos.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import aaa.solasitos.R;
import aaa.solasitos.gestores.GestorIdiomas;

public class Portada extends AppCompatActivity {
    Button btnStart;
    GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.portada);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                startActivity(intent);
            }
        });
    }
}