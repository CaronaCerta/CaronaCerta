package br.com.caronacerta.caronacerta.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.caronacerta.caronacerta.R;

public class EditarContaFragment extends Fragment {

    public EditarContaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_conta, container, false);
    }
}
