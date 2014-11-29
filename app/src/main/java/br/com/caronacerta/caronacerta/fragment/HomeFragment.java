package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.RegisterActivity;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        if (SessionUtil.isLoggedIn(getActivity().getApplicationContext()) == false) {
            SessionUtil.logout(getActivity().getApplicationContext());
            Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
            Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginActivity);
        } else {
            JSONObject jsonObject = RequestUtil.getData("usuario/me", SessionUtil.getToken(getActivity().getApplicationContext()));

            try {
                if (!jsonObject.getBoolean("error")) {
                    JSONObject user = jsonObject.getJSONObject("usuario");
                    String userName = user.getString("nome");
                    String userEmail = user.getString("email");
                    String userBirthDay = user.getString("data_nascimento");
                    String userPhone = user.getString("data_nascimento");
                    String userAddress = user.getString("endereco");
                    String userCity = user.getString("cidade");
                    ((TextView) rootView.findViewById(R.id.welcome)).append(" " + userName);
                    ((TextView) rootView.findViewById(R.id.email)).append(" " + userEmail);
                    ((TextView) rootView.findViewById(R.id.birthday)).append(" " + userBirthDay);
                    ((TextView) rootView.findViewById(R.id.phone)).append(" " + userPhone);
                    ((TextView) rootView.findViewById(R.id.address)).append(" " + userAddress);
                    ((TextView) rootView.findViewById(R.id.city)).append(" " + userCity);
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
        }

        return rootView;
    }
}
