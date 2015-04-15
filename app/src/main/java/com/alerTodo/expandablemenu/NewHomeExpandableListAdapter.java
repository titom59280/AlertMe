package com.alerTodo.expandablemenu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alerTodo.slidingmenu.SeeTemplateFragment;
import com.alertme.projet.alertme.R;
import com.alertme.projet.alertme.VariableGlobalClass;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tgraveleine on 03/02/2015.
 */
public class NewHomeExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;

    private List<String> _listDataHeader;//titles
    private HashMap<String, List<String>> _listDataChild;

    public ImageView editAlert;
    public ImageView removeAlert;
    private TextView title;
    private TextView content;


    private FragmentManager FmBase;


    public NewHomeExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listDataChild, FragmentManager FmMain) {
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
    public int getChildrenCount(int groupPosition){return this._listDataChild.get(this._listDataHeader.get(groupPosition))
            .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {


        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_alert_list, parent,false);
        }

        removeAlert = (ImageView) convertView.findViewById(R.id.delete_btn);
        editAlert = (ImageView) convertView.findViewById(R.id.edit_btn);

        removeAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(_context);

                LayoutInflater infalInflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View confirmView = infalInflater.inflate(R.layout.action_alert_dialog,null);
                title = (TextView) confirmView.findViewById(R.id.titleConfirm);
                content = (TextView) confirmView.findViewById(R.id.contentConfirm);
                title.setText(R.string.title_confirm_delete);
                content.setText(R.string.content_confirm_delete);
                builder.setView(confirmView);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                List<String> child =
                                        _listDataChild.get(_listDataHeader.get(groupPosition));
                                child.remove(childPosition);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.list_item_string);

        txtListChild.setText(childText);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

