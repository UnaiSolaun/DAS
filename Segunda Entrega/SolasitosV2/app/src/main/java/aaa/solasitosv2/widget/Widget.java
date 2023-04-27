package aaa.solasitosv2.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import aaa.solasitosv2.R;
import aaa.solasitosv2.activities.WidgetMenu;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {
    private static final String NOMBRE_ARCHIVO = "paises.txt";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);

        String[] paises = context.getApplicationContext().getResources().getStringArray(R.array.lista_compras);

        // Obtener una linea aleatoria del arvhivo de texto
        //String linea = obtenerLineaAleatoria(context);

        // Actualizar todos los widgets
        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, WidgetMenu.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_MUTABLE);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setOnClickPendingIntent(R.id.btn_widget_abrir, pendingIntent);

            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
            String strDate = mdformat.format(Calendar.getInstance().getTime());
            remoteViews.setTextViewText(R.id.txtHora, strDate);

            // Actualizar el texto de la vista
            Random random = new Random();
            int indiceAleatorio = random.nextInt(paises.length);
            String paisAleatorio = paises[indiceAleatorio];
            remoteViews.setTextViewText(R.id.txtPais,paisAleatorio);
            //remoteViews.setTextViewText(R.id.txtPais, linea);

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private String obtenerLineaAleatoria(Context context) {
        ArrayList<String> lineas = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(NOMBRE_ARCHIVO);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!lineas.isEmpty()) {
            Random random = new Random();
            int indiceAleatorio = random.nextInt(lineas.size());
            return lineas.get(indiceAleatorio);
        } else {
            return "";
        }
    }
}