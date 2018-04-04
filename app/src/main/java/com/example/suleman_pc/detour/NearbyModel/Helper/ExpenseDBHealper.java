package com.example.suleman_pc.detour.NearbyModel.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by suleman-pc on 2/25/2018.
 */

public class ExpenseDBHealper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expense.db";
    public static final String EXPENSES_TABLE_NAME = "expenses";
    public static final String EXPENSES_COLUMN_ID = "id";
    public static final String EXPENSES_COLUMN_NAME = "name";
    public static final String EXPENSES_COLUMN_GIVER = "giver";
    public static final String EXPENSES_COLUMN_DATE = "date";
    public static final String EXPENSES_COLUMN_AMOUNT = "amount";

    public ExpenseDBHealper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table expenses" + "(id integer primary key,name text,giver text,date text,amount text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expenses");
        onCreate(db);
    }

    public boolean insertExpense(String name, String giver, String date, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("giver", giver);
        contentValues.put("date", date);
        contentValues.put("amount", amount);
        db.insert("expenses", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from expenses where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, EXPENSES_TABLE_NAME);
        return numRows;
    }

    public boolean updateExpense(Integer id, String name, String giver, String date, String amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("giver", giver);
        contentValues.put("date", date);
        contentValues.put("amount", amount);
        db.update("expenses", contentValues, "id=?", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteExpense(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("expenses", "id=?", new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllExpenses(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from expenses",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            arrayList.add(res.getString(res.getColumnIndex(EXPENSES_COLUMN_NAME)));
        res.moveToNext();
        }
        return arrayList;
    }
}

