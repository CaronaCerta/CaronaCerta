package br.com.caronacerta.caronacerta.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.util.RequestUtil;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class HomeFragment extends BasicFragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        if (!SessionUtil.isLoggedIn(getActivity().getApplicationContext())) {
            SessionUtil.logout(getActivity().getApplicationContext());
            Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
            navigateToLoginActivity();
        } else {
            JSONObject jsonObject = RequestUtil.getData("usuario/me", SessionUtil.getToken(getActivity().getApplicationContext()));

            try {
                if (!jsonObject.getBoolean("error")) {
                    JSONObject user = jsonObject.getJSONObject("usuario");
                    String userName = user.getString("nome");
                    String userEmail = user.getString("email");
                    String userBirthDay = user.getString("data_nascimento");
                    String userPhone = user.getString("telefone");
                    String userAddress = user.getString("endereco");
                    String userCity = user.getString("cidade");
                    ((TextView) rootView.findViewById(R.id.title)).append(" " + userName);
                    ((TextView) rootView.findViewById(R.id.email)).append(" " + userEmail);
                    ((TextView) rootView.findViewById(R.id.birthday)).append(" " + userBirthDay);
                    ((TextView) rootView.findViewById(R.id.phone)).append(" " + userPhone);
                    ((TextView) rootView.findViewById(R.id.address)).append(" " + userAddress);
                    ((TextView) rootView.findViewById(R.id.city)).append(" " + userCity);
                } else {
                    SessionUtil.logout(getActivity().getApplicationContext());
                    Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
                    navigateToLoginActivity();
                }
            } catch (Exception e) {
                SessionUtil.logout(getActivity().getApplicationContext());
                Toast.makeText(getActivity().getApplicationContext(), R.string.session_timeout, Toast.LENGTH_LONG).show();
                navigateToLoginActivity();
                e.printStackTrace();
            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle(R.string.home_title);
    }
}
