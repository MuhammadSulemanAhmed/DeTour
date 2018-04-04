package com.example.suleman_pc.detour;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.example.suleman_pc.detour.Adapter.ExpenseAdpater;
import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.NearbyModel.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TripDetailExpenseActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    TripsDatabaseHandler mydb;
    ExpenseModel dataModel;
    ExpenseAdpater data;
    ListView Iv;
    TextView amount;
    int temp=2;
    Button btn_stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        mydb=new TripsDatabaseHandler(this);
        Iv=findViewById(R.id.listexpense);
        amount=findViewById(R.id.total_amount);
        btn_stat=findViewById(R.id.btn_stats);
        btn_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TripDetailExpenseActivity.this,PieChartActivity.class);
                startActivity(intent);
            }
        });
        ShowRecords();
        total();


    }
private  void  total(){
    ArrayList<String> array= new ArrayList<>(mydb.getTotalAmount(ShareData.getInstance().current_TripId));
    int total=0;
    for(int i=0;i<array.size();i++){
        if(array.get(i) != null) {
            total = total + Integer.parseInt(String.valueOf(array.get(i)));
        }
    }
    amount.setText("RS: "+total);
}

    private void ShowRecords() {
        final ArrayList<ExpenseModel> expenseModels = new ArrayList<>(mydb.getExpenses(ShareData.getInstance().current_TripId));
        data = new ExpenseAdpater(this,expenseModels);
        Iv.setAdapter(data);
//        Iv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dataModel = expenseModels.get(position);
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(TripDetailExpenseActivity.this,EditExpenseActivity.class);
//                startActivity(intent);
//
//            }
//        });
        Iv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel = expenseModels.get(position);
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
                String id= String.valueOf(dataModel.getId());
                Intent intent = new Intent(TripDetailExpenseActivity.this, EditExpenseActivity.class);
                intent.putExtra("expense_id",id);
                startActivity(intent);
            case R.id.delete_trip:
                dailog();
            default:
                return false;
        }

    }
    public void dailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Expense");
        builder.setMessage("Are you sure to delete Expense?");

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
    public void delete(){

        mydb.deleteSingleExpense(dataModel.getId());
        Intent refresh = new Intent(this,  TripDetailExpenseActivity.class);
        startActivity(refresh);
        this.finish();
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


    }





