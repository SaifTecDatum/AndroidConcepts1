package com.myapps.androidconcepts.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.myapps.androidconcepts.Adapters.ChartPagerAdapter;
import com.myapps.androidconcepts.Fragments.BarChartFragment;
import com.myapps.androidconcepts.Fragments.PieChartFragment;
import com.myapps.androidconcepts.Fragments.RadarChartFragment;
import com.myapps.androidconcepts.R;

public class ChartAndroidActivity extends AppCompatActivity {
    private static final String TAG = "ChartAndroidActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ChartPagerAdapter chartPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_android);

        toolbar = findViewById(R.id.chart_toolbar);
        tabLayout = findViewById(R.id.tabLayout_chart);
        viewPager = findViewById(R.id.viewPager_chart);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        chartPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager());
        chartPagerAdapter.addFragment(new BarChartFragment());
        chartPagerAdapter.addFragment(new PieChartFragment());
        chartPagerAdapter.addFragment(new RadarChartFragment());
        //Problem by this RadarChartFragment -backStack(null) not working in it but creating multiple instances.
        //not creating new instance while coming back from BarChartFragment. Need To Fix ASAP.

        viewPager.setAdapter(chartPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_add).setText("Bar Chart");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_add_videos).setText("Pie Chart");
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings).setText("Radar Chart");

    }

}