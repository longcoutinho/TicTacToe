package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;

import java.util.ArrayList;



public class LoginPage extends AppCompatActivity {
    Button signupbutton;
    TextView notify;
    EditText username, password;
    private LoginButton mBtnLoginFacebook;
    private CallbackManager mCallbackManager;
    public static String playername;
    public static String playerusername;
    public static int match_won;
    private DatabaseReference mDatabase;
    public ArrayList<Account> listofAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        signupbutton = (Button) findViewById(R.id.bt_signup);
        notify = (TextView) findViewById(R.id.loginnotify);
        notify.setText("");
        username = (EditText) findViewById(R.id.et_usernamelogin);
        password = (EditText) findViewById(R.id.et_passwordlogin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listofAccounts = new ArrayList<>();
        Query newq = mDatabase.child("listaccount");
        newq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Account>> t = new GenericTypeIndicator<ArrayList<Account>>() {};
                ArrayList<Account> yourStringArray = snapshot.getValue(t);
                //Toast.makeText(getContext(),yourStringArray.get(0).getName(),Toast.LENGTH_LONG).show();

                try {
                    listofAccounts.clear();
                    for(int i = 0; i < yourStringArray.size(); i++)
                    {
                        listofAccounts.add(yourStringArray.get(i));
                    }
                }
                catch (NullPointerException e) {

                }

                //Log.v("long", "take data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.btn_login_facebook);

        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("long", "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
                //Profile profile = Profile.getCurrentProfile();
                mDatabase.child("facebookname").setValue(loginResult.getAccessToken().getUserId());
                mDatabase.child("token").setValue(loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                String name = object.optString("name");
                                //mDatabase.child("name").setValue(id);
                                String id = object.optString("id");
                                LoginPage.playername = name;
                                LoginPage.playerusername = id;
                                int test = 0;
                                for(int i = 0; i < listofAccounts.size(); i++) {
                                    if (listofAccounts.get(i).getUsername().equals(id)) {
                                        test = 1;
                                        LoginPage.match_won = listofAccounts.get(i).getMatch_win();
                                        break;
                                    }
                                }
                                if (test == 0) {
                                    Account newaccount = new Account(name, id, "1", 0);
                                    listofAccounts.add(newaccount);
                                    mDatabase.child("listaccount").setValue(listofAccounts);
                                }
                                change_intent();
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v("long", "Login canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.v("long", "Login failed.");
            }
        });

        //mBtnLoginFacebook.setUser

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void change_intent() {
        Intent newintent = new Intent(this, InfoPage.class);
        startActivity(newintent);
    }

    public void newsignup(View v) {
        Intent newintent = new Intent(this, SignUpPage.class);
        startActivity(newintent);
    }
    public void newlogin(View v) {
        if (username.getText().toString().length() < 6 || username.getText().toString().length() > 12) {
            notify.setText("Username 's length between 6 and 12 words");
        }
        else if (password.getText().toString().length() < 6 || username.getText().toString().length() > 12) {
            notify.setText("Password 's length between 6 and 12 words");
        }
        else {
            String inputusername = username.getText().toString();
            String inputpassword = password.getText().toString();
            int kt = 0;
            for(int i = 0; i < listofAccounts.size() ; i++)
                if (listofAccounts.get(i).getUsername().equals(inputusername) && listofAccounts.get(i).getPassword().equals(inputpassword)) {
                    LoginPage.playername = listofAccounts.get(i).getName();
                    kt = 1;
                    LoginPage.playerusername = inputusername;
                    LoginPage.match_won = listofAccounts.get(i).getMatch_win();
                    Intent newintent = new Intent(this, InfoPage.class);
                    startActivity(newintent);
                    break;
                }
            if (kt == 1) {
                notify.setText("Login successfully!");
            }
            else {
                notify.setText("Invalid username or password");
            }
        }
    }


}