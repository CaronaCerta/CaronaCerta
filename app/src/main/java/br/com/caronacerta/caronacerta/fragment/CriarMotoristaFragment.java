package br.com.caronacerta.caronacerta.fragment;

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

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

public class CriarMotoristaFragment extends BasicFragment implements View.OnClickListener {
    String userId = null;

    // Email Edit View Object
    EditText driverLicenseNumberET;
    // Password Edit View Object
    EditText driverLicenseDateET;

    public CriarMotoristaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_criar_motorista, container, false);

        Button button = (Button) rootView.findViewById(R.id.btnCreate);
        button.setOnClickListener(this);


        userId = SessionUtil.getUserId(getActivity().getApplicationContext());
        // Find Email Edit View control by ID
        driverLicenseNumberET = (EditText) rootView.findViewById(R.id.createLicenseDriverNumber);
        // Find Password Edit View control by ID
        driverLicenseDateET = (EditText) rootView.findViewById(R.id.createLicenseDriverDate);

        return rootView;
    }

    public void editUser(View view) throws JSONException {
        // Get Email ET control value
        String driverLicenseNumber = driverLicenseNumberET.getText().toString();
        // Get Password ET control value
        String driverLicenseDate = driverLicenseDateET.getText().toString();

        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (Validation.isNotNull(driverLicenseNumber) &&
                Validation.isNotNull(driverLicenseDate)) {
            // When Email entered is Valid
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id_usuario", userId));
            nameValuePairs.add(new BasicNameValuePair("numero_habilitacao", driverLicenseNumber));
            nameValuePairs.add(new BasicNameValuePair("data_habilitacao", driverLicenseDate));

            JSONObject jsonObject = RequestUtil.postData("motorista", nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

            try {
                if (!jsonObject.getBoolean("error")) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.criar_motorista_success_message, Toast.LENGTH_LONG).show();
                    navigateToFragment(new VisualizarMotoristaFragment());
                }
                // Some error returned
                else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.criar_motorista_error_message, Toast.LENGTH_LONG).show();
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
        getActivity().setTitle(R.string.criar_motorista_title);
    }
}
