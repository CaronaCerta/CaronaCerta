package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.RatingListAdapter;
import br.com.caronacerta.caronacerta.adapter.ViewWrapper;

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

        setListAdapter(new RatingAdapter(list));

        return rootView;
    }

    static final String[] SmartPhones = new String[]{
            "HTC Rezound", "Samsung Galaxy S II Skyrocket",
            "Samsung Galaxy Nexus", "Motorola Droid Razr",
            "Samsung Galaxy S", "Samsung Epic Touch 4G",
            "iPhone 4S", "HTC Titan"
    };

    private class RowModel {
        String label;
        float rating = 2.0f;

        RowModel(String label) {
            this.label = label;
        }

        public String toString() {
            if (rating >= 3.0) {
                return (label.toUpperCase());
            }
            return (label);
        }
    }
}
