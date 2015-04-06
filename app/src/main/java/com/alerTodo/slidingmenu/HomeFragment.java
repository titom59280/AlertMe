package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alerTodo.expandablemenu.NewAlertExpandableListAdapter;
import com.alerTodo.expandablemenu.NewHomeExpandableListAdapter;
import com.alertme.projet.alertme.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public final static String URL = "http://www.alerTodo.com:8080/account";
    public Button start;
    private boolean hasAlert = true;
    public TextView data;

    public HomeFragment() {
    }
    NewHomeExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> listDataChild;
    private int lastExpandedGroupPosition = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        expListView = (ExpandableListView) rootView.findViewById(R.id.expandMenuHomeFragment);

        prepareListData();

        listAdapter = new NewHomeExpandableListAdapter(getActivity(), listDataHeader, listDataChild, getFragmentManager());
        start = (Button) rootView.findViewById(R.id.go);
        setHasOptionsMenu(true);

        //setting list adapter

        if(hasAlert) {

            start.setVisibility(View.INVISIBLE);
            expListView.setAdapter(listAdapter);
            expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (lastExpandedGroupPosition != -1 && groupPosition != lastExpandedGroupPosition) {
                        expListView.collapseGroup(lastExpandedGroupPosition);

                    }
                    lastExpandedGroupPosition = groupPosition;
                }
            });
        }
        else{
            start.setVisibility(View.VISIBLE);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //new TestAPI().execute();
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = null;
                    fragment = new NewAlertFragment();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

                    //update selected item and title then close the drawer
                    //mDrawerList.setItemChecked(position, true);
                    //mDrawerList.setSelection(position);
                    //setTitle(navMenuTitles[position]);
                }
            });
        }

        return rootView;
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList<String>();

        //adding header data
        listDataHeader.add("Personnel");
        listDataHeader.add("Professionnel");
        listDataHeader.add("Administratif");


        listDataChild.add(listDataHeader.get(0));


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem item = menu.add(Menu.NONE, R.id.action_bar_container, 1, R.string.title_alert_fragment);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_home).setEnabled(false);


        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private class TestAPI extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://www.alerTodo.com:8080/account");
            httpGet.setHeader("Content-Type", "application/json");
            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e(HomeFragment.class.toString(), "failed json object");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            data.setText(result);


        }
    }
}
