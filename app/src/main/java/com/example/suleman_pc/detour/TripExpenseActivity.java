package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.suleman_pc.detour.Adapter.GridViewAdapter;

public class TripExpenseActivity extends AppCompatActivity {
    GridView gridview;

    public static String[] osNameList = {
            "Murree",
            "Nathia Gali",
            "Lahore",
            "Karachi",
            "Sargodha",
            "Islamabad",
            "Rawalpindi",
            "Kashmir",
    };
    public static int[] osImages = {
            R.drawable.bikess,
    R.drawable.benz2,
 R.drawable.bikess,
    R.drawable.bikess
    ,R.drawable.bikess,R.drawable.bikess,R.drawable.bikess,R.drawable.bikess};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new GridViewAdapter(this, osNameList, osImages));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
        */
        int id = item.getItemId();

       // noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Intent intent=new Intent(this,AddNewTripActivity.class);
            startActivity(intent);
        }

        return true;
    }
}


