package com.alertme.projet.alertme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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

/**
 * Created by Thomas on 11/01/2015.
 */
public class CreateUserFragment extends DialogFragment {


    public final static String URL = "http://www.alerTodo.com/account";
    public VariableGlobalClass variableGlobalClass;
    public static final int TIMEOUT = 8000, TIMEOUTSOCKET =13000;
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        variableGlobalClass = (VariableGlobalClass) getActivity().getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.create_user_alert_dialog,null));
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getActivity(), SettingWelcomePage.class);
                startActivity(intent);
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        variableGlobalClass.setPass("");
                        variableGlobalClass.setUsername("");
                    }
                });
        //Create the AlertDialog object and return it
        return builder.create();
    }

}
