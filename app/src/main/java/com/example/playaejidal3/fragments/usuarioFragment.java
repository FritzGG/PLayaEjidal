package com.example.playaejidal3.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playaejidal3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class usuarioFragment extends Fragment {


    public usuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

}