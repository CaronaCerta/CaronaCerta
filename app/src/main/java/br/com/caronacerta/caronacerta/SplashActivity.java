package br.com.caronacerta.caronacerta;

import android.os.Bundle;

import br.com.caronacerta.caronacerta.util.SessionUtil;


public class SplashActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 2000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    checkLogin();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };

        logoTimer.start();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    private void checkLogin() {
        // User is already logged in
        if (SessionUtil.isLoggedIn(getApplicationContext())) {
            navigateToMainActivity();
        } else {
            navigateToLoginActivity();
        }
    }

}
