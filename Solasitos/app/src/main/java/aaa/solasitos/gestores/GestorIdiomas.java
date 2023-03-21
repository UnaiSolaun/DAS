package aaa.solasitos.gestores;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.Preference;

import java.util.Locale;

public class GestorIdiomas {
    private static final String PREFERENCIAS_IDIOMA = "prefs_language";
    private static final String KEY_IDIOMA = "language";
    private Context context;

    public GestorIdiomas (Context context) {
        this.context = context;
    }
    public void setIdioma(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS_IDIOMA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(KEY_IDIOMA, language);
        editor.apply();
    }

    public String getIdiomaActual() {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS_IDIOMA, Context.MODE_PRIVATE);
        return preferencias.getString(KEY_IDIOMA, Locale.getDefault().getLanguage());
    }
}
