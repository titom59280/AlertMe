package com.alertme.projet.alertme;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Setting_email_phone extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_setting_email_phone);
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
                if(!email.isEmpty()&&!phone.isEmpty()){
                    settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3PhoneAndEmailConfirmDialog).toString(),AcceptCategoryFromCommunity.class);
                }else if(!email.isEmpty()&&phone.isEmpty()){
                    settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1EmailConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3EmailConfirmDialog).toString(),AcceptCategoryFromCommunity.class);
                }else if(email.isEmpty()&&!phone.isEmpty()){
                    settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1SmsConfirmDialog).toString(),getText(R.string.contentPart2PhoneAndEmailConfirmDialog).toString(),getText(R.string.contentPart3PhoneConfirmDialog).toString(),AcceptCategoryFromCommunity.class);
                }else{
                    settingsPhoneEmail = new ConfirmDialog(getText(R.string.titleConfirmDialog).toString(),getText(R.string.contentPart1NoneConfirmDialog).toString(),"","",AcceptCategoryFromCommunity.class);
                }
                settingsPhoneEmail.show(getFragmentManager(),"create");
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
