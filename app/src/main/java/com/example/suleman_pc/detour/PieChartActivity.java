package com.example.suleman_pc.detour;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
//    private float[] ydata = {24, 35, 56, 44, 74, 78, 40};
//    private String[] xdata = {"Food", "Travel", "Hotel", "Extra Expense", "Firts Aid", "Room Extra", "Others"};
    PieChart pieChart;
    private static String TAG = "MainActivity";

    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = findViewById(R.id.idPieChart);
////        pieChart.setDescription("EXPENSES");
//        pieChart.setRotationEnabled(true);
//        pieChart.setHoleRadius(25f);
//        pieChart.setTransparentCircleAlpha(0);
//        pieChart.setCenterText("Expenses");
//        pieChart.setCenterTextSize(10f);
//        pieChart.setDrawEntryLabels(true);
//        addDataSet();
//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                int pos1 = e.toString().indexOf("SUM");
//                String sales = e.toString().substring(pos1 + 7);
//
//                for(int i = 0; i < ydata.length; i++){
//                    if(ydata[i] == Float.parseFloat(sales)){
//                        pos1 = i;
//                        break;
//                    }
//                }
//                String employee = xdata[pos1 + 1];
//                Toast.makeText(PieChartActivity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//
//        });
//
//    }
//
//    private void addDataSet() {
//        ArrayList<PieEntry> yEntrys = new ArrayList<>();
//        ArrayList<String> xEntrys = new ArrayList<>();
//        for (int i = 0; i < ydata.length; i++) {
//
//            yEntrys.add_key_startup_dialoge(new PieEntry(ydata[i], i));
//        }
//        xEntrys.addAll(Arrays.asList(xdata));
//        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Expenses on Trip");
//
//        pieChart.setDrawSlicesUnderHole(true);
//        pieDataSet.setValueTextSize(12);
//
//        ArrayList<Integer> colours = new ArrayList<>();
//        colours.add_key_startup_dialoge(Color.BLUE);
//        colours.add_key_startup_dialoge(Color.GREEN);
//        colours.add_key_startup_dialoge(Color.GRAY);
//        colours.add_key_startup_dialoge(Color.GREEN);
//        colours.add_key_startup_dialoge(Color.CYAN);
//        colours.add_key_startup_dialoge(Color.MAGENTA);
//        colours.add_key_startup_dialoge(Color.RED);
//        colours.add_key_startup_dialoge(Color.RED);
//        colours.add_key_startup_dialoge(Color.RED);
//        pieDataSet.setColors(colours);
//        Legend legend = new Legend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//        PieData pieData = new PieData(pieDataSet);
////        pieData.setValueTextColor(Color.red(10));
////        for(int c: colours) {
////            colours.add_key_startup_dialoge(c);
////            pieChart.setEntryLabelColor(colours);
////        }
//        pieChart.setData(pieData);
//        pieChart.invalidate();
//
//
//    }
        Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) findViewById(R.id.idPieChart);

//        pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1 + 1];
                Toast.makeText(PieChartActivity.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add_key_startup_dialoge colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add_key_startup_dialoge legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
