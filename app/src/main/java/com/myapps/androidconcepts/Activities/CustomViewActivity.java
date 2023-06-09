package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.myapps.androidconcepts.Helpers.CustomView;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class CustomViewActivity extends AppCompatActivity {
    private CustomView customView;
    private AppCompatButton button;

   /*
linksForThisCustomViews :
        part1 : https://www.youtube.com/watch?v=sb9OEl4k9Dk part-1
        part2 : https://www.youtube.com/watch?v=cfwO0Yui43g
        part3 : https://www.youtube.com/watch?v=BxBcs1ddEn8
        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        customView = findViewById(R.id.customView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.swapColor();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilities.onPauseToUnRegister(this);
    }

}