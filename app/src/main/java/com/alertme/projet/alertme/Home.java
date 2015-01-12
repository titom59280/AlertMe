package com.alertme.projet.alertme;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button connexion = (Button) findViewById(R.id.btnValider);
        final EditText loginText = (EditText) findViewById(R.id.username);
        final EditText mdpText = (EditText) findViewById(R.id.mdp);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = loginText.getText().toString();
                String pass = mdpText.getText().toString();
                checkLogin(log,pass);
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
