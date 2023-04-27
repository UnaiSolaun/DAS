package aaa.solasitosv2.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

import aaa.solasitosv2.R;

public class Login extends AppCompatActivity {

    EditText txtUser, txtPassword;
    Button btnInicio, btnRegistro;
    String username,password;
    String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/usolaun001/WEB/inicioSesion.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtUser = findViewById(R.id.txt_inicio_user);
        txtUser.requestFocus();
        txtPassword = findViewById(R.id.txt_inicio_password);

        btnInicio = findViewById(R.id.btn_inicio_iniciar);
        btnRegistro = findViewById(R.id.btn_inicio_crear);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion(view);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void iniciarSesion(View view) {

        String username = txtUser.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("CARGANDO...");

        if (username.isEmpty()) {
            txtUser.setError("Introduce un usuario");
        } else if (password.isEmpty()) {
            txtPassword.setError("Introduce la contraseña");
        } else {

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Inicio de sesion correcto")) {
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}