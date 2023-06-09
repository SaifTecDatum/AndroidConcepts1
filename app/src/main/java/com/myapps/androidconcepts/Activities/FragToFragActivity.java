package com.myapps.androidconcepts.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.myapps.androidconcepts.Fragments.BottomFragment;
import com.myapps.androidconcepts.Fragments.MiddleFragment;
import com.myapps.androidconcepts.Fragments.TopFragment;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.Interfaces.Communication;
import com.myapps.androidconcepts.R;

public class FragToFragActivity extends FragmentActivity implements Communication {
    FragmentManager fragmentManager;
    TopFragment topFragment;
    MiddleFragment middleFragment;
    BottomFragment bottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_to_frag);

        fragmentManager = getSupportFragmentManager();
        topFragment = new TopFragment();
        middleFragment = new MiddleFragment();
        bottomFragment = new BottomFragment();
        //weCanUseReplace or Add inTheBelowLines.
        fragmentManager.beginTransaction().add(R.id.frameLay_TopFrag, topFragment, "Frag_Top_tag").commit();
        fragmentManager.beginTransaction().add(R.id.frameLay_MiddleFrag, middleFragment, "Frag_Middle_tag").commit();
        fragmentManager.beginTransaction().add(R.id.frameLay_BottomFrag, bottomFragment, "Frag_Bottom_tag").commit();
    }

    @Override
    public void sendData() {
        String temp = topFragment.getData();
        if (temp.isEmpty()) {
            Toast.makeText(this, "Please Provide Data in EditText..!", Toast.LENGTH_SHORT).show();
        }
        else {
            bottomFragment.incrementData(temp);
        }
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