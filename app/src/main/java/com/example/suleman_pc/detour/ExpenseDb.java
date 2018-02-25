package com.example.suleman_pc.detour;

/**
 * Created by suleman-pc on 2/20/2018.
 */

public class ExpenseDb {
    //private variables
    int _id;
    String _name;
    String _expense_giver;
    String _date;

    // Empty constructor
    public ExpenseDb(){

    }
    // constructor
    public ExpenseDb(int id, String name, String _expense_giver, String _date){
        this._id = id;
        this._name = name;
        this._expense_giver = _expense_giver;
        this._date=_date;
    }

    // constructor
    public ExpenseDb(String name, String _expense_giver, String _date){
        this._name = name;
        this._expense_giver = _expense_giver;
        this._date=_date;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._expense_giver;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._expense_giver = phone_number;
    }

    public String getDate() {
        return _date;
    }
    public void setDate(String _date) {
        this._date = _date;
    }
}
