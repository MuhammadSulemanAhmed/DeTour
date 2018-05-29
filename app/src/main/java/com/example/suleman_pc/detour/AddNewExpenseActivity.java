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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripMembers;
import com.example.suleman_pc.detour.SharedPreferences.PrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
private Dialog keyDialog;
    String expenseName, groupMember;
    private int sid;
    Spinner spinnerExpense, spinnerMember;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private TripsDatabaseHandler db;
    List<String> arrayList;
    String nameOfMmeber = null;
    String[] expenseCat = {
            "Select Expense",
            "Food",
            "Travel",
            "Entertainment",
            "Others",
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        keyDialog=new Dialog(this);
//        spinnerExpense = findViewById(R.id.newExpenseName);
//        spinnerMember = findViewById(R.id.newExpenseGiver);
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

        arrayList = db.getMembersNames(ShareData.getInstance().current_TripId);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dates = dateView.getText().toString();

                if (expenseCat[sid].equals("Select Expense")) {
                    Toast.makeText(getApplicationContext(), "Please select expense", Toast.LENGTH_SHORT).show();
                } else if (dateView.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Date", Toast.LENGTH_SHORT).show();
                } else if (amount.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Amount", Toast.LENGTH_SHORT).show();
                } else if (nameOfMmeber.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select expense Giver Name", Toast.LENGTH_SHORT).show();
                }
//        else if(giver.getText().toString().trim().equals("")){
//            Toast.makeText(getApplicationContext(),"Please enter expense Date",Toast.LENGTH_SHORT).show();
//        }
                else {
                    GetKeyToSaveExpense();
//                    addContact();
                }
//        Toast.makeText(this,"saved", Toast.LENGTH_SHORT).show();

            }
        });
        //seect option for groupmember
        spinnerMember = (Spinner) findViewById(R.id.groupMmebersSspinner);
        spinnerMember.setElevation(View.FOCUSABLES_ALL);
        if(arrayList.size() > 0) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.
                    R.layout.simple_spinner_dropdown_item, arrayList);
            spinnerMember.setAdapter(adapter1);
        }
        else {
            String str="No Name ";
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.
                    R.layout.simple_spinner_dropdown_item, Collections.singletonList(str));
            spinnerMember.setAdapter(adapter1);

        }



        spinnerMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
//                sid = spinnerMember.getSelectedItemPosition();
//                groupMember=groupMembers.getMemberName();
//                Toast.makeText(getBaseContext(), "You have selected Trip Type : " + groupMembers,
//                        Toast.LENGTH_SHORT).show();
                nameOfMmeber = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
// Select option for expense
        spinnerExpense = (Spinner) findViewById(R.id.expenseTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, expenseCat);

        spinnerExpense.setAdapter(adapter);


        spinnerExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                sid = spinnerExpense.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You have selected Trip Type : " + expenseCat[sid],
                        Toast.LENGTH_SHORT).show();
                expenseName = expenseCat[sid];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void GetKeyToSaveExpense() {
        keyDialog.setContentView(R.layout.add_key_dialog);
        final EditText editText=keyDialog.findViewById(R.id.edittextkey);
        final Button button=keyDialog.findViewById(R.id.addKeybtn);
        keyDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=editText.getText().toString().trim();

                if(new PrefManager(getApplicationContext()).getExpenseKey().equals(key)){
                    addContact();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Enter correct key to save Expense", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getvalue() {
//        names = name.getText().toString();
        dates = dateView.getText().toString();
        amounts = amount.getText().toString();
    }

    private void addContact() {
        getvalue();

        db.addExpenses(new ExpenseModel(expenseName, nameOfMmeber, dates, amounts, ShareData.getInstance().current_TripId));
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
