package aaa.solasitos.fragments;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import aaa.solasitos.R;
import aaa.solasitos.activities.Inicio;
import aaa.solasitos.activities.Portada;
import aaa.solasitos.gestores.GestorIdiomas;

public class AjustesFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    GestorIdiomas gestorIdiomas;
    public AjustesFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        setPreferencesFromResource(R.xml.pref_config, key);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String idiomaSeleccionado = sharedPreferences.getString(key,"en");
        gestorIdiomas = new GestorIdiomas(getActivity());
        switch (key) {
            case "language":
                gestorIdiomas.setIdioma(idiomaSeleccionado);
                getActivity().finish();
                Intent i = new Intent(getContext(), Portada.class);
                i.putExtra("idioma", idiomaSeleccionado);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}