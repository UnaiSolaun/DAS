package aaa.solasitosv2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import aaa.solasitosv2.R;

public class Register extends AppCompatActivity {

    EditText txtUser, txtEmail, txtPass;
    Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        txtUser = findViewById(R.id.txt_registro_user);
        txtUser.requestFocus();
        txtEmail = findViewById(R.id.txt_registro_email);
        txtPass = findViewById(R.id.txt_registro_password);
        btnCrear = findViewById(R.id.btn_registro_crear);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Pedir el permiso
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 11);
            }
        }

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                añadirToken();
            }
        });
    }

    private void añadirToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        String urlCrear = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/usolaun001/WEB/registro.php";

                        insertarDatosABD(urlCrear,token);
                        enviarNotificacionFireBase(token);
                    }
                });
    }

    public void insertarDatosABD(String pUrl, String pToken) {
        //String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/usolaun001/WEB/registro.php";
        final String username = txtUser.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPass.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("CARGANDO...");

        if (username.isEmpty()) {
            txtUser.setError("complete los campos");
            return;
        } else if (email.isEmpty()) {
            txtEmail.setError("complete los campos");
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, pUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Informacion insertada")) {
                        Toast.makeText(Register.this, "Informacion insertada", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                            /*Intent intent = new Intent(Registro.this, Inicio.class);
                            startActivity(intent);*/
                        finish();
                    } else {
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, "No es posible insertar", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("phoneToken", pToken);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
            requestQueue.add(request);
        }
    }



    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    private void enviarNotificacionFireBase(String tokenUsu){
        Log.i("ENVIADO", tokenUsu);
        String URL = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/usolaun001/WEB/notificacion.php";

        StringRequest busquedaLog = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Register.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, "Ha habido un problema al conectarse. Intentelo otra vez", Toast.LENGTH_SHORT);

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("tokenID",tokenUsu);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(busquedaLog);
    }

}