package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.suleman_pc.detour.Adapter.ExpenseAdpater;
import com.example.suleman_pc.detour.Adapter.TripsGridAdapter;
import com.example.suleman_pc.detour.Common.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;
import java.util.List;

public class TripDetailExpenseActivity extends AppCompatActivity {

    TripsDatabaseHandler mydb;
    ExpenseModel dataModel;
    ExpenseAdpater data;
    ListView Iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        mydb=new TripsDatabaseHandler(this);
        Iv=findViewById(R.id.listexpense);
        ShowRecords();
    }
    private void ShowRecords() {
        final ArrayList<ExpenseModel> tripModels = new ArrayList<>(mydb.getExpenses(ShareData.getInstance().current_TripId));
        data = new ExpenseAdpater(this,tripModels);

        Iv.setAdapter(data);

        Iv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = tripModels.get(position);
//                mydb.deleteExpense(dataModel.getId());
                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(TripDetailExpenseActivity.this,TripDetailExpenseActivity.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(),AddNewExpenseActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//        }
//        return super.onKeyDown(keycode, event);
//    }

    }





