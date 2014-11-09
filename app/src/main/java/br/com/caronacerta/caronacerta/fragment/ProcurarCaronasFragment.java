package br.com.caronacerta.caronacerta.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.PlacesAutoCompleteAdapter;

public class ProcurarCaronasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_procurar_caronas, container, false);
        final AutoCompleteTextView autoCompOriginView = (AutoCompleteTextView) rootView.findViewById(R.id.search_carona_autocomplete_origin);
        final AutoCompleteTextView autoCompDestinView = (AutoCompleteTextView) rootView.findViewById(R.id.search_carona_autocomplete_destination);
        autoCompOriginView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));
        autoCompDestinView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));

        Button confirm = (Button) rootView.findViewById(R.id.search_carona_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (autoCompOriginView.getText().length() == 0 || autoCompDestinView.getText().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Termine de preencher os campos...")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                List<Integer> indices = new ArrayList<Integer>();
                for (int i = 0; i < MainActivity.caronasAvailable.size(); i++) {
                    //add to ListView
                }
            }
                //Criar dialogo com resultados
            }
        });


        return rootView;
    }
}