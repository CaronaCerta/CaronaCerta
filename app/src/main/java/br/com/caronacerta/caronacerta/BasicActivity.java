package br.com.caronacerta.caronacerta;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.View;

import br.com.caronacerta.caronacerta.fragment.EditarContaFragment;

public class BasicActivity extends Activity {

    public void navigateToLoginActivity() {
        navigateToActivity(LoginActivity.class);
    }

    /**
     * Method which navigates from Register Activity to Login Activity
     */
    public void navigateToLoginActivity(View view) {
        navigateToLoginActivity();
    }

    /**
     * Method gets triggered when Register button is clicked
     *
     * @param view
     */
    public void navigateToRegisterActivity(View view) {
        navigateToActivity(RegisterActivity.class);
    }

    /**
     * Method which navigates from Login Activity to Home Activity
     */
    public void navigateToMainActivity() {
        navigateToActivity(MainActivity.class);
        finish();
    }

    public void navigateToActivity(Class<?> cls) {
        Intent mainIntent = new Intent(getApplicationContext(), cls);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }


    /**
     * Method gets triggered when Edit account button is clicked
     *
     * @param view
     */
    public void navigateToEditarContaFragment(View view) {
        navigateToFragment(R.string.action_profile, new EditarContaFragment());
    }


    public void navigateToFragment(int resId, Fragment fragment) {
        navigateToFragment(getString(resId), fragment);
    }

    public void navigateToFragment(String title, Fragment fragment) {
        setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }
}
