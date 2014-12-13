package br.com.caronacerta.caronacerta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.Validation;

/**
 * Register Activity Class
 */
public class RegisterActivity extends BasicActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_launcher);

        // Find Email Edit View control by ID
        emailET = (EditText) findViewById(R.id.registerEmail);
        // Find Password Edit View control by ID
        passwordET = (EditText) findViewById(R.id.registerPassword);
        // Find Name Edit View control by ID
        nameET = (EditText) findViewById(R.id.registerName);
        // Find Birthday Edit View control by ID
        birthdayET = (EditText) findViewById(R.id.registerBirthday);
        // Find Phone Edit View control by ID
        phoneET = (EditText) findViewById(R.id.registerPhone);
        // Find Address Edit View control by ID
        addressET = (EditText) findViewById(R.id.registerAddress);
        // Find City Edit View control by ID
        cityET = (EditText) findViewById(R.id.registerCity);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_register;
    }

    /**
     * Method gets triggered when Register button is clicked
     *
     * @param view
     */
    public void registerUser(View view) throws JSONException {
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

                JSONObject jsonObject = RequestUtil.postData("usuario", nameValuePairs);

                try {
                    if (!jsonObject.getBoolean("error")) {
                        String sessionkey = jsonObject.getJSONObject("session").getString("key");
                        String userId = jsonObject.getJSONObject("usuario").getString("id_usuario");
                        SessionUtil.saveSession(sessionkey, userId, getApplicationContext());

                        navigateToMainActivity();
                    }
                    // Some error returned
                    else {
                        Toast.makeText(getApplicationContext(), R.string.register_error_message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
                }
            }
            // When Email is invalid
            else {
                Toast.makeText(getApplicationContext(), R.string.register_invalid_email_message, Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else {
            Toast.makeText(getApplicationContext(), R.string.register_null_fields_message, Toast.LENGTH_LONG).show();
        }

    }
}