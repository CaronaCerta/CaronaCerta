package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.ExpandableListAdapter;

public class CaronasFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_caronas, container, false);


        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.eLV);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this.getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        return rootView;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("25/10 - 14h00");
        listDataHeader.add("29/10 - 19h00");
        listDataHeader.add("01/11 - 18h00");

        // Adding child data
        List<String> l1 = new ArrayList<String>();
        l1.add("Xandão");
        l1.add("Batata");
        l1.add("PD");

        List<String> l2 = new ArrayList<String>();
        l2.add("Oscar");
        l2.add("Elizeu");
        l2.add("PD");

        List<String> l3 = new ArrayList<String>();
        l3.add("Xandão");
        l3.add("Tocha");
        l3.add("Giu");

        listDataChild.put(listDataHeader.get(0), l1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), l2);
        listDataChild.put(listDataHeader.get(2), l3);
    }
}
