package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.MainActivity;
import br.com.caronacerta.caronacerta.R;
import br.com.caronacerta.caronacerta.util.SessionUtil;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        String user_name = prefs.getString("user_name", null);
        TextView user_name_view = (TextView) rootView.findViewById(R.id.welcome);
        if (user_name.length() == 0) {
            SessionUtil.logout(getActivity().getApplicationContext());
            Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginActivity);
        }
        else user_name_view.append(" " + user_name);

        TextView num_caronas_view = (TextView) rootView.findViewById(R.id.num_caronas);
        Integer num_caronas = MainActivity.caronasELVGroup.size();
        num_caronas_view.setText("Voce possui " + num_caronas + " caronas agendadas!");

        return rootView;
    }
}
