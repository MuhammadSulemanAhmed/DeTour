package com.example.suleman_pc.detour;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripMembers;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by suleman-pc on 2/20/2018.
 */

public class EditExpenseActivity extends AppCompatActivity {
    Spinner name;
    Spinner giver;
    TextView dateView;
    private int year, month, day;
    TextView amount;
    String dates, amounts;
    Button btn;
    int id;
    String namespin = null;
    private TripsDatabaseHandler db;
    String nameMem = null;
    ArrayList<String> expenseName;
    ArrayList<String> expenseGiver;
    TextView txt_giver;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.newExpenseName);
        giver = findViewById(R.id.newExpenseGiver);
        dateView = findViewById(R.id.newExpenseDate);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        amount = findViewById(R.id.newExpenseAmount);
        btn = findViewById(R.id.addNewExpense);
        txt_giver = findViewById(R.id.tv_giver);
        db = new TripsDatabaseHandler(this);
        expenseGiver = new ArrayList<>();
        //getting expense id
        Intent intent = getIntent();
        String i = intent.getStringExtra("expense_id");
        id = Integer.parseInt(i);
        ExpenseModel expenseModel = db.getSingleExpenses(id);
        namespin = expenseModel.getExpenseName();
        nameMem = expenseModel.getExpenseGiver();
        //setting edittext
        amount.setText(expenseModel.getExpenseAmount());
        dateView.setText(expenseModel.getExpenseDate());
        expenseGiver.add(nameMem);
        ArrayList<String> namesList = new ArrayList<>(db.getMembersNames(ShareData.getInstance().current_TripId));
        try {
            for (int j = 0; j <= namesList.size(); j++) {
                expenseGiver.add(namesList.get(j));
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception ", Toast.LENGTH_SHORT).show();

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateView.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Date", Toast.LENGTH_SHORT).show();
                } else if (amount.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Amount", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateExpense();
                }
            }
        });

        //seect option for groupmember
        giver = (Spinner) findViewById(R.id.newExpenseGiver);
        final String tripType = db.getTripType(ShareData.getInstance().current_TripId);
        if (tripType.equals("Group Trip")) {
            SetSpinnerForName();
        } else {
            giver.setVisibility(View.GONE);
            txt_giver.setVisibility(View.GONE);
        }


// Select option for expense category
        expenseName = new ArrayList<>();
        switch (namespin) {
            case "Food":
                expenseName.add("Food");
                expenseName.add("Travel");
                expenseName.add("Entertainment");
                expenseName.add("Others");
                break;
            case "Entertainment":
                expenseName.add("Entertainment");
                expenseName.add("Food");
                expenseName.add("Travel");
                expenseName.add("Others");
                break;
            case "Travel":
                expenseName.add("Travel");
                expenseName.add("Food");
                expenseName.add("Entertainment");
                expenseName.add("Others");
                break;
            case "Others":
                expenseName.add("Others");
                expenseName.add("Food");
                expenseName.add("Travel");
                expenseName.add("Entertainment");
                break;
        }
        name = (Spinner) findViewById(R.id.newExpenseName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, expenseName);

        name.setAdapter(adapter);


        name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                namespin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void SetSpinnerForName() {
        if (expenseGiver.size() > 0) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.
                    R.layout.simple_spinner_dropdown_item, expenseGiver);
            giver.setAdapter(adapter1);
        } else {
            String str = "No Name ";
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.
                    R.layout.simple_spinner_dropdown_item, Collections.singletonList(str));
            giver.setAdapter(adapter1);
        }
        giver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                nameMem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    //Getting values from edittext
    private void getvalue() {
        dates = dateView.getText().toString();
        amounts = amount.getText().toString();
    }

    //  Updating the single Expense
    private void UpdateExpense() {
        getvalue();

        db.updateSingleExpense(new ExpenseModel(namespin, nameMem, dates, amounts, ShareData.getInstance().current_TripId), id);
        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EditExpenseActivity.this, TripDetailExpenseActivity.class);
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
