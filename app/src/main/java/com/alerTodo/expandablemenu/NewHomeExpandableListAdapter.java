package com.alerTodo.expandablemenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alerTodo.slidingmenu.SeeTemplateFragment;
import com.alertme.projet.alertme.R;
import com.alertme.projet.alertme.VariableGlobalClass;

import java.util.Calendar;
import java.util.List;

/**
 * Created by tgraveleine on 03/02/2015.
 */
public class NewHomeExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;

    private List<String> _listDataHeader;//titles
    private List<String> _listDataChild;
    private Button seeTemplates;



    private FragmentManager FmBase;


    public NewHomeExpandableListAdapter(Context context, List<String> listDataHeader, List<String> listDataChild, FragmentManager FmMain) {
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
                convertView = inflater.inflate(R.layout.fragment_alert_list, null);

                break;
            case 1:
                convertView = inflater.inflate(R.layout.fragment_alert_list,null);

                break;
            case 2:
                convertView = inflater.inflate(R.layout.fragment_alert_list,null);
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
