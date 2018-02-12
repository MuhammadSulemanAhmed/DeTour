package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class TripDetailActivity extends AppCompatActivity {
ListView listview;
ImageView addExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        // Initializing list view with the custom adapter
        ArrayList <Expense> itemList = new ArrayList<Expense>();
        ExpenseAdpater itemArrayAdapter = new ExpenseAdpater(this, R.layout.single_expense, itemList);
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(itemArrayAdapter);


        // Populating list items
        for(int i=0; i<100; i++) {
            itemList.add(new Expense("Item " + i));
        }
        addExpense=findViewById(R.id.addexpense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(TripDetailActivity.this,AddNewExpenseActivity.class);
                startActivity(intent);

        // Set up list item onclick listener
        setUpListItemClickListener();

    }
    private void setUpListItemClickListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
});
    }}
