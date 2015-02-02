package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alertme.projet.alertme.R;

public class MyAccountFragment extends Fragment {

    public MyAccountFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_my_account,container,false);
        return rootView;
    }
}
