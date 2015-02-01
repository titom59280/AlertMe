package com.alertme.projet.alertme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Setting_email_phone extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_setting_email_phone);
        this.setTitle(R.string.phone_and_email);
        final Button next = (Button) findViewById(R.id.button_phone_email_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting_email_phone.this, AcceptCategoryFromCommunity.class);
                startActivity(intent);
            }
        });

        final Button previous = (Button) findViewById(R.id.button_phone_email_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting_email_phone.this, SettingWelcomePage.class);
                startActivity(intent);
            }
        });

    }



}
