package br.com.caronacerta.caronacerta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
 * Login Activity Class
 */
public class LoginActivity extends BasicActivity {
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Email Edit View Object
    EditText emailET;
    // Passwprd Edit View Object
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // User is already logged in
        if (SessionUtil.isLoggedIn(getApplicationContext())) {
            navigateToMainActivity();
            return;
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);
        // Find Error Msg Text View control by ID
        errorMsg = (TextView) findViewById(R.id.loginError);
        // Find Email Edit View control by ID
        emailET = (EditText) findViewById(R.id.loginEmail);
        // Find Password Edit View control by ID
        passwordET = (EditText) findViewById(R.id.loginPassword);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage(getString(R.string.process_dialog));
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    public void loginUser(View view) throws JSONException {
        // Get Email Edit View Value
        String email = emailET.getText().toString();
        // Get Password Edit View Value
        String password = passwordET.getText().toString();
        // Instantiate Http Request Param Object
        // When Email Edit View and Password Edit View have values other than Null
        if (Validation.isNotNull(email) && Validation.isNotNull(password)) {
            // When Email entered is Valid
            if (Validation.validateEmail(email)) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("senha", password));

                JSONObject jsonObject = RequestUtil.postData("login", nameValuePairs);

                try {
                    if (!jsonObject.getBoolean("error")) {
                        String sessionkey = jsonObject.getJSONObject("session").getString("key");
                        SessionUtil.saveSession(sessionkey, getApplicationContext());

                        navigateToMainActivity();
                    }
                    // Invalid login credentials
                    else {
                        Toast.makeText(getApplicationContext(), R.string.login_invalid_credentials_message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "The server is not responding. Please, contact the admin at alexcreto@gmail.com", Toast.LENGTH_LONG).show();
                }
            }
            // When Email is invalid
            else {
                Toast.makeText(getApplicationContext(), R.string.login_invalid_email_message, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.login_null_fields_message, Toast.LENGTH_LONG).show();
        }

    }


}