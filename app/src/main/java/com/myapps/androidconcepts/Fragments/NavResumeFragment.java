package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

public class NavResumeFragment extends Fragment {
    AppCompatButton btn_resumeFragment;
    NavController navController;

    public NavResumeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_resume, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        btn_resumeFragment = view.findViewById(R.id.btn_resumeFragment);

        btn_resumeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navResumeFragment_to_navStopFragment);
            }
        });
    }
}