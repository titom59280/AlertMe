package com.alertme.projet.alertme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class Home extends Activity {

    private String username,password;
    private Button connexion;
    private EditText loginText,mdpText;
    private CheckBox remenberMe;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    private TextView newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        connexion = (Button) findViewById(R.id.btnValider);
        loginText = (EditText) findViewById(R.id.username);
        mdpText = (EditText) findViewById(R.id.mdp);
        newUser = (TextView) findViewById(R.id.new_user);
        remenberMe = (CheckBox) findViewById(R.id.saveLoginCheckbox);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin",false);
        if(saveLogin == true){
            loginText.setText(loginPreferences.getString("username",""));
            mdpText.setText(loginPreferences.getString("password",""));
            remenberMe.setChecked(true);
        }

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(loginText.getWindowToken(), 0);

                username = loginText.getText().toString();
                password = mdpText.getText().toString();

                if(remenberMe.isChecked()){
                    loginPrefsEditor.putBoolean("saveLogin",true);
                    loginPrefsEditor.putString("username",username);
                    loginPrefsEditor.putString("password",password);
                    loginPrefsEditor.commit();
                }else{
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
                checkLogin(username,password);

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newUserFragment = new CreateNewUserFragment();
                newUserFragment.show(getFragmentManager(),"create");
            }
        });
    }


    public void checkLogin(String login, String password){
        if(login.equals("test")&& password.equals("test")){
            Intent intent = new Intent(Home.this,YourAlert.class);
            startActivity(intent);
        }
        else{
             DialogFragment newFragment = new CreateUserFragment();
             newFragment.show(getFragmentManager(),"create");
             newFragment.setCancelable(false);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
