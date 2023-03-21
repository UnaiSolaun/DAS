package aaa.solasitos.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import aaa.solasitos.R;
import aaa.solasitos.db.DataBase;
import aaa.solasitos.fragments.CrearCuentaDialogFragment;
import aaa.solasitos.gestores.GestorIdiomas;

public class Inicio extends AppCompatActivity implements CrearCuentaDialogFragment.ListenerCrearCuentaDialogFragment {
    EditText usernameET;
    EditText passwordET;
    Button btnSignIn;
    Button btnCreateAccount;
    private GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.inicio);
        
        DataBase DB = new DataBase(this, "dbProyecto.db", null,1);
        DB.getWritableDatabase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //gestorIdiomas.setIdioma(recibirIdioma());

        usernameET = (EditText) findViewById(R.id.editTextUsername);
        usernameET.requestFocus();
        passwordET = (EditText) findViewById(R.id.editTextPassword);

        // ACCIONES AL PULSAR EL BOTON DE SIGN IN
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                if (DB.existsUser(username))
                {
                    if(DB.existsUserPassword(username, password)) {
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Log.d("CONTRASEÑA", "La contraseña es incorrecta");
                        Toast.makeText(getApplicationContext(), "La contraseña no es correcta",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    DialogFragment dialog = new CrearCuentaDialogFragment();
                    dialog.show(getSupportFragmentManager(),"dialogo_crear");
                }
            }
        });

        // ACCIONES AL PULSAR EL BOTON DE SIGN UP
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                llamarAjustes();
                break;
            default:
                break;
        }
        return true;
    }

    public void llamarAjustes() {
        Intent intent = new Intent(this, Preferences.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void alPulsarCrear() {
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    @Override
    public void alPulsarCancelar() {

    }
}