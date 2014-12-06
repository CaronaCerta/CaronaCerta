package br.com.caronacerta.caronacerta.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.caronacerta.caronacerta.R;

public class AvaliarCaronasFragment extends Fragment {

    public AvaliarCaronasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_avaliar_caronas, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This section hasn't been implemented yet.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        return rootView;
    }
}
