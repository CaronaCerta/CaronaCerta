package br.com.caronacerta.caronacerta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
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
public class LoginActivity extends Activity {
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


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Welcome to the Udacity preview of the Right Ride app.\n" +
                "This app was designed for the Brazilian market, " +
                "therefor it contains location-based elements targeting " +
                "the local region. " +
                "It was exclusively translated for this assignment.\n\n" +
                "Please, keep in mind that it relies heavily on server side " +
                "operations that are not fully operational yet. Some features " +
                "will be missing.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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

                if (!jsonObject.getBoolean("error")) {
                    String sessionkey = jsonObject.getJSONObject("session").getString("key");
                    String user_name = jsonObject.getJSONObject("usuario").getString("nome");
                    SessionUtil.saveSession(sessionkey, getApplicationContext());

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_name",user_name);
                    editor.apply();

                    navigateToMainActivity();
                }
                // Invalid login credentials
                else {
                    Toast.makeText(getApplicationContext(), R.string.login_invalid_credentials_message, Toast.LENGTH_LONG).show();
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

    /**
     * Method gets triggered when Register button is clicked
     *
     * @param view
     */
    public void navigateToRegisterActivity(View view) {
        Intent loginIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigateToMainActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }
}