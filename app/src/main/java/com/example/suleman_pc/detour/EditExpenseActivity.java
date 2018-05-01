package com.example.suleman_pc.detour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;

/**
 * Created by suleman-pc on 2/20/2018.
 */

public class EditExpenseActivity extends AppCompatActivity {
    //    int from_Where_I_Am_Coming = 0;
//  private ExpenseDBHealper mydb;
    TextView name;
    TextView giver;
    TextView dateView;
    TextView amount;
    String names, dates, givers, amounts;
    Button btn;
    int id;
    private TripsDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.newExpenseName);
        giver = findViewById(R.id.newExpenseGiver);
        dateView = findViewById(R.id.newExpenseDate);
        amount = findViewById(R.id.newExpenseAmount);
        btn = findViewById(R.id.addNewExpense);
        db = new TripsDatabaseHandler(this);
        Intent intent = getIntent();
        String i = intent.getStringExtra("expense_id");
        id = Integer.parseInt(i);
        ExpenseModel expenseModel = db.getSingleExpenses(id);
        name.setText(expenseModel.getExpenseName());
        giver.setText(expenseModel.getExpenseGiver());
        amount.setText(expenseModel.getExpenseAmount());
        dateView.setText(expenseModel.getExpenseDate());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dates = dateView.getText().toString();
                if (name.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense name", Toast.LENGTH_SHORT).show();
                } else if (dateView.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Date", Toast.LENGTH_SHORT).show();
                } else if (amount.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Amount", Toast.LENGTH_SHORT).show();
                } else if (giver.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter expense Giver Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdateExpense();
                }
            }
        });
    }

    private void getvalue() {
        names = name.getText().toString();
        dates = dateView.getText().toString();
        amounts = amount.getText().toString();
        givers = giver.getText().toString();

    }

    private void UpdateExpense() {
        getvalue();

        db.updateSingleExpense(new ExpenseModel(names, givers, dates, amounts, ShareData.getInstance().current_TripId), id);
        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EditExpenseActivity.this, TripDetailExpenseActivity.class);
        startActivity(intent);
    }
}
