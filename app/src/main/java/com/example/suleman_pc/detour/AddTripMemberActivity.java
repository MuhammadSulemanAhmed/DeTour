package com.example.suleman_pc.detour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Adapter.ExpenseAdpater;
import com.example.suleman_pc.detour.Adapter.GroupMemberAdapter;
import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripMembers;

import java.util.ArrayList;

public class AddTripMemberActivity extends AppCompatActivity {
    ListView listView;
    TripMembers tripMembers;
    GroupMemberAdapter groupMemberAdapter;
    TripsDatabaseHandler mydb;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_member);
        mydb = new TripsDatabaseHandler(this);
        textView = findViewById(R.id.nomember);
        listView = findViewById(R.id.groupMember);

        final ArrayList<TripMembers> expenseModels = new ArrayList<>(mydb.getMembers(ShareData.getInstance().current_TripId));
        if (expenseModels.isEmpty()) {
            textView.setVisibility(View.VISIBLE);

        } else {
            groupMemberAdapter = new GroupMemberAdapter(this, expenseModels);
            listView.setAdapter(groupMemberAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tripMembers=expenseModels.get(position);
                mydb.deleteSingleMember(tripMembers.getId());
                Toast.makeText(getApplicationContext(), "Sucessfully Deleted", Toast.LENGTH_SHORT).show();
                groupMemberAdapter.remove(groupMemberAdapter.getItem(position));
                groupMemberAdapter.notifyDataSetChanged();
            }
        });
    }


}
