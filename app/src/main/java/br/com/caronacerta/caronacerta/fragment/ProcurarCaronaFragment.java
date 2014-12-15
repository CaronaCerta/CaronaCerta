package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.model.Carona;
import br.com.caronacerta.caronacerta.adapter.CaronaAdapter;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.PlacesAutoCompleteAdapter;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

public class ProcurarCaronaFragment extends BasicFragment implements View.OnClickListener {
    EditText originET;
    EditText destinationET;
    RecyclerView recList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_procurar_carona, container, false);
        final AutoCompleteTextView autoCompOriginView = (AutoCompleteTextView) rootView.findViewById(R.id.origin);
        final AutoCompleteTextView autoCompDestinView = (AutoCompleteTextView) rootView.findViewById(R.id.destination);
        autoCompOriginView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));
        autoCompDestinView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));

        originET = (EditText) rootView.findViewById(R.id.origin);
        destinationET = (EditText) rootView.findViewById(R.id.destination);

        Button button = (Button) rootView.findViewById(R.id.btnSearch);
        button.setOnClickListener(this);

        recList = (RecyclerView) rootView.findViewById(R.id.procurar_carona_results);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        return rootView;
    }

    public void editUser(View view) throws JSONException {
        String origin = originET.getText().toString();
        String destination = destinationET.getText().toString();


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (Validation.isNotNull(origin)) {
            nameValuePairs.add(new BasicNameValuePair("lugar_saida", origin));
        }
        if (Validation.isNotNull(destination)) {
            nameValuePairs.add(new BasicNameValuePair("lugar_destino", destination));
        }

        JSONObject jsonObject = RequestUtil.getData("carona", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                if (jsonObject.getJSONArray("caronas").length() > 0) {
                    Toast.makeText(getActivity().getApplicationContext(), ((JSONObject) jsonObject.getJSONArray("caronas").get(0)).toString(), Toast.LENGTH_LONG).show();
                    RecyclerView.Adapter ca = new CaronaAdapter(createList(30));
                    recList.setAdapter(ca);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.procurar_carona_no_results, Toast.LENGTH_LONG).show();
                }
            }
            // Some error returned
            else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.procurar_carona_error_message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
        }


    }

    private List<Carona> createList(int size) {

        List<Carona> result = new ArrayList<Carona>();
        for (int i = 1; i <= size; i++) {
            Carona ci = new Carona();
            ci.name = Carona.NAME_PREFIX + i;
            ci.surname = Carona.SURNAME_PREFIX + i;
            ci.email = Carona.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        try {
            editUser(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}