package com.alertme.projet.alertme;

import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Thomas on 11/04/2015.
 */
public class NotificationService extends Service {
    private static final String TAG = "NotificationService" ;
    private static final String URL = "Url of webservice check notif";

    private final int TIMEBETWEENCHECK = 60000;
    private final int TIMEOUT =8000;
    private final int TIMEOUTSOCKET = 13000;
    private Timer timer;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");
        MyNotification myNotification = new MyNotification();
        timer = new Timer();

        timer.schedule(myNotification, TIMEBETWEENCHECK,TIMEBETWEENCHECK);
    }
    class MyNotification extends TimerTask {
        public void run(){
            new checkForNotification().execute();
            generateNotification(getApplicationContext(), "Hello is a new notifications");
        }
    }

    private class checkForNotification extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String url = URL;

            InputStream inStream = null;
            String result =null;

            try{
                //method to call webservice
            /*}catch(ConnectTimeoutException e){
                Log.v("ALERT","problem with the webService");
                //when timeOutException*/
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


            if(result ==null){
                //no response
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

                //if response generateNotification

            }



        }
    }
    private void generateNotification(Context context, String message) {

        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Notification notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, Home.class), 0);

        // To support 2.3 os, we use "Notification" class and 3.0+ os will use
        // "NotificationCompat.Builder" class.
        if (currentapiVersion < android.os.Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification(icon, message, 0);
            notification.setLatestEventInfo(context, appname, message,
                    contentIntent);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify((int) when, notification);

        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    context);
            notification = builder.setContentIntent(contentIntent)
                    .setSmallIcon(icon).setTicker(appname).setWhen(0)
                    .setAutoCancel(true).setContentTitle(appname)
                    .setContentText(message).build();

            notificationManager.notify((int) when, notification);

        }

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
        this.timer.cancel();
    }
}
