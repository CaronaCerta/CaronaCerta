package br.com.caronacerta.caronacerta.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

import br.com.caronacerta.caronacerta.LoginActivity;
import br.com.caronacerta.caronacerta.R;

public class BasicFragment extends Fragment {
    public void navigateToFragment(int resId, Fragment fragment) {
        navigateToFragment(getString(resId), fragment);
    }

    public void navigateToFragment(String title, Fragment fragment) {
        getActivity().setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void navigateToLoginActivity() {
        navigateToActivity(LoginActivity.class);
    }

    public void navigateToActivity(Class<?> cls) {
        Intent loginActivity = new Intent(getActivity().getApplicationContext(), cls);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginActivity);
    }
}
