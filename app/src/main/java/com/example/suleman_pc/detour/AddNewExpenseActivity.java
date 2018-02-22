package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.suleman_pc.detour.Model.AddNewExpenseDb;

public class AddNewExpenseActivity extends AppCompatActivity {
    EditText newexpensename,amount,date,giver;
    String name;
    String amountgiven;
    String expensedate;
    String exppensegiver;
Button addNewExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);
        AddNewExpenseDbHandler db=new AddNewExpenseDbHandler(this);
        newexpensename=findViewById(R.id.newExpenseName);
        amount=findViewById(R.id.newExpenseAmount);
        date=findViewById(R.id.newExpenseDate);
        giver=findViewById(R.id.newExpenseGiver);
        addNewExpense=findViewById(R.id.addNewExpense);
//        name=newexpensename.getText().toString();
//        amountgiven=amount.getText().toString();
//        expensedate=date.getText().toString();
//        exppensegiver=giver.getText().toString();
//        Log.d("new",name);
        addNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=newexpensename.getText().toString();
                amountgiven=amount.getText().toString();
                expensedate=date.getText().toString();
                exppensegiver=giver.getText().toString();
                Log.d("newhello",name);
                Intent intent=new Intent(AddNewExpenseActivity.this,TripDetailActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("amountgiven",amountgiven);
                intent.putExtra("expensedate",expensedate);
                intent.putExtra("expensegiver",exppensegiver);
                startActivity(intent);

            }
        });
        db.addExpense(new AddNewExpenseDb(name,exppensegiver,expensedate,amountgiven));
    }
}
