package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.alerTodo.expandablemenu.ExpandableListAdapter;
import com.alertme.projet.alertme.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAccountFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataChild;
    private int lastExpandedGroupPosition = -1;

    public MyAccountFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_my_account,container,false);


        setHasOptionsMenu(true);

        expListView = (ExpandableListView) rootView.findViewById(R.id.expandMenuMyAccountFragment);

        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listDataChild);

        //setting list adapter
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
        listDataChild = new ArrayList<String>();

        //adding header data
        listDataHeader.add(getText(R.string.personal_informations).toString());
        listDataHeader.add(getText(R.string.confirmations).toString());
        listDataHeader.add(getText(R.string.stats).toString());



        listDataChild.add(listDataHeader.get(0));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater){
        MenuItem item = menu.add(Menu.NONE,R.id.action_bar,1,R.string.title_my_account_fragment);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.user).setEnabled(false);
        

        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
