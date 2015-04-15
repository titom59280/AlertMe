package com.alertme.projet.alertme;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SettingEmailPhone extends ActionBarActivity {
    private final String regexEmail = "[a-zA-Z0-9_\\.\\+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-\\.]+";
    private final String regexPhone = "(0|(\\\\+33)|(0033))[1-9][0-9]{8}";
    private final int TIMEOUT =8000;
    private final int TIMEOUTSOCKET = 13000;

    private final static String URL = "http://www.alerTodo.com/account/on";
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_setting_email_phone);

        progress = (ProgressBar) findViewById(R.id.progressBarSetting);
        progress.setVisibility(View.INVISIBLE);
        final EditText mail = (EditText) findViewById(R.id.email);
        final EditText phoneEdit = (EditText) findViewById(R.id.phone);
        this.setTitle(R.string.phone_and_email);
        final Button next = (Button) findViewById(R.id.button_phone_email_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String phone = phoneEdit.getText().toString();
                DialogFragment settingsPhoneEmail;
                DialogFragment settingsPhone = new WrongPhoneFragment();
                DialogFragment settingsEmail = new WrongEmailFragment();
                final VariableGlobalClass variableGlobalClass = (VariableGlobalClass) getApplicationContext();
                variableGlobalClass.setMail(email);
                variableGlobalClass.setPhone(phone);
                if(!email.isEmpty()&&!phone.isEmpty()){
                    if(email.matches(regexEmail)){
                        if(phone.matches(regexPhone)){
                            progress.setVisibility(View.VISIBLE);
                            new CallAPI().execute();
                            settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3PhoneAndEmailConfirmDialog).toString(),YourAlert.class);
                            settingsPhoneEmail.show(getFragmentManager(),"Attention");
                            settingsPhoneEmail.setCancelable(false);
                        }
                        else{
                            settingsPhone.show(getFragmentManager(),"Attention");
                        }
                    }
                    else{

                        settingsEmail.show(getFragmentManager(),"Attention");
                    }

                }else if(!email.isEmpty()&&phone.isEmpty()){
                    if(email.matches(regexEmail)){

                        progress.setVisibility(View.VISIBLE);
                        new CallAPI().execute();
                        settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1EmailConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3EmailConfirmDialog).toString(),YourAlert.class);
                        settingsPhoneEmail.show(getFragmentManager(),"Attention");
                        settingsPhoneEmail.setCancelable(false);
                    }
                    else{
                        settingsEmail.show(getFragmentManager(),"Attention");
                    }
                }else if(email.isEmpty()&&!phone.isEmpty()){
                    if(phone.matches(regexPhone)){

                        progress.setVisibility(View.VISIBLE);
                        new CallAPI().execute();
                        settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1SmsConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3PhoneAndEmailConfirmDialog).toString(),YourAlert.class);
                        settingsPhoneEmail.show(getFragmentManager(),"Attention");
                        settingsPhoneEmail.setCancelable(false);
                    }
                    else{
                        settingsPhone.show(getFragmentManager(),"Attention");
                    }
                }else{

                    progress.setVisibility(View.VISIBLE);
                    new CallAPI().execute();
                    settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1NoneConfirmDialog).toString(),"","",YourAlert.class);
                    settingsPhoneEmail.show(getFragmentManager(),"Attention");
                    settingsPhoneEmail.setCancelable(false);
                }
            }
        });

        final Button previous = (Button) findViewById(R.id.button_phone_email_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingEmailPhone.this, SettingWelcomePage.class);
                startActivity(intent);
            }
        });

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
                int timeout = TIMEOUT;
                HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
                int timeoutSocket = TIMEOUTSOCKET;
                HttpConnectionParams.setSoTimeout(httpParams,timeoutSocket);
                HttpClient hclient = new DefaultHttpClient(httpParams);

                //List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                //nameValuePairs.add(new BasicNameValuePair("user", loginInit));
                //nameValuePairs.add(new BasicNameValuePair("pass", passInit));

                //hpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                VariableGlobalClass variableGlobalClass = (VariableGlobalClass) getApplicationContext();
                JSONStringer json = new JSONStringer().object().key("user").value(variableGlobalClass.getUsername()).key("pass").value(variableGlobalClass.getPass()).key("email").value(variableGlobalClass.getEmail()).key("phone").value(variableGlobalClass.getPhone()).endObject();
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
            if(result ==null){
                Log.v("ALERT","problem with the webService");
                Toast.makeText(getApplicationContext(), "problème avec le webservice ok pour dev", Toast.LENGTH_LONG).show();

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
                    //Intent intent = new Intent(SettingEmailPhone.this,YourAlert.class);
                    //startActivity(intent);
                }
                else if(message!=null&&message.equals("{\"user\":[\"This login does not exist.\"]}")||message.equals("{\"user\":[\"The login or password is false.\"]}"))
                {
                    //A voir
                }

            }



        }
    }


}
