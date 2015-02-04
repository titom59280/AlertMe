package com.alerTodo.expandablemenu;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alertme.projet.alertme.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tgraveleine on 03/02/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;//titles
    private List<String> _listDataChild;
    Spinner choiceNewCode;


    public ExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listDataChild){
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
                    convertView = inflater.inflate(R.layout.fragment_personal_informations, null);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.fragment_confirmations,null);
                    choiceNewCode =(Spinner) convertView.findViewById(R.id.choiceNewConfirmationCode);

                    String[] lChoice = {"E-mail","SMS"};

                    ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this._context,android.R.layout.simple_spinner_item,lChoice);
                    dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if(choiceNewCode!=null) {
                        choiceNewCode.setAdapter(dataAdapterR);
                    }
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.fragment_home, null);
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
