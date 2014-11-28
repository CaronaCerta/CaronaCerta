package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.util.SessionUtil;
import br.com.caronacerta.caronacerta.util.SharedPreferencesUtil;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefs = SharedPreferencesUtil.getPref(this.getActivity());
        String userName = prefs.getString("name", null);
        TextView userNameView = (TextView) rootView.findViewById(R.id.welcome);
        if (userName.length() == 0) {
            SessionUtil.logout(getActivity().getApplicationContext());
            Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginActivity);
        }
        else userNameView.append(" " + userName);

        return rootView;
    }
}
