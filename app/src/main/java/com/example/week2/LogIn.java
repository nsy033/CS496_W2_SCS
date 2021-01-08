package com.example.week2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.example.week2.LoginCallback;
import com.facebook.CallbackManager;

import java.util.Arrays;

import static java.security.AccessController.getContext;

public class LogIn extends AppCompatActivity {
    private Context mContext;
    private LoginButton btn_facebook_login;
    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;
    public static String user_name = "";
    public static String user_email = "";
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        mContext = getApplicationContext();

        mCallbackManager = CallbackManager.Factory.create();
        mLoginCallback = new LoginCallback();

        Button button = (Button) findViewById(R.id.get_start);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    Toast.makeText(getBaseContext(), "Please Log in First", Toast.LENGTH_SHORT).show();
                    flag = true;
                }
            }
        });

        flag= true;
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
                flag = false;
            }
        };

        btn_facebook_login = (LoginButton) findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"));
        btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {
            Button button = (Button) findViewById(R.id.get_start);
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}