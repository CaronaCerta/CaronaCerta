package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.CaronaAdapter;
import br.com.caronacerta.caronacerta.adapter.PlacesAutoCompleteAdapter;
import br.com.caronacerta.caronacerta.model.Carona;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

public class ProcurarCaronaFragment extends BasicFragment implements View.OnClickListener {
    EditText originET;
    EditText destinationET;
    ListView viewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_procurar_carona, container, false);
        final AutoCompleteTextView autoCompOriginView = (AutoCompleteTextView) rootView.findViewById(R.id.origin);
        final AutoCompleteTextView autoCompDestinyView = (AutoCompleteTextView) rootView.findViewById(R.id.destination);
        autoCompOriginView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));
        autoCompDestinyView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));

        originET = (EditText) rootView.findViewById(R.id.origin);
        destinationET = (EditText) rootView.findViewById(R.id.destination);

        Button button = (Button) rootView.findViewById(R.id.btnSearch);
        button.setOnClickListener(this);

        viewList = (ListView) rootView.findViewById(R.id.procurar_carona_results);

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
                    ListAdapter ca = new CaronaAdapter(getActivity().getApplicationContext(), createList(jsonObject.getJSONArray("caronas")), this);
                    viewList.setAdapter(ca);
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

    public void entrarCarona(String idCarona) {
        String userId = SessionUtil.getUserId(getActivity().getApplicationContext());
        String token = SessionUtil.getToken(getActivity().getApplicationContext());

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_carona", idCarona));
        nameValuePairs.add(new BasicNameValuePair("id_usuario", userId));

        JSONObject jsonObject = RequestUtil.postData("passageiro", nameValuePairs, token);

        try {
            if (!jsonObject.getBoolean("error")) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.procurar_carona_entrar_carona_sucesso, Toast.LENGTH_LONG).show();
            }
            // Some error returned
            else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.procurar_carona_entrar_carona_erro, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<Carona> createList(JSONArray caronas) throws JSONException {

        ArrayList<Carona> result = new ArrayList<Carona>();
        for (int i = 0; i < caronas.length(); i++) {
            Carona ci = new Carona();
            JSONObject carona = (JSONObject) caronas.get(i);
            ci.id_carona = carona.getString("id_carona");
            ci.lugar_saida = carona.getString("lugar_saida");
            ci.lugar_destino = carona.getString("lugar_destino");
            ci.lugares_disponiveis = carona.getString("lugares_disponiveis");
            ci.data = carona.getString("data");
            ci.observacoes = carona.getString("observacoes");

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