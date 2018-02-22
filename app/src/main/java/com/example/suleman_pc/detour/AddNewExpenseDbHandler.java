package com.example.suleman_pc.detour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.suleman_pc.detour.Model.AddNewExpenseDb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suleman-pc on 2/22/2018.
 */

public class AddNewExpenseDbHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "expense";

    // Contacts table name
    private static final String TABLE_EXPENSES= "expenses";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_Giver = "expense_giver";
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT = "amount";

    public AddNewExpenseDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_Giver + " TEXT," + KEY_DATE +"TEXT,"+KEY_AMOUNT+"TEXT"+ ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addExpense(AddNewExpenseDb expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, expense.getExpenseName());
        values.put(KEY_Giver,expense.getExpenseGiver());
        values.put(KEY_DATE,expense.getExpenseDate());
        values.put(KEY_AMOUNT,expense.getExpenseAmount());

        // Inserting Row
        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    AddNewExpenseDb getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSES, new String[] { KEY_ID,
                        KEY_NAME, KEY_Giver,KEY_DATE,KEY_AMOUNT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        AddNewExpenseDb expense = new AddNewExpenseDb(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return contact
        return expense;
    }

    // Getting All Contacts
    public List<AddNewExpenseDb> getAllExpense() {
        List<AddNewExpenseDb> expenseList = new ArrayList<AddNewExpenseDb>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               AddNewExpenseDb expense=new AddNewExpenseDb();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setExpenseName(cursor.getString(1));
                expense.setExpenseGiver(cursor.getString(2));
                expense.setExpenseDate(cursor.getString(3));
                expense.setExpenseAmount(cursor.getString(4));
                // Adding contact to list
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;
    }

    // Updating single contact
    public int updateContact(AddNewExpenseDb expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, expense.getExpenseName());
        values.put(KEY_Giver,expense.getExpenseGiver());
        values.put(KEY_DATE,expense.getExpenseDate());
        values.put(KEY_AMOUNT,expense.getExpenseAmount());

        // updating row
        return db.update(TABLE_EXPENSES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) });
    }

    // Deleting single contact
    public void deleteContact(AddNewExpenseDb expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
