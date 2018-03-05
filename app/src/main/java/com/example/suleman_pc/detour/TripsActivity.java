package com.example.suleman_pc.detour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Adapter.TripsGridAdapter;
import com.example.suleman_pc.detour.Common.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    private TripsDatabaseHandler db;
    private GridView lv;
    private TripsGridAdapter data;
    private TripModel dataModel;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        db = new TripsDatabaseHandler(this);

        lv = (GridView) findViewById(R.id.list1);
        btn=findViewById(R.id.addnew);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripsActivity.this,AddNewTripActivity.class);
                startActivity(intent);
            }
        });

        ShowRecords();
    }
    private void ShowRecords() {
        final ArrayList<TripModel> tripModels = new ArrayList<>(db.getAllContacts());
        data = new TripsGridAdapter(this, tripModels);

        lv.setAdapter(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = tripModels.get(position);
//                db.deleteAllExpense(ShareData.getInstance().current_TripId);
//                db.deleteContact(dataModel.getID());

                Intent intent=new Intent(TripsActivity.this,TripDetailExpenseActivity.class);
//                  AlertDialog.Builder builder = new AlertDialog.Builder(this);

//
                ShareData.getInstance().current_TripId = dataModel.getID();
//                db.deleteAllExpense(ShareData.getInstance().current_TripId);
//                db.deleteContact(dataModel.getID());
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
