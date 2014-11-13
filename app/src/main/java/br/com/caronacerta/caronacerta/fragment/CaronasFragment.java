package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.ExpandableListAdapter;
import br.com.caronacerta.caronacerta.contract.RidesContract;

public class CaronasFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    private SimpleCursorAdapter mRidesAdapter;

    private static final int RIDES_LOADER = 0;
    private String mRides;

    private static final String[] COLUMN_GROUP_NAME = {
            RidesContract.RidesGroupEntry.TABLE_NAME + "." + RidesContract.RidesGroupEntry._ID,
            RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME,
    };

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // To only show current and future dates, get the String representation for today,
        // and filter the query to return weather only for dates after or including today.
        // Only return data after today.

        // Sort order:  Ascending, by date.
        String sortOrder = RidesContract.RidesGroupEntry.COLUMN_GROUP_NAME+ " ASC";
        Uri ridesURI = RidesContract.RidesGroupEntry.buildRidesUri(id);

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                ridesURI,
                COLUMN_GROUP_NAME,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRidesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRidesAdapter.swapCursor(null);
    }
}