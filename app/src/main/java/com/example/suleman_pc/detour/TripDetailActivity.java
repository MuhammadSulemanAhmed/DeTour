package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.suleman_pc.detour.Adapter.ExpenseAdpater;
import com.example.suleman_pc.detour.Model.AddNewExpenseDb;
import com.example.suleman_pc.detour.Model.Expense;

import java.util.ArrayList;
import java.util.List;

public class TripDetailActivity extends AppCompatActivity {
ListView listview;
String name,date,amount,giver;
ImageView addExpense;
    AddNewExpenseDb cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
//        AddNewExpenseDbHandler db=new AddNewExpenseDbHandler(this);
//        ExpenseDatabaseHandler db=new ExpenseDatabaseHandler(this);
        // Initializing list view with the custom adapter
        ArrayList <Expense> itemList = new ArrayList<Expense>();
        ExpenseAdpater itemArrayAdapter = new ExpenseAdpater(this, R.layout.single_expense, itemList);
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(itemArrayAdapter);
//        Intent iin=getIntent();
//        Bundle bundle=iin.getExtras();
//        if(bundle!=null){
//
//             name =(String) bundle.get("name");
//             amount =(String) bundle.get("amountgiven");
//            date =(String) bundle.get("expensedate");
//            giver =(String) bundle.get("expensegiver");
//        }
//        db.addExpense(new AddNewExpenseDb(name,date,giver,amount));
            // Populating list items
//        for(int i=0; i<100; i++) {

//            itemList.add(new Expense(name, giver, date, amount));
//        List<AddNewExpenseDb> expenses=db.getAllExpense();
//        for (AddNewExpenseDb cn : expenses) {
//            String log = "Id"+cn.getId()+"name"+cn.getExpenseName()+"giver"+cn.getExpenseGiver()+"date"+cn.getExpenseDate()+"amount"+cn.getExpenseAmount();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }


        itemList.add(new Expense("name:","giver","date","amount"));
//        }

//        for(AddNewExpenseDb ex: expenses)
//        {
//            itemList.add(new Expense("name:"+ex.getExpenseName(),"giver"+ex.getExpenseGiver(),"date"+ex.getExpenseDate(),"amount"+ex.getExpenseAmount()));
//        }

        addExpense=findViewById(R.id.addexpense);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(TripDetailActivity.this, AddNewExpenseActivity.class);
                startActivity(intent);

        // Set up list item onclick listener


    }

        });
        DatabaseHandler db = new DatabaseHandler(this);

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<ExpenseDb> contacts = db.getAllContacts();

        for (ExpenseDb cn : contacts) {
            itemList.add(new Expense(""+cn.getName(),cn.getPhoneNumber(),"1245"+cn.getDate(),""));
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

    }

    }

