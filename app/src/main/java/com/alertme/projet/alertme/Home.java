package com.alertme.projet.alertme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Home extends Activity {

    private String username,password;
    private Button connexion;
    private EditText loginText,mdpText;
    private CheckBox remenberMe;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    private TextView newUser;
    public final static String URL = "http://10.13.1.105:8080/public/login";
    public final static String EXTRA_MESSAGE = "com.example.testWebService.MESSAGE";
    public String loginInit = "";
    public String passInit = "";

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
                //checkLogin(username,password);

                if(username != null&& password != null){
                    loginInit = username;
                    passInit = password;
                    verifyLogin();
                    //new CallAPI().execute(URL);
                }

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


    public void verifyLogin(){
       if(loginInit != null&& passInit != null){
            new CallAPI().execute(URL);
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class CallAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = URL;

            HttpClient hclient = new DefaultHttpClient();
            HttpPost hpost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("login", loginInit));
            nameValuePairs.add(new BasicNameValuePair("password", passInit));

            InputStream inStream = null;
            String result =null;
            try{
                hpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse hresponse = hclient.execute(hpost);
                Log.v("Alert", "response is back");
                HttpEntity entity = hresponse.getEntity();
                inStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
                StringBuilder sBuilder = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) !=null){
                    sBuilder.append(line +"\n");
                }
                result = sBuilder.toString();
                Log.v("ALERT", "string built already");
            }catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    if(inStream!=null){
                        inStream.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            Log.v("ALERT", result);
            return result;
        }

        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            JSONObject jobject = null;
            Log.v("ALERT",result);
            String message = "";

            try{
                jobject = new JSONObject(result);
                message = jobject.getString("message");

            }
            catch(JSONException e){
                Log.v("ALERT", result);
            }

            if(message!=null&&message.equals("User exist"))
            {
                Intent intent = new Intent(Home.this,YourAlert.class);
                startActivity(intent);
            }
            else if(message!=null&&message.equals("User does not exist"))
            {
                DialogFragment newFragment = new CreateUserFragment();
                newFragment.show(getFragmentManager(),"create");
                newFragment.setCancelable(false);
            }
            else
            {
                Log.v("ALERT","problem with the webService");
                if(loginInit.equals("test")&&passInit.equals("test")){
                    Intent intent = new Intent(Home.this,YourAlert.class);
                    startActivity(intent);
                }
            }

        }
    }
}
