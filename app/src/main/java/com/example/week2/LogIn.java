package com.example.week2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.Arrays;

public class LogIn extends AppCompatActivity {
    private Context mContext;
    private Button btn_custom_login, btn_custom_logout, btn_custom_go;
    static TextView tx;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;
    public static String user_name = "";
    public static String user_email = "";
    private boolean flag = true;
    public static boolean login_s = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        mContext = getApplicationContext();

        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();

        AccessToken.refreshCurrentAccessTokenAsync();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                startTracking();
                updateWithToken(newAccessToken);
                flag=false;
                stopTracking();
            }
        };
        accessTokenTracker.startTracking();
        AccessToken.refreshCurrentAccessTokenAsync();

        btn_custom_login = (Button) findViewById(R.id.btn_custom_login);
        btn_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LogIn.this,
                        Arrays.asList("public_profile", "email"));
                loginManager.registerCallback(mCallbackManager, mLoginCallback);

                accessTokenTracker.startTracking();
                AccessToken.refreshCurrentAccessTokenAsync();
            }
        });

        btn_custom_logout = (Button) findViewById(R.id.btn_custom_logout);
        btn_custom_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                flag=false;
                tx.setText("Please Log In");
                btn_custom_login.setEnabled(true);
                btn_custom_login.setVisibility(getCurrentFocus().VISIBLE);
                btn_custom_logout.setEnabled(false);
                btn_custom_logout.setVisibility(getCurrentFocus().INVISIBLE);
                btn_custom_go.setEnabled(false);
                btn_custom_go.setVisibility(getCurrentFocus().INVISIBLE);
            }
        });

        btn_custom_go = (Button) findViewById(R.id.btn_custom_go);
        btn_custom_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        tx = (TextView) findViewById(R.id.login_text);

        btn_custom_login.setEnabled(true);
        btn_custom_login.setVisibility(getCurrentFocus().VISIBLE);
        btn_custom_logout.setEnabled(false);
        btn_custom_logout.setVisibility(getCurrentFocus().INVISIBLE);
        btn_custom_go.setEnabled(false);
        btn_custom_go.setVisibility(getCurrentFocus().INVISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(login_s) {
/*
            btn_custom_login.setEnabled(false);
            btn_custom_logout.setEnabled(true);
            btn_custom_go.setEnabled(true);
*/
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            if(Profile.getCurrentProfile() != null) {
                if (flag){
                    tx.setText("Hello " + Profile.getCurrentProfile().getFirstName() + Profile.getCurrentProfile().getMiddleName());
                    user_name = Profile.getCurrentProfile().getLastName() + Profile.getCurrentProfile().getFirstName() + Profile.getCurrentProfile().getMiddleName();
                    user_email = Profile.getCurrentProfile().getId();
                }
            }
            btn_custom_login.setEnabled(false);
            btn_custom_login.setVisibility(getCurrentFocus().INVISIBLE);
            btn_custom_logout.setEnabled(true);
            btn_custom_logout.setVisibility(getCurrentFocus().VISIBLE);
            btn_custom_go.setEnabled(true);
            btn_custom_go.setVisibility(getCurrentFocus().VISIBLE);
        }
    }
}