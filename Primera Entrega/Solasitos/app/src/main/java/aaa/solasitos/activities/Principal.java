package aaa.solasitos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import aaa.solasitos.R;
import aaa.solasitos.activities.Jugadores;
import aaa.solasitos.gestores.GestorIdiomas;

public class Principal extends AppCompatActivity {

    Button btnJugadores, btnComentarios;
    private GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.principal);

        btnJugadores = (Button) findViewById(R.id.btnPlayers);
        btnJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Jugadores.class);
                startActivity(intent);
            }
        });

        btnComentarios = (Button) findViewById(R.id.btnComments);
        btnComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Comentarios.class);
                startActivity(intent);
            }
        });
    }
}