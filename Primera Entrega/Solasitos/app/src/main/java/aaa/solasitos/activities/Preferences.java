package aaa.solasitos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import aaa.solasitos.fragments.AjustesFragment;
import aaa.solasitos.R;
import aaa.solasitos.gestores.GestorIdiomas;

public class Preferences extends AppCompatActivity {
    private GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.preferences);


        getSupportFragmentManager().beginTransaction().
                replace(R.id.contenedorAjustes, new AjustesFragment()).commit();
    }
}