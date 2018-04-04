package com.example.suleman_pc.detour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.suleman_pc.detour.NearbyModel.Helper.ExpenseDBHealper;
import com.example.suleman_pc.detour.NearbyModel.Helper.TripsDatabaseHandler;

public class ShowExpenseDetailActivity extends AppCompatActivity {
    TextView name;
    TextView date, amount, giver;
    private TripsDatabaseHandler mydb;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense_detail);
        mydb = new TripsDatabaseHandler(this);
        name=findViewById(R.id.ex_name);
        date=findViewById(R.id.ex_date);
        amount=findViewById(R.id.ex_amount);
        giver=findViewById(R.id.ex_giver);
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            int Value = extras.getInt("id");
//            if (Value > 0) {

//        final ArrayList<TripModel> tripModels = new ArrayList<>(mydb.getAllContacts());
//        TripsGridAdapter data = new TripsGridAdapter(this, tripModels);
//
//        GridView lv=new GridView(this);
//        lv.setAdapter(data);
//                Cursor res =  mydb.getAllExpenses();
////                id_To_Update = Value;
//                res.moveToFirst();
//                String nam = res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_NAME));
//                String giv = res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_GIVER));
//                String dat = res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_DATE));
//                String amo = res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_AMOUNT));
//                if (!res.isClosed()){
//                    res.close();
//                }
//                name.setText(nam);
//                date.setText(dat);
//                amount.setText(amo);
//                giver.setText(giv);
            }

        }

