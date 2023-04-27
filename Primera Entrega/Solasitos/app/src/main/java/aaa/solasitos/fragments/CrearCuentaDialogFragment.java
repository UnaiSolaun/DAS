package aaa.solasitos.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import aaa.solasitos.R;

public class CrearCuentaDialogFragment extends DialogFragment {
    private ListenerCrearCuentaDialogFragment listenerCrearCuenta;
    public interface ListenerCrearCuentaDialogFragment {
        void alPulsarCrear();
        void alPulsarCancelar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenerCrearCuenta = (ListenerCrearCuentaDialogFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("La clase " + context.toString() + "debe implementar ListenerCrearCuentaDialogFragment");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.messageDialog)
                .setPositiveButton(R.string.createAccount, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listenerCrearCuenta.alPulsarCrear();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listenerCrearCuenta.alPulsarCancelar();
                    }
                });
        return builder.create();
    }
}