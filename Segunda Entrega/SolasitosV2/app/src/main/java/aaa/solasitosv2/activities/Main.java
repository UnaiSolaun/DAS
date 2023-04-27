package aaa.solasitosv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import aaa.solasitosv2.R;
import aaa.solasitosv2.widget.Widget;

public class Main extends AppCompatActivity {
    TextView txtTitulo;
    Button btn_camara, btn_widget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String username = getIntent().getExtras().getString("username");

        txtTitulo = findViewById(R.id.txt_principal_titulo);
        txtTitulo.setText(txtTitulo.getText() + " " + username);

        btn_camara = (Button) findViewById(R.id.btn_principal_camara);
        btn_widget = findViewById(R.id.btn_principal_widget);

        btn_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, Camera.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

        btn_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, WidgetMenu.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Main.this, Login.class);
        startActivity(intent);
        finish();
    }
}