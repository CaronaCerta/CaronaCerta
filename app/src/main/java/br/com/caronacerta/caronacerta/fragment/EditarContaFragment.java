package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditarContaFragment extends Fragment implements View.OnClickListener {
    String userId = null;

    // Email Edit View Object
    EditText emailET;
    // Password Edit View Object
    EditText passwordET;
    // Name Edit View Object
    EditText nameET;
    // Birthday Edit View Object
    EditText birthdayET;
    // Phone Edit View Object
    EditText phoneET;
    // Address Edit View Object
    EditText addressET;
    // City Edit View Object
    EditText cityET;

    public EditarContaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editar_conta, container, false);

        Button button = (Button) rootView.findViewById(R.id.btnEdit);
        button.setOnClickListener(this);

        // Find Email Edit View control by ID
        emailET = (EditText) rootView.findViewById(R.id.editEmail);
        // Find Password Edit View control by ID
        passwordET = (EditText) rootView.findViewById(R.id.editPassword);
        // Find Name Edit View control by ID
        nameET = (EditText) rootView.findViewById(R.id.editName);
        // Find Birthday Edit View control by ID
        birthdayET = (EditText) rootView.findViewById(R.id.editBirthday);
        // Find Phone Edit View control by ID
        phoneET = (EditText) rootView.findViewById(R.id.editPhone);
        // Find Address Edit View control by ID
        addressET = (EditText) rootView.findViewById(R.id.editAddress);
        // Find City Edit View control by ID
        cityET = (EditText) rootView.findViewById(R.id.editCity);


        JSONObject jsonObject = RequestUtil.getData("usuario/me", SessionUtil.getToken(getActivity().getApplicationContext()));

        try {
            if (!jsonObject.getBoolean("error")) {
                JSONObject user = jsonObject.getJSONObject("usuario");
                userId = user.getString("id_usuario");
                String userName = user.getString("nome");
                String userEmail = user.getString("email");
                String userBirthDay = user.getString("data_nascimento");
                String userPhone = user.getString("telefone");
                String userAddress = user.getString("endereco");
                String userCity = user.getString("cidade");
                ((TextView) rootView.findViewById(R.id.editEmail)).append(userEmail);
                ((TextView) rootView.findViewById(R.id.editName)).append(userName);
                ((TextView) rootView.findViewById(R.id.editBirthday)).append(userBirthDay);
                ((TextView) rootView.findViewById(R.id.editPhone)).append(userPhone);
                ((TextView) rootView.findViewById(R.id.editAddress)).append(userAddress);
                ((TextView) rootView.findViewById(R.id.editCity)).append(userCity);
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
        String email = emailET.getText().toString();
        // Get Password ET control value
        String password = passwordET.getText().toString();
        // Get Name ET control value
        String name = nameET.getText().toString();
        // Get Birthday ET control value
        String birthday = birthdayET.getText().toString();
        // Get Phone ET control value
        String phone = phoneET.getText().toString();
        // Get Address ET control value
        String address = addressET.getText().toString();
        // Get City ET control value
        String city = cityET.getText().toString();

        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if (Validation.isNotNull(email) &&
                Validation.isNotNull(password) &&
                Validation.isNotNull(name) &&
                Validation.isNotNull(birthday) &&
                Validation.isNotNull(phone) &&
                Validation.isNotNull(address) &&
                Validation.isNotNull(city)
                ) {
            // When Email entered is Valid
            if (Validation.validateEmail(email)) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("senha", password));
                nameValuePairs.add(new BasicNameValuePair("nome", name));
                nameValuePairs.add(new BasicNameValuePair("data_nascimento", birthday));
                nameValuePairs.add(new BasicNameValuePair("telefone", phone));
                nameValuePairs.add(new BasicNameValuePair("endereco", address));
                nameValuePairs.add(new BasicNameValuePair("cidade", city));

                JSONObject jsonObject = RequestUtil.putData("usuario/" + userId, nameValuePairs, SessionUtil.getToken(getActivity().getApplicationContext()));

                try {
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.editar_conta_success_message, Toast.LENGTH_LONG).show();
                        navigateToFragment(R.string.visualizar_conta_title, new VisualizarContaFragment());
                    }
                    // Some error returned
                    else {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.editar_conta_error_message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
                }
            }
            // When Email is invalid
            else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.editar_conta_invalid_email_message, Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.editar_conta_null_fields_message, Toast.LENGTH_LONG).show();
        }

    }

    public void navigateToFragment(int resId, Fragment fragment) {
        navigateToFragment(getString(resId), fragment);
    }

    public void navigateToFragment(String title, Fragment fragment) {
        getActivity().setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
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
