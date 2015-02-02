package com.alertme.projet.alertme;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


public class AcceptCategoryFromCommunity extends ActionBarActivity {
    private Switch mySwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_category_from_community);

        mySwitch = (Switch) findViewById(R.id.accept_choice);
        mySwitch.setChecked(true);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //choice is on true
                }else{
                    //choice is on false
                }
            }
        });
        this.setTitle(R.string.title_choice_other_categories);

        final Button next = (Button) findViewById(R.id.button_accept_categories_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceptCategoryFromCommunity.this, YourAlert.class);
                startActivity(intent);
            }
        });

        final Button previous = (Button) findViewById(R.id.button_accept_categories_previous);
        previous.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Intent intent = new Intent(AcceptCategoryFromCommunity.this,Setting_email_phone.class);
                startActivity(intent);
            }
        });
    }
}
