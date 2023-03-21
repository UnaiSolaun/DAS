package aaa.solasitos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import aaa.solasitos.db.DataBase;
import aaa.solasitos.R;
import aaa.solasitos.gestores.GestorIdiomas;

public class Registro extends AppCompatActivity {
    EditText usernameET;
    EditText passwordET;
    EditText nameET;
    EditText surnameET;
    Button btnBack;
    Button btnDone;
    DataBase DB;
    String username;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    private GestorIdiomas gestorIdiomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.registro);

        DB = new DataBase(this, "dbProyecto.db", null,1);

        usernameET = (EditText) findViewById(R.id.editTextUsername);
        usernameET.requestFocus();
        passwordET = (EditText) findViewById(R.id.editTextPassword);
        nameET = (EditText) findViewById(R.id.editTextName);
        surnameET = (EditText) findViewById(R.id.editTextSurname);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String name = nameET.getText().toString();
                String surname = surnameET.getText().toString();

                if (username.length()!=0 || password.length()!=0 || name.length()!=0 || surname.length()!=0){
                    boolean usuarioExiste = DB.existsUser(username);
                    if (!usuarioExiste) {
                        DB.createUser(username, password, name, surname);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);

                        // NOTIFICACION
                        createNotificationChannel();
                        crearNotificacionRegistro();

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.yaExiste, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.empty, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT );
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    @SuppressLint("MissingPermission")
    private void crearNotificacionRegistro() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_check_circle_outline_24);
        builder.setContentTitle(getResources().getString(R.string.succesfully));
        builder.setContentText(username);
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}