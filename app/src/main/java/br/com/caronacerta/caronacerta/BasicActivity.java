package br.com.caronacerta.caronacerta;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.caronacerta.caronacerta.fragment.CriarCarroFragment;
import br.com.caronacerta.caronacerta.fragment.CriarMotoristaFragment;
import br.com.caronacerta.caronacerta.fragment.EditarCarroFragment;
import br.com.caronacerta.caronacerta.fragment.EditarContaFragment;
import br.com.caronacerta.caronacerta.fragment.EditarMotoristaFragment;
import br.com.caronacerta.caronacerta.fragment.VisualizarCarroFragment;

public abstract class BasicActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(getLayoutResource());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        toolbar.setNavigationIcon(iconRes);
    }

    public void navigateToLoginActivity() {
        navigateToActivity(LoginActivity.class, true);
    }

    public void navigateToSettingsActivity() {
        navigateToActivity(SettingsActivity.class);
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
        navigateToActivity(MainActivity.class, true);
    }

    public void navigateToActivity(Class<?> cls) {
        navigateToActivity(cls, false);
    }

    public void navigateToActivity(Class<?> cls, boolean finish) {
        Intent mainIntent = new Intent(getApplicationContext(), cls);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);

        if (finish) {
            finish();
        }
    }


    /**
     * Method gets triggered when Edit account button is clicked
     *
     * @param view
     */
    public void navigateToEditarContaFragment(View view) {
        navigateToFragment(new EditarContaFragment());
    }

    public void navigateToEditarMotoristaFragment(View view) {
        navigateToFragment(new EditarMotoristaFragment());
    }

    public void navigateToCriarMotoristaFragment(View view) {
        navigateToFragment(new CriarMotoristaFragment());
    }

    public void navigateToCriarCarroFragment(View view) {
        navigateToFragment(new CriarCarroFragment());
    }

    public void navigateToEditarCarroFragment(View view) {
        navigateToFragment(new EditarCarroFragment());
    }

    public void navigateToVisualizarCarroFragment(View view) {
        navigateToFragment(new VisualizarCarroFragment());
    }

    public void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
