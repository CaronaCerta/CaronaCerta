package br.com.caronacerta.caronacerta.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

public class EditarCarroFragment extends BasicFragment implements View.OnClickListener {
    String userId = null;
    String carId = null;
    String driverId = null;

    // Email Edit View Object
    EditText carModelET;
    // Password Edit View Object
    EditText carDescriptionET;

    public EditarCarroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editar_carro, container, false);

        Button button = (Button) rootView.findViewById(R.id.btnEdit);
        button.setOnClickListener(this);

        // Find Email Edit View control by ID
        carModelET = (EditText) rootView.findViewById(R.id.editModel);
        // Find Password Edit View control by ID
        carDescriptionET = (EditText) rootView.findViewById(R.id.editDescription);


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_usuario", SessionUtil.getUserId(getActivity().getApplicationContext())));
        JSONObject jsonObject = RequestUtil.getData("motorista", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                if (jsonObject.getJSONArray("motoristas").length() > 0) {
                    JSONObject driver = (JSONObject) jsonObject.getJSONArray("motoristas").get(0);
                    driverId = driver.getString("id_motorista");
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("id_motorista", driverId));
                    JSONObject jsonObjectCar = RequestUtil.getData("carro", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));
                    if (!jsonObjectCar.getBoolean("error")) {
                        if (jsonObjectCar.getJSONArray("carros").length() > 0) {
                            JSONObject car = (JSONObject) jsonObjectCar.getJSONArray("carros").get(0);
                            carId = car.getString("id_carro");
                            String carModel = car.getString("modelo");
                            String carDescription = car.getString("descricao");
                            carModelET.append(carModel);
                            carDescriptionET.append(carDescription);
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

    public void editUser(View view) throws JSONException {
        // Get Email ET control value
        String carModel = carModelET.getText().toString();
        // Get Password ET control value
        String carDescription = carDescriptionET.getText().toString();

        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (Validation.isNotNull(carModel) &&
                Validation.isNotNull(carDescription)
                ) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id_motorista", driverId));
            nameValuePairs.add(new BasicNameValuePair("modelo", carModel));
            nameValuePairs.add(new BasicNameValuePair("descricao", carDescription));

            JSONObject jsonObject = RequestUtil.putData("carro/" + carId, nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

            try {
                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.editar_carro_success_message, Toast.LENGTH_LONG).show();
                    navigateToFragment(new VisualizarCarroFragment());
                }
                // Some error returned
                else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.editar_carro_error_message, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.editar_carro_null_fields_message, Toast.LENGTH_LONG).show();
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
        getActivity().setTitle(R.string.editar_carro_title);
    }
}
