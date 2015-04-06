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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Home extends Activity {

    public static final int INT = 1000000;
    private String username,password;
    private ProgressBar progress;
    private Button connexion;
    private EditText loginText,mdpText;
    private CheckBox remenberMe;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private boolean saveLogin;
    private TextView newUser;
    public final static String URL = "http://www.alerTodo.com/account/on";
    public final static String EXTRA_MESSAGE = "com.example.testWebService.MESSAGE";
    public String loginInit = "";
    public String passInit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progress = (ProgressBar) findViewById(R.id.progressBarHome);
        connexion = (Button) findViewById(R.id.btnValider);
        loginText = (EditText) findViewById(R.id.username);
        mdpText = (EditText) findViewById(R.id.mdp);
        newUser = (TextView) findViewById(R.id.new_user);
        remenberMe = (CheckBox) findViewById(R.id.saveLoginCheckbox);
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        progress.setVisibility(View.INVISIBLE);
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
                progress.setVisibility(View.VISIBLE);
                connexion.setVisibility(View.INVISIBLE);
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
    private class CallAPI extends AsyncTask<String, Integer, String> {

        @Override
        protected void onProgressUpdate(Integer... values){
            progress.setProgress(values[0]);

        }
        @Override
        protected String doInBackground(String... params) {
            String url = URL;

            InputStream inStream = null;
            String result =null;

            try{
                //HttpPost hpost = new HttpPost(url);
                HttpPut httpPut = new HttpPut(url);
                httpPut.setHeader("Content-Type","application/json");
                HttpParams httpParams = new BasicHttpParams();
                int timeout =8000;
                HttpConnectionParams.setConnectionTimeout(httpParams,timeout);
                int timeoutSocket = 13000;
                HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket);
                HttpClient hclient = new DefaultHttpClient(httpParams);

                //List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                //nameValuePairs.add(new BasicNameValuePair("user", loginInit));
                //nameValuePairs.add(new BasicNameValuePair("pass", passInit));

                //hpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                JSONStringer json = new JSONStringer().object().key("user").value(loginInit).key("pass").value(passInit).endObject();
                StringEntity entityForPut = new StringEntity(json.toString());
                httpPut.setEntity(entityForPut);
                HttpResponse hresponse = hclient.execute(httpPut);

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
            }catch(ConnectTimeoutException e){
                Log.v("ALERT","problem with the webService");
                if(loginInit.equals("test")&&passInit.equals("test")){
                    Intent intent = new Intent(Home.this,YourAlert.class);
                    startActivity(intent);
                }
            }
            catch(Exception e){
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
            return result;
        }

        protected void onPostExecute(String result) {

            progress.setVisibility(View.INVISIBLE);
            connexion.setVisibility(View.VISIBLE);
            if(result ==null){
                Log.v("ALERT","problem with the webService");
                Toast.makeText(getApplicationContext(),"problème avec le webservice ok pour dev",1000).show();
                if(loginInit.equals("test")&&passInit.equals("test")){
                    Intent intent = new Intent(Home.this,YourAlert.class);
                    startActivity(intent);
                }
            }else{
                super.onPostExecute(result);
                JSONObject jobject = null;
                String message = "";

                try{
                    jobject = new JSONObject(result);
                    message = jobject.getString("data");

                }
                catch(JSONException e){
                    Log.v("ALERT", result);
                }

                if(message!=null&&message.equals("DONE"))
                {
                    Intent intent = new Intent(Home.this,YourAlert.class);
                    startActivity(intent);
                }
                else if(message!=null&&message.equals("{\"user\":[\"This login does not exist.\"]}")||message.equals("{\"user\":[\"The login or password is false.\"]}"))
                {
                    DialogFragment newFragment = new CreateUserFragment();
                    newFragment.show(getFragmentManager(),"create");
                    newFragment.setCancelable(false);
                }

            }



        }
    }
}