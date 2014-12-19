package br.com.caronacerta.caronacerta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.RatingAdapter;
import br.com.caronacerta.caronacerta.model.RowModel;

public class AvaliarCaronasFragment extends BasicFragment {

    public AvaliarCaronasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_avaliar_caronas, container, false);

        ArrayList<RowModel> list = new ArrayList<RowModel>();

        for (String s : SmartPhones) {
            list.add(new RowModel(s));
        }

        ListView viewList = (ListView) rootView.findViewById(R.id.row);
        viewList.setAdapter(new RatingAdapter(getActivity().getApplicationContext(), list));

        return rootView;
    }

    static final String[] SmartPhones = new String[]{
            "HTC Rezound", "Samsung Galaxy S II Skyrocket",
            "Samsung Galaxy Nexus", "Motorola Droid Razr",
            "Samsung Galaxy S", "Samsung Epic Touch 4G",
            "iPhone 4S", "HTC Titan"
    };
}                                                     