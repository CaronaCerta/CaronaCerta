package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.caronacerta.caronacerta.R;

public class OfferCaronasFragment extends Fragment {

    public OfferCaronasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offer_caronas, container, false);

        return rootView;
    }
}
