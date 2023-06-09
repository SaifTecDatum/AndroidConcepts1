package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.myapps.androidconcepts.R;

public class TopFragment extends Fragment {
    AppCompatEditText frag_editText;

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top, container, false);

        frag_editText = view.findViewById(R.id.frag_editText);

        return view;
    }

    public String getData() {
        if (TextUtils.isEmpty(frag_editText.getText().toString().trim())) {
            frag_editText.setError("Fields Required..!");
        }
        return frag_editText.getText().toString().trim();
    }

}