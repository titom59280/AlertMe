package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alerTodo.expandablemenu.ExceptionExpandableListAdapter;
import com.alerTodo.expandablemenu.MyAccountExpandableListAdapter;
import com.alerTodo.expandablemenu.NewAlertExpandableListAdapter;
import com.alertme.projet.alertme.R;

import java.util.ArrayList;
import java.util.List;

public class NewAlertFragment extends Fragment {

    public NewAlertFragment(){}
    NewAlertExpandableListAdapter listAdapter;
    ExceptionExpandableListAdapter exceptionListAdapter;
    ExpandableListView expListView, expExceptionListView;
    List<String> listDataHeader, listDataHeaderException;
    List<String> listDataChild,listDataChildException;
    private int lastExpandedGroupPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_new_alert,container,false);

        View excRootView = inflater.inflate(R.layout.fragment_advanced_parameters,container,false);
        setHasOptionsMenu(true);

        expListView = (ExpandableListView) rootView.findViewById(R.id.expandMenuNewAlertFragment);

        expExceptionListView = (ExpandableListView) excRootView.findViewById(R.id.expandMenuExceptionFragment);
        prepareListData();

        listAdapter = new NewAlertExpandableListAdapter(getActivity(),listDataHeader,listDataChild);

        exceptionListAdapter = new ExceptionExpandableListAdapter(getActivity(),listDataHeaderException,listDataChildException);

        //setting list adapter

        expExceptionListView.setAdapter(exceptionListAdapter);
        expExceptionListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedGroupPosition != -1 && groupPosition != lastExpandedGroupPosition){
                    expExceptionListView.collapseGroup(lastExpandedGroupPosition);

                }
                lastExpandedGroupPosition = groupPosition;
            }
        });

        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedGroupPosition != -1 && groupPosition != lastExpandedGroupPosition){
                    expListView.collapseGroup(lastExpandedGroupPosition);

                }
                lastExpandedGroupPosition = groupPosition;
            }
        });


        return rootView;
    }

    private void prepareListData(){
        listDataHeader = new ArrayList<String>();
        listDataHeaderException = new ArrayList<String>();
        listDataChild = new ArrayList<String>();
        listDataChildException = new ArrayList<String>();

        //adding header data
        listDataHeader.add(getText(R.string.standard).toString());
        listDataHeader.add(getText(R.string.advanced).toString());

        listDataHeaderException.add(getText(R.string.day).toString());
        listDataHeaderException.add(getText(R.string.month).toString());


        listDataChild.add(listDataHeader.get(0));

        listDataChildException.add(listDataHeaderException.get(0));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater){
        MenuItem item = menu.add(Menu.NONE,R.id.action_bar,1,R.string.title_new_alert_fragment);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.plus).setEnabled(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
