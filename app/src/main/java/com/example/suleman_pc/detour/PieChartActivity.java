package com.example.suleman_pc.detour;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

public class PieChartActivity extends AppCompatActivity {
private float[] ydata={24,35,56,44,74,78,400};
private String[] xdata={"Food","Travel","Hotel","Extra Expense","Firts Aid","Room Extra","Others"};
PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart=findViewById(R.id.idPieChart);
//        pieChart.setDescription("EXPENSES");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("EXxpenses");
        pieChart.setCenterTextSize(10f);
        pieChart.setDrawEntryLabels(true);
        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1=e.toString().indexOf("SUM");
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys=new ArrayList<>();
        ArrayList<String> xEntrys=new ArrayList<>();
        for(int i=0;i<ydata.length;i++){

            yEntrys.add(new PieEntry(ydata[i],i));
        }
        xEntrys.addAll(Arrays.asList(xdata));
        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Expenses on Trip");
        pieChart.setDrawSlicesUnderHole(true);
//        pieChart.setValueTextSize(12);

        ArrayList<Integer> colours=new ArrayList<>();
        colours.add(Color.BLUE);
        colours.add(Color.GREEN);
        colours.add(Color.GRAY);
        colours.add(Color.GREEN);
        colours.add(Color.CYAN);
        colours.add(Color.MAGENTA);
        colours.add(Color.RED);
        
        Legend legend=new Legend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        PieData pieData=new PieData(pieDataSet);
//        pieData.setValueTextColor(Color.red(10));
//        for(int c: colours) {
//            colours.add(c);
//            pieChart.setEntryLabelColor(colours);
//        }
        pieChart.setData(pieData);
        pieChart.invalidate();



    }
}
