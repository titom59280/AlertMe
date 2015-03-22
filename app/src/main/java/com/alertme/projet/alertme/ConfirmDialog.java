package com.alertme.projet.alertme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.TextView;

/**
 * Created by tgraveleine on 02/02/2015.
 */
public class ConfirmDialog extends DialogFragment {
    private TextView titleDialog;
    private TextView contentDialog;
    private IconTextView contentAndIconPart1;
    private IconTextView contentAndIconPart2;
    private String title;
    private String contentPart1;
    private String contentPart2;
    private String contentPart3;
    private Class activityToStart;

    public ConfirmDialog(String title, String content1, String content2,String content3, Class startActivity){
        this.title = title;
        this.contentPart1 = content1;
        this.contentPart2 = content2;
        this.contentPart3 = content3;
        this.activityToStart = startActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View confirmView = inflater.inflate(R.layout.new_user,null);
        titleDialog = (TextView) confirmView.findViewById(R.id.titleConfirm);
        titleDialog.setText(title);
        contentDialog = (TextView) confirmView.findViewById(R.id.contentPart1);
        contentDialog.setText(contentPart1);
        contentAndIconPart1 = (IconTextView) confirmView.findViewById(R.id.contentPart2);
        contentAndIconPart1.setText(contentPart2);
        contentAndIconPart2 = (IconTextView) confirmView.findViewById(R.id.contentPart3);
        contentAndIconPart2.setText(contentPart3);
        builder.setView(confirmView);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getActivity(), activityToStart);
                startActivity(intent);

            }
        });
        //Create the AlertDialog object and return it
        return builder.create();
    }
}

