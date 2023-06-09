package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.myapps.androidconcepts.Interfaces.Communication;
import com.myapps.androidconcepts.R;

public class MiddleFragment extends Fragment {
    Communication communication;
    private AppCompatButton frag_btnSubmit;

    public MiddleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_middle, container, false);

        frag_btnSubmit = view.findViewById(R.id.frag_btnSubmit);

        communication = (Communication) getActivity();

        frag_btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communication.sendData();
            }
        });

        return view;
    }
}
