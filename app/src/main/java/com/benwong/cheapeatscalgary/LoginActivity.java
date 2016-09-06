package com.benwong.cheapeatscalgary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by benwong on 16-09-05.
 */
public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);


        loginButton = (LoginButton) findViewById(R.id.login_button);

        AccessToken token;
        token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            //Means user is not logged in
//            Toast.makeText(getApplicationContext(), "User not logged in " , Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getApplicationContext(), "User is logged in " , Toast.LENGTH_SHORT).show();
            // User is logged in
            goToMain();
        }


        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();

                Profile profile = Profile.getCurrentProfile();
                // App code
                System.out.println("Social Login Result " + loginResult.getAccessToken());
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                System.out.println("Social GraphRequest response" +  response.toString());
                                System.out.println("Social GraphRequest JSON" +  object.toString());

                                goToMain();
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name, last_name ,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        System.out.println("Social " + data);
        System.out.println("Social " + requestCode);
        System.out.println("Social " + resultCode);
    }

    private void goToMain(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}