package aaa.solasitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import aaa.solasitos.R;
import aaa.solasitos.gestores.GestorIdiomas;
import aaa.solasitos.listView.Adaptador;
import aaa.solasitos.listView.Entidad;


public class Jugadores extends AppCompatActivity {

    private ListView lvItems;
    private Adaptador adaptador;
    private String POR, DEF, MID, DEL;
    private Button btnBack;

    private GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.jugadores);

        POR = getResources().getString(R.string.POR);
        DEF = getResources().getString(R.string.DEF);
        MID = getResources().getString(R.string.MID);
        DEL = getResources().getString(R.string.DEL);

        lvItems = (ListView) findViewById(R.id.lvItems);
        adaptador = new Adaptador(this, GetArrayItems());
        lvItems.setAdapter(adaptador);

        btnBack = (Button) findViewById(R.id.btnBackJugadores);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        listItems.add(new Entidad(R.drawable.camiseta_entrenador, "Bernardo Dominguez", "Entrenador"));
        listItems.add(new Entidad(R.drawable.camiseta_entrenador, "Mikel Arberas", "Preparador Fisico"));
        listItems.add(new Entidad(R.drawable.camiseta1, "Javier Arteta",  POR));
        listItems.add(new Entidad(R.drawable.camiseta2, "Unai Galdos", DEF));
        listItems.add(new Entidad(R.drawable.camiseta3, "Aimar Uliarte", DEF));
        listItems.add(new Entidad(R.drawable.camiseta4, "Ioritz Hernandez", DEF));
        listItems.add(new Entidad(R.drawable.camiseta5, "Endika Gil", DEF));
        listItems.add(new Entidad(R.drawable.camiseta6, "Aitor Alessanco", MID));
        listItems.add(new Entidad(R.drawable.camiseta7, "Ioritz Benito", DEL));
        listItems.add(new Entidad(R.drawable.camiseta8, "Mikel Elejalde", MID));
        listItems.add(new Entidad(R.drawable.camiseta9, "Iñigo Fraile", DEL));
        listItems.add(new Entidad(R.drawable.camiseta10, "Unai Solaun", DEL));
        listItems.add(new Entidad(R.drawable.camiseta11, "Ozman Deddah", DEL));
        listItems.add(new Entidad(R.drawable.camiseta12, "Unai Hernaez", DEF));
        listItems.add(new Entidad(R.drawable.camiseta13, "Jon Martinez", POR));
        listItems.add(new Entidad(R.drawable.camiseta14, "Koldo Castaño", MID));
        listItems.add(new Entidad(R.drawable.camiseta15, "Abdou Begui", DEF));
        listItems.add(new Entidad(R.drawable.camiseta16, "Salva Mba", MID));
        listItems.add(new Entidad(R.drawable.camiseta17, "Osama Balali", MID));
        listItems.add(new Entidad(R.drawable.camiseta18, "Julen Beraza", DEL));
        return listItems;
    }
}