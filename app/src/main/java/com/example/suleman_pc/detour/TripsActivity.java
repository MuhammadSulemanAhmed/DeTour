package com.example.suleman_pc.detour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.suleman_pc.detour.Adapter.TripsGridAdapter;
import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TripsDatabaseHandler db;
    private GridView lv;
    private TripsGridAdapter data;
    private TripModel dataModel;
    private Button btn;
static  int p=0;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        db = new TripsDatabaseHandler(this);

        lv = (GridView) findViewById(R.id.list1);
        btn = findViewById(R.id.addnew);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripsActivity.this, AddNewTripActivity.class);
                startActivity(intent);
                finish();
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

//
                Intent intent = new Intent(TripsActivity.this, TripDetailExpenseActivity.class);

//
                ShareData.getInstance().current_TripId = dataModel.getID();

//              public void  delete() {
//                db.deleteAllExpense(ShareData.getInstance().current_TripId);
//                db.deleteContact(dataModel.getID());
//                }
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), String.valueOf(dataModel.getID()), Toast.LENGTH_SHORT).show();

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel = tripModels.get(position);
                ShareData.getInstance().current_TripId = dataModel.getID();
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                Showpopup(view);

                return true;

            }
        });
    }
        public void Showpopup(View v){
            PopupMenu popupMenu=new PopupMenu(this,v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.trip_long_click_menu);
            popupMenu.show();


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_trip:
                Intent intent = new Intent(TripsActivity.this, EditTripActivity.class);
                startActivity(intent);
            case R.id.delete_trip:
                dailog();
            default:
                return false;
        }

    }
    public void  delete() {
        db.deleteContact(ShareData.getInstance().current_TripId);
        db.deleteAllExpense(ShareData.getInstance().current_TripId);
        Intent refresh = new Intent(this,  TripsActivity.class);
        startActivity(refresh);
        this.finish();

    }
    public void dailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Trip");
        builder.setMessage("Are you sure to delete trip?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                delete();
                Toast.makeText(getApplicationContext(),"Sucessfully Deleted",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}
