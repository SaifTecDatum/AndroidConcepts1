package com.myapps.androidconcepts.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {
    private PieChart pieChart;
    private final List<PieEntry> pieChartList = new ArrayList<>();

    public PieChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        pieChart = view.findViewById(R.id.pieChart);

        pieChartList.add(new PieEntry(508, "2016"));
        pieChartList.add(new PieEntry(600, "2017"));
        pieChartList.add(new PieEntry(750, "2018"));
        pieChartList.add(new PieEntry(600, "2019"));
        pieChartList.add(new PieEntry(670, "2020"));
        pieChartList.add(new PieEntry(720, "2021"));
        pieChartList.add(new PieEntry(800, "2022"));

        PieDataSet pieDataSet = new PieDataSet(pieChartList, "Visitors");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setText("Pie Chart Example");
        //pieChart.getDescription().setEnabled(true);   //notNecessary - alreadyDefaultValueIsTrue.
        pieChart.setCenterText("Visitors");
        pieChart.animate();

        return view;
    }
}