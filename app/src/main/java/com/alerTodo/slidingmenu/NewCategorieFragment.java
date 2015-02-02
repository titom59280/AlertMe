package com.alerTodo.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alertme.projet.alertme.R;

public class NewCategorieFragment extends Fragment {

    public NewCategorieFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_new_categorie,container,false);
        return rootView;
    }
}
