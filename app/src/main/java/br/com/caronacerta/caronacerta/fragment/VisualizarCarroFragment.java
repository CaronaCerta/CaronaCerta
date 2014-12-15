package br.com.caronacerta.caronacerta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class VisualizarCarroFragment extends BasicFragment {

    public VisualizarCarroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visualizar_carro, container, false);

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
                            String carModel = car.getString("modelo");
                            String carDescription = car.getString("descricao");
                            ((TextView) rootView.findViewById(R.id.model)).append(" " + carModel);
                            ((TextView) rootView.findViewById(R.id.description)).append(" " + carDescription);
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

        return rootView;
    }
}
