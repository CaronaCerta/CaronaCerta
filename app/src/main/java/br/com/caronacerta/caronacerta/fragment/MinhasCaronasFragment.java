package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.ExpandableListAdapter;
import br.com.caronacerta.caronacerta.contract.RidesContract;

public class MinhasCaronasFragment extends BasicFragment {

    private static final int RIDES_LOADER = 0;
    private static final String[] COLUMN_GROUP_NAME = {
            RidesContract.RidesGroupEntry.TABLE_NAME + "." + RidesContract.RidesGroupEntry._ID,
            RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME,
    };
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    private SimpleCursorAdapter mRidesAdapter;
    private String mRides;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_minhas_caronas, container, false);


        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.eLV);

        listAdapter = new ExpandableListAdapter(this.getActivity(), MainActivity.caronasELVGroup, MainActivity.caronasELVChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        Button add_minhas_caronas = (Button) rootView.findViewById(R.id.add_minhas_caronas);

        add_minhas_caronas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new AdicionarMinhasCaronasFragment());
            }
        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(R.string.minhas_caronas_title);
    }
}