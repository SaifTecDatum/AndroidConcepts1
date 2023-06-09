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

public class NavStartFragment extends Fragment {
    AppCompatButton btn_startFragment;
    NavController navController;

    public NavStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_start, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        btn_startFragment = view.findViewById(R.id.btn_startFragment);

        btn_startFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navStartFragment_to_navResumeFragment);
            }
        });
    }
}