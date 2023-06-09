package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.myapps.androidconcepts.R;

import org.jetbrains.annotations.NotNull;

public class NavStopFragment extends Fragment {
    AppCompatButton btn_stopFragment;
    NavController navController;

    public NavStopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_stop, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        btn_stopFragment = view.findViewById(R.id.btn_stopFragment);

        btn_stopFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*navController.navigate(R.id.action_navStopFragment_to_navStartFragment);*/

                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.navStartFragment, true).build();
                navController.navigate(R.id.action_navStopFragment_to_navStartFragment, null, navOptions);

                /*thisAbove NavOptions WorksAs "finish" (or) itPopsUp AllThe Fragments ThatAre InBetween ToThe Destination
                fragment,ourDestinationFragment is navStartFragment. inclusive TrueMeans ToActivate TheCondition.*/
            }
        });
    }
}