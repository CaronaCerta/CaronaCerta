package br.com.caronacerta.caronacerta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class VisualizarMotoristaFragment extends BasicFragment {

    public VisualizarMotoristaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visualizar_motorista, container, false);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_usuario", SessionUtil.getUserId(getActivity().getApplicationContext())));
        JSONObject jsonObject = RequestUtil.getData("motorista", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                if (jsonObject.getJSONArray("motoristas").length() > 0) {
                    JSONObject driver = (JSONObject) jsonObject.getJSONArray("motoristas").get(0);
                    String driverLicenseNumber = driver.getString("numero_habilitacao");
                    String driverLicenseDate = driver.getString("data_habilitacao");
                    ((TextView) rootView.findViewById(R.id.licence_driver_number)).append(" " + driverLicenseNumber);
                    ((TextView) rootView.findViewById(R.id.licence_driver_date)).append(" " + driverLicenseDate);
                    ((Button) rootView.findViewById(R.id.create_driver)).setVisibility(View.GONE);
                    ((TextView) rootView.findViewById(R.id.no_driver_message)).setVisibility(View.GONE);
                }
                else {
                    ((Button) rootView.findViewById(R.id.edit_driver)).setVisibility(View.GONE);
                    ((TextView) rootView.findViewById(R.id.licence_driver_number)).setVisibility(View.GONE);
                    ((TextView) rootView.findViewById(R.id.licence_driver_date)).setVisibility(View.GONE);
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
