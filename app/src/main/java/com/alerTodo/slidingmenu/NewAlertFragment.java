package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.alertme.projet.alertme.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NewAlertFragment extends Fragment {

    public NewAlertFragment(){}
    private Spinner choiceCategory;
    private DatePicker dayNotif;

    private int year;
    private int month;
    private int day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_new_alert,container,false);
        setHasOptionsMenu(true);

        choiceCategory = (Spinner) rootView.findViewById(R.id.choiceCategory);
        String[] choice = {"","Personnel", "Professionnel", "Administratif"};

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,choice);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(choiceCategory!=null) {
            choiceCategory.setAdapter(dataAdapterR);
        }

        final Calendar cal = GregorianCalendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        dayNotif = (DatePicker) rootView.findViewById(R.id.dayNotif);
        dayNotif.init(year, month, day,null);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater){
        MenuItem item = menu.add(Menu.NONE,R.id.action_bar,1,R.string.title_new_alert_fragment);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.plus).setEnabled(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
