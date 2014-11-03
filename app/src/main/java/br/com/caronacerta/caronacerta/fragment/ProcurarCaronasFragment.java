package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import br.com.caronacerta.caronacerta.R;

public class ProcurarCaronasFragment extends Fragment {

    public ProcurarCaronasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_procurar_caronas, container, false);

        return rootView;
    }

    public void OnActivityCreate(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        ExpandableListView eLV = (ExpandableListView) getView().findViewById(R.id.expandableListView);

        ArrayList<String> groupItem = new ArrayList<String>();
        ArrayList<Object> childItem = new ArrayList<Object>();

/*
        groupItem.add("03/10/14 - 14:00");
        groupItem.add("03/10/14 - 17:00");
        groupItem.add("04/10/14 - 12:00");
        groupItem.add("05/10/14 - 19:00");
        groupItem.add("08/10/14 - 14:00");

        ArrayList<String> child = new ArrayList<String>();
        child.add("Bruno");
        child.add("Tocha");
        child.add("Elizeu");
        childItem.add(child);

        ArrayList<String> child = new ArrayList<String>();
        child.add("Bruno");
        child.add("Elizeu");
        childItem.add(child);

        ArrayList<String> child = new ArrayList<String>();
        child.add("Pota");
        child.add("Tocha");
        child.add("Elizeu");
        childItem.add(child);

        ArrayList<String> child = new ArrayList<String>();
        child.add("Tocha");
        child.add("Elizeu");
        childItem.add(child);

        ArrayList<String> child = new ArrayList<String>();
        child.add("Bruno");
        child.add("Xandao");
        childItem.add(child);
*/

    }
}
