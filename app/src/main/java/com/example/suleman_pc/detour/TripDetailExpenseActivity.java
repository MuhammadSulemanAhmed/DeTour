package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.suleman_pc.detour.Adapter.ExpenseAdpater;
import com.example.suleman_pc.detour.Common.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TripDetailExpenseActivity extends AppCompatActivity {

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
        total =total +  Integer.parseInt(String.valueOf(array.get(i)));

    }
    amount.setText("RS: "+total);
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





