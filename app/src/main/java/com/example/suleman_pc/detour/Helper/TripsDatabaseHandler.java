package com.example.suleman_pc.detour.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.Model.TripMembers;
import com.example.suleman_pc.detour.Model.TripModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by suleman-pc on 3/1/2018.
 */

public class TripsDatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tripsManager";

    //  TRIPS Table NAME
    private static final String TABLE_TRIPS = "trips";

    // Trips Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TRIP_NAME = "tname";
    private static final String KEY_TRIP_POTO = "tpoto";
    private static final String KEY_TRIP_DATE = "tdate";
    private static final String KEY_TRIP_TYPE = "ttype";


    //Table Expenses Name
    private static final String TABLE_EXPENSES = "expenses";


    //Expense Table Columns
    public static final String EXPENSES_COLUMN_ID = "id";
    public static final String EXPENSES_COLUMN_NAME = "name";
    public static final String EXPENSES_COLUMN_GIVER = "giver";
    public static final String EXPENSES_COLUMN_DATE = "date";
    public static final String EXPENSES_COLUMN_AMOUNT = "amount";
    public static final String EXPENSES_COLUMN_TRIP_ID = "trip_id";


    //Table Trip Mmebers Name
    private static final String TABLE_MEMBERS = "tripmembers";

    //TRIPMEMBERS Table Columns
    public static final String MEMBERS_COLUMN_ID = "id";
    public static final String MEMBERS_COLUMN_NAME = "mname";
    public static final String MEMBERS_COLUMN_TRIP_ID = "trip_id";

    public TripsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TRIPS = "CREATE TABLE " + TABLE_TRIPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TRIP_NAME + " TEXT,"
                + KEY_TRIP_POTO + " BLOB,"
                + KEY_TRIP_DATE + " TEXT,"
                + KEY_TRIP_TYPE + " TEXT "
                + ")";
        String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + EXPENSES_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + EXPENSES_COLUMN_NAME + " TEXT,"
                + EXPENSES_COLUMN_DATE + " TEXT,"
                + EXPENSES_COLUMN_AMOUNT + " TEXT,"
                + EXPENSES_COLUMN_GIVER + " TEXT,"
                + EXPENSES_COLUMN_TRIP_ID + " INTEGER,"
                + " FOREIGN KEY (" + EXPENSES_COLUMN_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + KEY_ID + ")" + ")";

        String CREATE_TABLE_MEMBERS = "CREATE TABLE " + TABLE_MEMBERS + "("
                + MEMBERS_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + MEMBERS_COLUMN_NAME + " TEXT,"
                + MEMBERS_COLUMN_TRIP_ID + " INTEGER,"
                + " FOREIGN KEY (" + MEMBERS_COLUMN_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + KEY_ID + ")" + ")";

        try {
            db.execSQL(CREATE_TABLE_TRIPS);
            db.execSQL(CREATE_TABLE_EXPENSE);
            db.execSQL(CREATE_TABLE_MEMBERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table trips
    public void addTrips(TripModel tripModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TRIP_NAME, tripModel.getFName());
        values.put(KEY_TRIP_POTO, tripModel.getImage());
        values.put(KEY_TRIP_DATE, tripModel.getDate());
        values.put(KEY_TRIP_TYPE, tripModel.get_type());

        db.insert(TABLE_TRIPS, null, values);
        db.close();
    }

    //Insert values to the table expenses
    public void addExpenses(ExpenseModel expenseModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(EXPENSES_COLUMN_NAME, expenseModel.getExpenseName());
        values.put(EXPENSES_COLUMN_DATE, expenseModel.getExpenseDate());
        values.put(EXPENSES_COLUMN_AMOUNT, expenseModel.getExpenseAmount());
        values.put(EXPENSES_COLUMN_GIVER, expenseModel.getExpenseGiver());
        values.put(EXPENSES_COLUMN_TRIP_ID, expenseModel.getTrip_id());

        db.insert(TABLE_EXPENSES, null, values);
        db.close();
    }

    public void addMembers(TripMembers membersModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMBERS_COLUMN_NAME, membersModel.getMemberName());
        values.put(MEMBERS_COLUMN_TRIP_ID, membersModel.getTrip_id());

        db.insert(TABLE_MEMBERS, null, values);
        db.close();
    }

    /**
     * Getting All Records
     **/
    public List<TripMembers> getMembers(int i) {
        List<TripMembers> tripMmebersModelList = new ArrayList<TripMembers>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS + " where " + MEMBERS_COLUMN_TRIP_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                TripMembers tripMemberModel = new TripMembers();
                tripMemberModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_ID))));
                tripMemberModel.setMemberName(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_NAME)));
                tripMemberModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_TRIP_ID))));


                // Adding tripModel to list
                tripMmebersModelList.add(tripMemberModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return tripMmebersModelList;

    }
    public List<String> getMembersNames(int i) {
        List<String> tripMmebersModelList = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MEMBERS + " where " + MEMBERS_COLUMN_TRIP_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

//                TripMembers tripMemberModel = new TripMembers();
//                tripMemberModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_ID))));
//                tripMemberModel.setMemberName(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_NAME)));
//                tripMemberModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MEMBERS_COLUMN_TRIP_ID))));


                // Adding tripModel to list
                tripMmebersModelList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // return contact list
        return tripMmebersModelList;

    }
    public List<TripModel> getAllTrips() {
        List<TripModel> tripModelList = new ArrayList<TripModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRIPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TripModel tripModel = new TripModel();
                tripModel.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                tripModel.setFName(cursor.getString(cursor.getColumnIndex(KEY_TRIP_NAME)));
                tripModel.setImage(cursor.getBlob(cursor.getColumnIndex(KEY_TRIP_POTO)));
                tripModel.setDate(cursor.getString(cursor.getColumnIndex(KEY_TRIP_DATE)));
                tripModel.set_type(cursor.getString(cursor.getColumnIndex(KEY_TRIP_TYPE)));

                // Adding tripModel to list
                tripModelList.add(tripModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return tripModelList;
    }

    //getting trip trip
    public String getTripType(int i) {
//        List<String> tripType= new ArrayList<String>();
        String tripType = null;
        String selectQuery = "Select " + KEY_TRIP_TYPE + " FROM " + TABLE_TRIPS + " where " + KEY_ID + " = " + i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tripType = cursor.getString(cursor.getColumnIndex(KEY_TRIP_TYPE));
//                tripType.add(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));

            } while (cursor.moveToNext());
        }
//        return Collections.singletonList(tripType);
        return tripType;
    }

    //Getting Exenses Names In String[]
    public List<String> getExpensesNames(int i) {
        List<String> expenseModelList = new ArrayList<String>();
        String selectQuery = "Select " + EXPENSES_COLUMN_NAME + " FROM " + TABLE_EXPENSES + " where " + EXPENSES_COLUMN_TRIP_ID + " = " + i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                expenseModelList.add(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));

            } while (cursor.moveToNext());
        }
        return expenseModelList;
    }

    //getting Total Amount surf on trip
    public List<String> getTotalAmount(int i) {
        List<String> expenseModelList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  " + EXPENSES_COLUMN_AMOUNT + " FROM  " + TABLE_EXPENSES + " where " + EXPENSES_COLUMN_TRIP_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
//                ExpenseModel expenseModel = new ExpenseModel();

//                expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
                expenseModelList.add(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
            } while (cursor.moveToNext());
        }
        return expenseModelList;
    }

    //getting expenses with current trip id
    public List<ExpenseModel> getExpenses(int i) {
        List<ExpenseModel> expenseModelList = new ArrayList<ExpenseModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM  " + TABLE_EXPENSES + " where " + EXPENSES_COLUMN_TRIP_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExpenseModel expenseModel = new ExpenseModel();
                expenseModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_ID))));
                expenseModel.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));
                expenseModel.setExpenseDate(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_DATE)));
                expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
                expenseModel.setExpenseGiver(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_GIVER)));
                expenseModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_TRIP_ID))));

                // Adding expenseModel to list
                expenseModelList.add(expenseModel);
            } while (cursor.moveToNext());
        }

        // return expense list
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
                expenseModel.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));
                expenseModel.setExpenseDate(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_DATE)));
                expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
                expenseModel.setExpenseGiver(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_GIVER)));
                expenseModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_TRIP_ID))));

                // Adding expenseModel to list
                expenseModelList.add(expenseModel);
            } while (cursor.moveToNext());
        }
        // return complete expense list
        return expenseModelList;
    }

    /**
     * Updating single tripModel
     **/

    public int updateSingleTrip(TripModel tripModel, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRIP_NAME, tripModel.getFName());
        values.put(KEY_TRIP_POTO, tripModel.getImage());
        values.put(KEY_TRIP_DATE, tripModel.getDate());
        values.put(KEY_TRIP_TYPE, tripModel.get_type());

        // updating row
        return db.update(TABLE_TRIPS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Deleting single contact
     **/

    public void deleteTrip(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIPS, KEY_ID + " = ?",
                new String[]{String.valueOf(Id)});
        db.close();
    }

    //delete the all expenses in table
    public void deleteAllExpense(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
//                new String[] { String.valueOf(Id) });
        db.delete(TABLE_EXPENSES, EXPENSES_COLUMN_TRIP_ID + " = " + Id,
                null);
        db.close();
    }

    //Getting singe trip
    public TripModel getSingleTrip(int i) {
        TripModel tripModel = new TripModel();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRIPS + " where " + KEY_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                tripModel.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                tripModel.setFName(cursor.getString(cursor.getColumnIndex(KEY_TRIP_NAME)));
                tripModel.setImage(cursor.getBlob(cursor.getColumnIndex(KEY_TRIP_POTO)));
                tripModel.setDate(cursor.getString(cursor.getColumnIndex(KEY_TRIP_DATE)));
                tripModel.set_type(cursor.getString(cursor.getColumnIndex(KEY_TRIP_TYPE)));

                // Adding tripModel to list

            } while (cursor.moveToNext());
        }

        // return contact list
        return tripModel;
    }

    //delete single trip
    public void deleteSingleExpense(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
                new String[]{String.valueOf(Id)});
        db.close();
    }
        //delete single Member
    public void deleteSingleMember(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMBERS, KEY_ID + " = ?",
                new String[]{String.valueOf(Id)});
        db.close();
    }
    //delete all members of a trip
    public void deleteAllMembers(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_EXPENSES, KEY_ID + " = ?",
//                new String[] { String.valueOf(Id) });
        db.delete(TABLE_MEMBERS, MEMBERS_COLUMN_TRIP_ID + " = " + Id,
                null);
        db.close();
    }

    //Getting single expense in return
    public ExpenseModel getSingleExpenses(int i) {
        ExpenseModel expenseModel = new ExpenseModel();
        // Select All Query
        String selectQuery = "SELECT  * FROM  " + TABLE_EXPENSES + " where " + EXPENSES_COLUMN_ID + " = " + i;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                expenseModel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_ID))));
                expenseModel.setExpenseName(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_NAME)));
                expenseModel.setExpenseDate(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_DATE)));
                expenseModel.setExpenseAmount(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_AMOUNT)));
                expenseModel.setExpenseGiver(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_GIVER)));
                expenseModel.setTrip_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(EXPENSES_COLUMN_TRIP_ID))));

                // Adding expenseModel to list

            } while (cursor.moveToNext());
        }
        // return complete expense list
        return expenseModel;
    }

    //update single expense
    public int updateSingleExpense(ExpenseModel expenseModel, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXPENSES_COLUMN_NAME, expenseModel.getExpenseName());
        values.put(EXPENSES_COLUMN_DATE, expenseModel.getExpenseDate());
        values.put(EXPENSES_COLUMN_AMOUNT, expenseModel.getExpenseAmount());
        values.put(EXPENSES_COLUMN_GIVER, expenseModel.getExpenseGiver());

        // updating row
        return db.update(TABLE_EXPENSES, values, EXPENSES_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
