package aaa.solasitosv2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import aaa.solasitosv2.R;
import aaa.solasitosv2.widget.Widget;

public class WidgetMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_menu);

        Button btnRefresh = findViewById(R.id.btn_widgetMenu_refresh);
        Button btnCreate = findViewById(R.id.btn_widgetMenu_create);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppWidgetManager appWidgetManager = view.getContext().getSystemService(AppWidgetManager.class);
                Intent intent = new Intent(getApplication(), Widget.class);
                int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), Widget.class));
                intent.setAction(appWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppWidgetManager appWidgetManager = view.getContext().getSystemService(AppWidgetManager.class);
                ComponentName myProvider = new ComponentName(view.getContext(), Widget.class);
                if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                    appWidgetManager.requestPinAppWidget(myProvider, null, null);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        String username = getIntent().getExtras().getString("username");
        Intent intent = new Intent(WidgetMenu.this, Main.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
}