package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.alerTodo.expandablemenu.NewAlertExpandableListAdapter;
import com.alertme.projet.alertme.R;
import com.alertme.projet.alertme.SeeTemplate;
import com.alertme.projet.alertme.VariableGlobalClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 04/04/2015.
 */
public class SeeTemplateFragment extends Fragment{
    ArrayList<String> templateList;

    private ArrayAdapter<String> templateListAdapter ;
    public SeeTemplateFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_templates_alert, container, false);
        ListView templates = (ListView) rootView.findViewById(R.id.alertTemplates);


        final ArrayList<String> templateList = new ArrayList<String>();
        templateListAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.simplerow,templateList);
        getTemplates();
        templates.setAdapter(templateListAdapter);
        setHasOptionsMenu(true);


        templates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = templateList.get(position);
                final VariableGlobalClass variableGlobalClass = (VariableGlobalClass) getActivity().getApplicationContext();
                variableGlobalClass.setTemplate(selectedItem);
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = null;
                fragment = new NewAlertFragment();
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                //Toast.makeText(getActivity(),"Item selectionner : "+selectedItem,Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem item = menu.add(Menu.NONE, R.id.action_bar, 1, R.string.title_new_alert_fragment);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.plus).setEnabled(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }
    void getTemplates()
    {
        templateListAdapter.add("DOG");
        templateListAdapter.add("CAT");
        templateListAdapter.add("HORSE");
        templateListAdapter.add("ELEPHANT");
        templateListAdapter.add("LION");
        templateListAdapter.add("COW");
        templateListAdapter.add("MONKEY");
        templateListAdapter.add("DEER");
        templateListAdapter.add("RABBIT");
        templateListAdapter.add("BEER");
        templateListAdapter.add("DONKEY");
        templateListAdapter.add("LAMB");
        templateListAdapter.add("GOAT");


    }
}
