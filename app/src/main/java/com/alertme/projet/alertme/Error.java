package com.alertme.projet.alertme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Thomas on 16/01/2015.
 */
public class Error extends DialogFragment {
    public TextView messageText;
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View custom = inflater.inflate(R.layout.error,null);
        final VariableGlobalClass variableGlobalClass = (VariableGlobalClass) getActivity().getApplicationContext();
        messageText = (TextView) custom.findViewById(R.id.messageError) ;
        messageText.setText(variableGlobalClass.getMessageFromWebservice());

        builder.setView(custom);
        //builder.setMessage(variableGlobalClass.getMessageFromWebservice());
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    //nothing

            }
        });
        //Create the AlertDialog object and return it
        return builder.create();
    }
}
