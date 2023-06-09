package com.myapps.androidconcepts.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class BarChartFragment extends Fragment {
    private BarChart barChart;
    private final List<BarEntry> barEntryList = new ArrayList<>();

    public BarChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        barChart = view.findViewById(R.id.barChart);

        barEntryList.add(new BarEntry(2014, 456));
        barEntryList.add(new BarEntry(2015, 508));
        barEntryList.add(new BarEntry(2016, 556));
        barEntryList.add(new BarEntry(2017, 598));
        barEntryList.add(new BarEntry(2018, 607));
        barEntryList.add(new BarEntry(2019, 645));
        barEntryList.add(new BarEntry(2020, 695));
        barEntryList.add(new BarEntry(2021, 458));
        barEntryList.add(new BarEntry(2022, 498));

        BarDataSet barDataSet = new BarDataSet(barEntryList, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);

        return view;
    }
}