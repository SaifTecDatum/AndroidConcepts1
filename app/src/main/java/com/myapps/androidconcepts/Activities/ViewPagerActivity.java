package com.myapps.androidconcepts.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.myapps.androidconcepts.Adapters.ViewpagerAdapter;
import com.myapps.androidconcepts.Fragments.FourthFragment;
import com.myapps.androidconcepts.Fragments.OneFragment;
import com.myapps.androidconcepts.Fragments.ThreeFragment;
import com.myapps.androidconcepts.Fragments.TwoFragment;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

public class ViewPagerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewpagerAdapter viewpagerAdapter;

    //In This ViewPager Process or anywhere must have to follow stepwise code
    //Ex: here I wrote adapterCode, viewPagerCode and then tabLayoutCode orElse will face error.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
        viewpagerAdapter.addFragment(new OneFragment());
        viewpagerAdapter.addFragment(new TwoFragment());
        viewpagerAdapter.addFragment(new ThreeFragment());
        viewpagerAdapter.addFragment(new FourthFragment());

        viewPager.setAdapter(viewpagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_call).setText("Calls");
        tabLayout.getTabAt(1).setIcon(R.drawable.svg_notify).setText("Messages");
        tabLayout.getTabAt(2).setIcon(R.drawable.svg_search).setText("Video Calls");
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_settings).setText("Settings");
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