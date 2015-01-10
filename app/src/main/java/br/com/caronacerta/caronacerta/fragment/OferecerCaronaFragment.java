package br.com.caronacerta.caronacerta.fragment;

import android.content.Intent;
import android.os.Bundle;
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

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.adapter.PlacesAutoCompleteAdapter;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

public class OferecerCaronaFragment extends BasicFragment implements View.OnClickListener {
    String carId;
    EditText originET;
    EditText destinationET;
    EditText dateET;
    EditText seatsET;
    EditText observationsET;

    public OferecerCaronaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_oferecer_carona, container, false);

        final AutoCompleteTextView autoCompOriginView = (AutoCompleteTextView) rootView.findViewById(R.id.origin);
        final AutoCompleteTextView autoCompDestinView = (AutoCompleteTextView) rootView.findViewById(R.id.destination);
        autoCompOriginView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));
        autoCompDestinView.setAdapter(new PlacesAutoCompleteAdapter(this.getActivity(), R.layout.autocomplete_item));

        Button button = (Button) rootView.findViewById(R.id.btnCreate);
        button.setOnClickListener(this);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_usuario", SessionUtil.getUserId(getActivity().getApplicationContext())));
        JSONObject jsonObject = RequestUtil.getData("motorista", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                if (jsonObject.getJSONArray("motoristas").length() > 0) {
                    JSONObject driver = (JSONObject) jsonObject.getJSONArray("motoristas").get(0);
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("id_motorista", driver.getString("id_motorista")));
                    JSONObject jsonObjectCar = RequestUtil.getData("carro", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));
                    if (!jsonObjectCar.getBoolean("error")) {
                        if (jsonObjectCar.getJSONArray("carros").length() > 0) {
                            JSONObject car = (JSONObject) jsonObjectCar.getJSONArray("carros").get(0);
                            carId = car.getString("id_carro");
                        }
                    }
                }

            } else {
                SessionUtil.logout(getActivity().getApplicationContext());
                Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
                Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginActivity);
            }
        } catch (Exception e) {
            SessionUtil.logout(getActivity().getApplicationContext());
            Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
            Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginActivity);
            e.printStackTrace();
        }


        originET = (EditText) rootView.findViewById(R.id.origin);
        destinationET = (EditText) rootView.findViewById(R.id.destination);
        dateET = (EditText) rootView.findViewById(R.id.date);
        seatsET = (EditText) rootView.findViewById(R.id.seats);
        observationsET = (EditText) rootView.findViewById(R.id.observations);

        return rootView;
    }

    public void editUser(View view) throws JSONException {
        String origin = originET.getText().toString();
        String destination = destinationET.getText().toString();
        String date = dateET.getText().toString();
        String seats = seatsET.getText().toString();
        String observations = observationsET.getText().toString();

        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (Validation.isNotNull(origin) &&
                Validation.isNotNull(destination) &&
                Validation.isNotNull(date) &&
                Validation.isNotNull(seats) &&
                Validation.isNotNull(observations)) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id_carro", carId));
            nameValuePairs.add(new BasicNameValuePair("data", date));
            nameValuePairs.add(new BasicNameValuePair("lugar_saida", origin));
            nameValuePairs.add(new BasicNameValuePair("lugar_destino", destination));
            nameValuePairs.add(new BasicNameValuePair("lugares_disponiveis", seats));
            nameValuePairs.add(new BasicNameValuePair("observacoes", observations));

            JSONObject jsonObject = RequestUtil.postData("carona", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

            try {
                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.oferecer_carona_success_message, Toast.LENGTH_LONG).show();
                    navigateToFragment(new HomeFragment());
                }
                // Some error returned
                else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.oferecer_carona_error_message, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.server_response_error, Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.required_fields_message, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {
        try {
            editUser(view);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(R.string.oferecer_carona_title);
    }
}
