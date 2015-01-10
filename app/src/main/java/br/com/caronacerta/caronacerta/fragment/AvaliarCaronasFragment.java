package br.com.caronacerta.caronacerta.fragment;

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

    static final String[] SmartPhones = new String[]{
            "Alexandre - 17/10", "Bruno - 21/10",
            "Carlos - 29/10", "Daniel - 03/11", "Daniel - 03/11"
    };

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
        viewList.setScrollContainer(false);
        setListViewHeightBasedOnChildren(viewList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(R.string.avaliar_caronas_title);
    }
}                                                     