package com.alerTodo.expandablemenu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alertme.projet.alertme.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by tgraveleine on 03/02/2015.
 */
public class NewAlertExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;//titles
    private List<String> _listDataChild;

    private int lastExpandedGroupPosition = -1;
    List<String> listDataHeaderException;
    List<String>listDataChildException;
    private Spinner choiceCategory;
    private Spinner choiceRepeatSpinner;
    private DatePicker dayNotif;

    ExceptionExpandableListAdapter exceptionListAdapter;
    ExpandableListView expExceptionListView;

    private int year;
    private int month;
    private int day;


    public NewAlertExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listDataChild){
        this._context = context;
        this._listDataChild = listDataChild;
        this._listDataHeader = listDataHeader;
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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);
        }

        TextView lbListHeader = (TextView) convertView.findViewById(R.id.lbListHeaderMyAccountFragment);
        lbListHeader.setTypeface(null, Typeface.BOLD);
        lbListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (groupPosition){
                case 0:
                    convertView = inflater.inflate(R.layout.fragment_standard_parameters, null);
                    choiceCategory = (Spinner) convertView.findViewById(R.id.choiceCategory);
                    //choiceRepeatSpinner = (Spinner) rootView.findViewById(R.id.choiceRepeatMode);
                    String[] choice = {"","Personnel", "Professionnel", "Administratif"};
                    //String[] choiceRepeat = {"", getString(R.string.day),getString(R.string.week),getString(R.string.month),getString(R.string.year)};

                    /*ArrayAdapter<String> dataAdapterRepeat = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,choiceRepeat);
                    dataAdapterRepeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if(choiceRepeatSpinner!=null) {
                    choiceRepeatSpinner.setAdapter(dataAdapterRepeat);
                    }*/

                    ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this._context,android.R.layout.simple_spinner_item,choice);
                    dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if(choiceCategory!=null) {
                        choiceCategory.setAdapter(dataAdapterR);
                    }

                    final Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);

                    dayNotif = (DatePicker) convertView.findViewById(R.id.dayNotif);
                    dayNotif.init(year, month, day,null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.fragment_advanced_parameters,null);

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
