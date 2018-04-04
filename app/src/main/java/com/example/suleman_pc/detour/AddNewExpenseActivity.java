package com.example.suleman_pc.detour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.NearbyModel.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.ExpenseModel;

/**
 * Created by suleman-pc on 2/20/2018.
 */

public class AddNewExpenseActivity extends AppCompatActivity{
//    int from_Where_I_Am_Coming = 0;
//  private ExpenseDBHealper mydb;
  TextView name;
  TextView giver;
  TextView date;
  TextView amount;
  String names,dates,givers,amounts;
  Button btn;
    int trip_id;
  private TripsDatabaseHandler db;
//  int id_To_Update=0;
//  TripModel data;
  @Override
    protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_new_expense);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      name=findViewById(R.id.newExpenseName);
      giver=findViewById(R.id.newExpenseGiver);
      date=findViewById(R.id.newExpenseDate);
      amount=findViewById(R.id.newExpenseAmount);
      btn=findViewById(R.id.addNewExpense);
      db=new TripsDatabaseHandler(this);
//      Intent intent=getIntent();
//      Bundle extra=intent.getExtras();
//      String get= (String) extra.get("Tripid");
//      int trip_id=ShareData.getInstance().current_TripId;
//      Toast.makeText(this,"get"+trip_id,Toast.LENGTH_SHORT).show();
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        names=name.getText().toString();
        dates=date.getText().toString();
//        amounts=amount.getText().toString();
//        givers=giver.getText().toString();
        if(name.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter expense name",Toast.LENGTH_SHORT).show();
        }
        else if(date.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter expense Date",Toast.LENGTH_SHORT).show();
        }
        else if(amount.getText().toString().trim().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter expense Amount",Toast.LENGTH_SHORT).show();
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
//      mydb =new ExpenseDBHealper(this);
//
//      Bundle extras=getIntent().getExtras();
//      if (extras != null){
//          int Value =extras.getInt("id");
//          if (Value>0){
//              Cursor res=mydb.getData(Value);
//              id_To_Update=Value;
//              res.moveToFirst();
//              String nam=res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_NAME));
//              String giv=res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_GIVER));
//              String dat=res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_DATE));
//              String amo=res.getString(res.getColumnIndex(ExpenseDBHealper.EXPENSES_COLUMN_AMOUNT));
//          if (!res.isClosed()){
//              res.close();
//          }
//              Button b=findViewById(R.id.addNewExpense);
//          b.setVisibility(View.INVISIBLE);
//
//              name.setText((CharSequence)nam);
//              name.setFocusable(false);
//              name.setClickable(false);
//
//              giver.setText((CharSequence)giv);
//              giver.setFocusable(false);
//              giver.setClickable(false);
//
//              date.setText((CharSequence)dat);
//              date.setFocusable(false);
//              date.setClickable(false);
//
//              amount.setText((CharSequence)amo);
//              amount.setFocusable(false);
//              amount.setClickable(false);
//
//          }
//      }
//  }
//  @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        Bundle extras=getIntent().getExtras();
//        if (extras!=null){
//            int Value=extras.getInt("id");
//            if (Value>0) {
//                getMenuInflater().inflate(R.menu.display_contact, menu);
//            }else {
//                getMenuInflater().inflate(R.menu.main,menu);
//            }
//        }
//       return true;
//  }
//  public boolean onOptionsItemSelected(MenuItem item){
//        super.onOptionsItemSelected(item);
//                switch(item.getItemId()) {
//                    case R.id.Edit_Expense:
//                        Button b = (Button)findViewById(R.id.addNewExpense);
//                        b.setVisibility(View.VISIBLE);
//                        name.setEnabled(true);
//                        name.setFocusableInTouchMode(true);
//                        name.setClickable(true);
//
//                        giver.setEnabled(true);
//                        giver.setFocusableInTouchMode(true);
//                        giver.setClickable(true);
//
//                        date.setEnabled(true);
//                        date.setFocusableInTouchMode(true);
//                        date.setClickable(true);
//
//                        amount.setEnabled(true);
//                        amount.setFocusableInTouchMode(true);
//                        amount.setClickable(true);
//                        return true;
//                    case R.id.Delete_Expense:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setMessage("")
//                                .setPositiveButton("", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        mydb.deleteExpense(id_To_Update);
//                                        Toast.makeText(getApplicationContext(), "Deleted Successfully",
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(),TripDetailExpenseActivity.class);
//                                        startActivity(intent);
//                                    }
//                                })
//                                .setNegativeButton("", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // User cancelled the dialog
//                                    }
//                                });
//                        AlertDialog d = builder.create();
//                        d.setTitle("Are you sure");
//                        d.show();
//                        return true;
//                    default:
//                        return super.onOptionsItemSelected(item);
//                }
//  }
//    public void run(View view) {
//        Bundle extras = getIntent().getExtras();
//        if(extras !=null) {
//            int Value = extras.getInt("id");
//            if(Value>0){
//                if(mydb.updateExpense(id_To_Update,name.getText().toString(),
//                        giver.getText().toString(), date.getText().toString(),
//                        amount.getText().toString())){
//                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(),TripDetailExpenseActivity.class);
//                    startActivity(intent);
//                } else{
//                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
//                }
//            } else{
//                if(mydb.insertExpense(name.getText().toString(), giver.getText().toString(),
//                        date.getText().toString(), amount.getText().toString())){
//                    Toast.makeText(getApplicationContext(), "done",
//                            Toast.LENGTH_SHORT).show();
//                } else{
//                    Toast.makeText(getApplicationContext(), "not done",
//                            Toast.LENGTH_SHORT).show();
//                }
//                Intent intent = new Intent(getApplicationContext(),TripDetailExpenseActivity.class);
//                startActivity(intent);
//            }
//        }
//    }

}
private void getvalue(){
        names=name.getText().toString();
        dates=date.getText().toString();
        amounts=amount.getText().toString();
        givers=giver.getText().toString();

}
    private void addContact(){
        getvalue();

        db.addExpenses(new ExpenseModel(names,givers,dates,amounts,ShareData.getInstance().current_TripId));
        Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(AddNewExpenseActivity.this,TripDetailExpenseActivity.class);
        startActivity(intent);
    }
}
