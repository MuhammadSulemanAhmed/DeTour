package com.example.suleman_pc.detour.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suleman-pc on 3/1/2018.
 */

public class TripsDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tripsManager";

    //  table TRIPS
       private static final String TABLE_TRIPS = "trips";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_POTO = "poto";

    //Table Expenses
    private static final String TABLE_EXPENSES = "expenses";
    //Columns
    public static final String EXPENSES_COLUMN_ID = "id";
    public static final String EXPENSES_COLUMN_NAME = "name";
    public static final String EXPENSES_COLUMN_GIVER = "giver";
    public static final String EXPENSES_COLUMN_DATE = "date";
    public static final String EXPENSES_COLUMN_AMOUNT = "amount";
    public static final String EXPENSES_COLUMN_TRIP_ID = "trip_id";

    public TripsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TRIPS="CREATE TABLE " + TABLE_TRIPS + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_FNAME +" TEXT,"
                + KEY_POTO  +" BLOB" + ")";
        String CREATE_TABLE_EXPENSE="CREATE TABLE "+TABLE_EXPENSES+"("
                +EXPENSES_COLUMN_ID+" INTEGER PRIMARY KEY,"
                +EXPENSES_COLUMN_NAME+" TEXT,"
                +EXPENSES_COLUMN_DATE+" TEXT,"
                +EXPENSES_COLUMN_AMOUNT+" TEXT,"
                +EXPENSES_COLUMN_GIVER+" TEXT,"
                +EXPENSES_COLUMN_TRIP_ID+" INTEGER,"
                + " FOREIGN KEY ("+EXPENSES_COLUMN_TRIP_ID+") REFERENCES "+TABLE_TRIPS+"("+KEY_ID+")"+")";
        try {
            db.execSQL(CREATE_TABLE_TRIPS);
            db.execSQL(CREATE_TABLE_EXPENSE);
        }catch (Exception e){
            e.printStackTrace();
//            Toast.makeText("show",Toast.LENGTH_SHORT).show();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table trips
    public void addTrips(TripModel tripModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, tripModel.getFName());
        values.put(KEY_POTO, tripModel.getImage() );


        db.insert(TABLE_TRIPS, null, values);
        db.close();
    }

    //Insert values to the table expenses
    public void addExpenses(ExpenseModel expenseModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(EXPENSES_COLUMN_NAME,expenseModel.getExpenseName());
        values.put(EXPENSES_COLUMN_DATE,expenseModel.getExpenseDate() );
        values.put(EXPENSES_COLUMN_AMOUNT,expenseModel.getExpenseAmount());
        values.put(EXPENSES_COLUMN_GIVER,expenseModel.getExpenseGiver());
        values.put(EXPENSES_COLUMN_TRIP_ID,expenseModel.getTrip_id());

        db.insert(TABLE_EXPENSES, null, values);
        db.close();
    }
    /**
     *Getting All Contacts
     **/

    public List<TripModel> getAllContacts() {
        List<TripModel> tripModelList = new ArrayList<TripModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TripModel tripModel = new TripModel();
                tripModel.setID(Integer.parseInt(cursor.getString(0)));
                tripModel.setFName(cursor.getString(1));
                tripModel.setImage(cursor.getBlob(2));


                // Adding tripModel to list
                tripModelList.add(tripModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return tripModelList;
    }
    //getting with current trip id
    public List<ExpenseModel> getExpenses(int i) {
        List<ExpenseModel> expenseModelList = new ArrayList<ExpenseModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM  " + TABLE_EXPENSES + " where "+ EXPENSES_COLUMN_TRIP_ID + " = " + i ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseModel expenseModel = new ExpenseModel();
                expenseModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_ID))));
//            tripModel.setID(Integer.parseInt(cursor.getString(0)));
                expenseModel.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));
//           expenseModel.setExpenseName(cursor.getString(1));
                expenseModel.setExpenseDate(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_DATE)));
//           expenseModel.setExpenseDate(cursor.getString(2));
                expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
//           expenseModel.setExpenseAmount(cursor.getString(3));
                expenseModel.setExpenseGiver(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_GIVER)));
//           3expenseModel.setExpenseGiver(cursor.getString(4));
                expenseModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_TRIP_ID))));

                // Adding tripModel to list
                expenseModelList.add(expenseModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseModelList;
    }
//getting all Expenses
public List<ExpenseModel> getAllExpenses() {
    List<ExpenseModel> expenseModelList = new ArrayList<ExpenseModel>();
    // Select All Query
    String selectQuery = "SELECT  * FROM  " + TABLE_EXPENSES;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
            ExpenseModel expenseModel = new ExpenseModel();
            expenseModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_ID))));
//            tripModel.setID(Integer.parseInt(cursor.getString(0)));
            expenseModel.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));
//           expenseModel.setExpenseName(cursor.getString(1));
            expenseModel.setExpenseDate(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_DATE)));
//           expenseModel.setExpenseDate(cursor.getString(2));
            expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
//           expenseModel.setExpenseAmount(cursor.getString(3));
            expenseModel.setExpenseGiver(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_GIVER)));
//           3expenseModel.setExpenseGiver(cursor.getString(4));
            expenseModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_TRIP_ID))));

            // Adding tripModel to list
            expenseModelList.add(expenseModel);
        } while (cursor.moveToNext());
    }

    // return contact list
    return expenseModelList;
}

    /**
     *Updating single tripModel
     **/

    public int updateContact(TripModel tripModel, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, tripModel.getFName());
        values.put(KEY_POTO, tripModel.getImage());


        // updating row
        return db.update(TABLE_TRIPS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     *Deleting single contact
     **/

    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIPS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

    public void deleteAllExpense(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
//                new String[] { String.valueOf(Id) });
        db.delete(TABLE_EXPENSES, EXPENSES_COLUMN_TRIP_ID+" = "+Id ,
                null);
        db.close();
    }

}
