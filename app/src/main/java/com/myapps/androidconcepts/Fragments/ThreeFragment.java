package com.myapps.androidconcepts.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.myapps.androidconcepts.R;

public class ThreeFragment extends Fragment {
    private LottieAnimationView LAV_checkedDone, LAV_switchBtn, LAV_deliveryItem;
    private boolean isCheckedDone = false;
    private boolean isSwitchedOn = false;
    private boolean ifFoodDelivered = false;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        LAV_checkedDone = view.findViewById(R.id.LAV_checkedDone);
        LAV_switchBtn = view.findViewById(R.id.LAV_switchBtn);
        LAV_deliveryItem = view.findViewById(R.id.LAV_deliveryItem);

        LAV_checkedDone.enableMergePathsForKitKatAndAbove(true);
        LAV_switchBtn.enableMergePathsForKitKatAndAbove(true);
        LAV_deliveryItem.enableMergePathsForKitKatAndAbove(true);

        LAV_checkedDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheckedDone) {
                    isCheckedDone = false;
                    LAV_checkedDone.setSpeed(-1);
                    LAV_checkedDone.playAnimation();
                }
                else {
                    isCheckedDone = true;
                    LAV_checkedDone.setSpeed(1);
                    LAV_checkedDone.playAnimation();
                }
            }
        });

        LAV_switchBtn.setSpeed(3f);
        LAV_switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSwitchedOn) {
                    isSwitchedOn = false;
                    LAV_switchBtn.setMinAndMaxProgress(0.5f, 1.0f);
                    LAV_switchBtn.playAnimation();
                }
                else {
                    isSwitchedOn = true;
                    LAV_switchBtn.setMinAndMaxProgress(0.0f, 0.5f);
                    LAV_switchBtn.playAnimation();
                }
            }
        });

        LAV_deliveryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifFoodDelivered) {
                    ifFoodDelivered = false;
                    LAV_deliveryItem.setSpeed(-1);
                    LAV_deliveryItem.playAnimation();
                } else {
                    ifFoodDelivered = true;
                    LAV_deliveryItem.setSpeed(1);
                    LAV_deliveryItem.playAnimation();
                }
            }
        });

        return view;
    }
}