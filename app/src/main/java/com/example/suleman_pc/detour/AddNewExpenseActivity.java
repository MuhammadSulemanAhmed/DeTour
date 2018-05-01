package com.example.suleman_pc.detour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;

import java.util.Calendar;

/**
 * Created by suleman-pc on 2/20/2018.
 */

public class AddNewExpenseActivity extends AppCompatActivity {

    TextView name;
    TextView giver;
    TextView date;
    TextView amount;
    String names, dates, givers, amounts;
    Button btn;
    int trip_id;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private TripsDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.newExpenseName);
        giver = findViewById(R.id.newExpenseGiver);
        dateView = findViewById(R.id.newExpenseDate);
        amount = findViewById(R.id.newExpenseAmount);
        btn = findViewById(R.id.addNewExpense);
//      dateView = findViewById(R.id.tvdate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        db = new TripsDatabaseHandler(this);
//      Intent intent=getIntent();
//      Bundle extra=intent.getExtras();
//      String get= (String) extra.get("Tripid");
//      int trip_id=ShareData.getInstance().current_TripId;
//      Toast.makeText(this,"get"+trip_id,Toast.LENGTH_SHORT).show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        names=name.getText().toString();
                dates = dateView.getText().toString();
//        amounts=amount.getText().toString();
//        givers=giver.getText().toString();
                if (name.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense name", Toast.LENGTH_SHORT).show();
                } else if (dateView.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Date", Toast.LENGTH_SHORT).show();
                } else if (amount.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Amount", Toast.LENGTH_SHORT).show();
                } else if (giver.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Giver Name", Toast.LENGTH_SHORT).show();
                }
//        else if(giver.getText().toString().trim().equals("")){
//            Toast.makeText(getApplicationContext(),"Please enter expense Date",Toast.LENGTH_SHORT).show();
//        }
                else {
                    addContact();
                }
//        Toast.makeText(this,"saved", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getvalue() {
        names = name.getText().toString();
        dates = dateView.getText().toString();
        amounts = amount.getText().toString();
        givers = giver.getText().toString();

    }

    private void addContact() {
        getvalue();

        db.addExpenses(new ExpenseModel(names, givers, dates, amounts, ShareData.getInstance().current_TripId));
        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddNewExpenseActivity.this, TripDetailExpenseActivity.class);
        startActivity(intent);

    }

    //date picker
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

}
