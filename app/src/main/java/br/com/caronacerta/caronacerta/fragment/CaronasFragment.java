package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.ExpandableListAdapter;

public class CaronasFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_caronas, container, false);


        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.eLV);

        listAdapter = new ExpandableListAdapter(this.getActivity(), MainActivity.caronasELVGroup, MainActivity.caronasELVChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return rootView;
    }
}