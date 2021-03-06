package com.alerTodo.expandablemenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alerTodo.slidingmenu.NewAlertFragment;
import com.alerTodo.slidingmenu.SeeTemplateFragment;
import com.alertme.projet.alertme.R;
import com.alertme.projet.alertme.SeeTemplate;
import com.alertme.projet.alertme.VariableGlobalClass;
import com.alertme.projet.alertme.YourAlert;

import java.util.Calendar;
import java.util.List;

/**
 * Created by tgraveleine on 03/02/2015.
 */
public class NewAlertExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;

    private EditText title;
    private EditText content;
    private List<String> _listDataHeader;//titles
    private List<String> _listDataChild;
    private Button seeTemplates;



    private Spinner choiceCategory, choiceRepeatSpinner;
    private DatePicker dayNotif;

    private int year;
    private int month;
    private int day;
    private FragmentManager FmBase;


    public NewAlertExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listDataChild,FragmentManager FmMain) {
        this._context = context;
        this._listDataChild = listDataChild;
        this._listDataHeader = listDataHeader;

        this.FmBase =FmMain;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lbListHeader = (TextView) convertView.findViewById(R.id.lbListHeaderMyAccountFragment);
        lbListHeader.setTypeface(null, Typeface.BOLD);
        lbListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {


        final LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (groupPosition) {
            case 0:
                final VariableGlobalClass variableGlobalClass = (VariableGlobalClass) _context.getApplicationContext();
                convertView = inflater.inflate(R.layout.fragment_standard_parameters, null);
                choiceCategory = (Spinner) convertView.findViewById(R.id.choiceCategory);
                seeTemplates = (Button) convertView.findViewById(R.id.seeTemplates);
                title = (EditText) convertView.findViewById(R.id.alertTitle);
                content = (EditText) convertView.findViewById(R.id.alertContent);
                if(!(variableGlobalClass.getTemplate() ==null)){
                    content.setText(variableGlobalClass.getTemplate().toString());
                }
                if(!(variableGlobalClass.getTitleAlert()==null)){
                    title.setText(variableGlobalClass.getTitleAlert().toString());
                }
                String[] choice = {"", "Personnel", "Professionnel", "Administratif"};

                ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this._context, android.R.layout.simple_spinner_item, choice);
                dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                if (choiceCategory != null) {
                    choiceCategory.setAdapter(dataAdapterR);
                }

                final Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                dayNotif = (DatePicker) convertView.findViewById(R.id.dayNotif);
                dayNotif.init(year, month, day, null);

                seeTemplates.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        variableGlobalClass.setTitleAlert(title.getText().toString());
                        if(content!=null){
                            variableGlobalClass.setTemplate(content.getText().toString());
                        }
                        Fragment fragment = new SeeTemplateFragment();
                        FragmentManager fragmentManager = FmBase;
                        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    }
                });
                break;
            case 1:
                convertView = inflater.inflate(R.layout.fragment_advanced_parameters,null);
                choiceRepeatSpinner = (Spinner) convertView.findViewById(R.id.choiceRepeat);
                String[] choiceRepeat = {"", this._context.getString(R.string.day),this._context.getString(R.string.week),this._context.getString(R.string.month),this._context.getString(R.string.year)};

                    ArrayAdapter<String> dataAdapterRepeat = new ArrayAdapter<String>(this._context,android.R.layout.simple_spinner_item,choiceRepeat);
                    dataAdapterRepeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if(choiceRepeatSpinner!=null) {
                    choiceRepeatSpinner.setAdapter(dataAdapterRepeat);
                    }
                break;
            case 2:
                convertView = inflater.inflate(R.layout.fragment_templates_alert,null);
                break;
            default:
                break;
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
