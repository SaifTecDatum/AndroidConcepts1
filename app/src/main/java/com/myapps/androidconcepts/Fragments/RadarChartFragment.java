package com.myapps.androidconcepts.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

//Problem by this RadarChartFragment -backStack(null) not working in it but creating multiple instances.
//not creating new instance while coming back from BarChartFragment. Need To Fix ASAP.
public class RadarChartFragment extends Fragment {
    private RadarChart radarChart;
    private final List<RadarEntry> radarEntryList = new ArrayList<>();
    private final List<RadarEntry> radarEntryList2 = new ArrayList<>();

    public RadarChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_radar_chart, container, false);


        radarChart = view.findViewById(R.id.radarChart);


        radarEntryList.add(new RadarEntry(420));
        radarEntryList.add(new RadarEntry(475));
        radarEntryList.add(new RadarEntry(500));
        radarEntryList.add(new RadarEntry(555));
        radarEntryList.add(new RadarEntry(605));
        radarEntryList.add(new RadarEntry(685));
        radarEntryList.add(new RadarEntry(725));
        radarEntryList.add(new RadarEntry(775));

        RadarDataSet radarDataSet = new RadarDataSet(radarEntryList, "Website 1");
        //radarDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        radarDataSet.setColor(Color.RED);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.RED);
        radarDataSet.setValueTextSize(14f);


        radarEntryList2.add(new RadarEntry(310));
        radarEntryList2.add(new RadarEntry(378));
        radarEntryList2.add(new RadarEntry(430));
        radarEntryList2.add(new RadarEntry(490));
        radarEntryList2.add(new RadarEntry(550));
        radarEntryList2.add(new RadarEntry(600));
        radarEntryList2.add(new RadarEntry(644));
        radarEntryList2.add(new RadarEntry(895));

        RadarDataSet radarDataSet2 = new RadarDataSet(radarEntryList2, "Website 2");
        //radarDataSet2.setColors(ColorTemplate.LIBERTY_COLORS);
        radarDataSet2.setColor(Color.BLUE);
        radarDataSet2.setLineWidth(2f);
        radarDataSet2.setValueTextColor(Color.BLUE);
        radarDataSet2.setValueTextSize(14f);


        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.addDataSet(radarDataSet2);

        String[] labels = {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.setData(radarData);
        radarChart.getDescription().setText("Radar Chart Example");

        return view;
    }
}