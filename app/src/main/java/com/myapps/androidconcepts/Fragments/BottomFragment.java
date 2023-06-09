package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.myapps.androidconcepts.R;

public class BottomFragment extends Fragment {
    TextView tv_fragName;

    public BottomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        tv_fragName = view.findViewById(R.id.tv_fragName);

        return view;
    }

    public void incrementData(String displayText) {
        tv_fragName.setText(displayText);
    }
}
