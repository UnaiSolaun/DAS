package aaa.solasitos.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import aaa.solasitos.R;
import aaa.solasitos.gestores.GestorIdiomas;

public class Comentarios extends AppCompatActivity {
    EditText editText;
    TextView todosComentarios;
    Button btnSave, btnLoad, btnBack;
    private static final String KEY_TEXT = "text";
    private static final String FILE_NAME = "comentarios.txt";

    private GestorIdiomas gestorIdiomas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestorIdiomas = new GestorIdiomas(this);
        String idiomaActual = gestorIdiomas.getIdiomaActual();
        gestorIdiomas.setIdioma(idiomaActual);

        setContentView(R.layout.comentarios);

        todosComentarios = (TextView) findViewById(R.id.txtComments);
        todosComentarios.setText("");

        editText = (EditText) findViewById(R.id.editTextComments);
        btnSave = (Button) findViewById(R.id.btnSaveComment);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile();
                editText.setText("");
                todosComentarios.setText("");
            }
        });

        btnLoad = (Button) findViewById(R.id.btnLoadComment);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todosComentarios.setText("");
                loadFile();
            }
        });

        btnBack = (Button) findViewById(R.id.btnBackComments);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Principal.class);
                startActivity(intent);
            }
        });

        editText = (EditText) findViewById(R.id.editTextComments);
        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(KEY_TEXT);
            todosComentarios.setText(savedText);
        } else {
            loadFile();
        }
    }

    private void saveFile(){
        String textoASalvar = editText.getText().toString();
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_APPEND);
            fileOutputStream.write((textoASalvar + "\n").getBytes());
            Log.d("etiqueta", "Fichero Guardado en: " + getFilesDir() + "/" + FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadFile(){
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineaTexto;
            StringBuilder stringBuilder = new StringBuilder();
            while((lineaTexto = bufferedReader.readLine())!=null){
                stringBuilder.append(lineaTexto);
                stringBuilder.append("\n");
            }
//            bufferedReader.close();
            todosComentarios.setText(stringBuilder.toString());
        } catch (Exception e) {

        } finally {
            if (fileInputStream !=null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT, todosComentarios.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedText = savedInstanceState.getString(KEY_TEXT);
        todosComentarios.setText(savedText);
    }
}