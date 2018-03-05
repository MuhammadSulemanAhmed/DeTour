package com.example.suleman_pc.detour.Model;

import java.util.ArrayList;

/**
 * Created by suleman-pc on 2/22/2018.
 */

public class ExpenseModel {
    private String ExpenseDate;
    private int id;



    private int trip_id;
    private String expenseName;
    private String expenseGiver;
    private String expenseAmount;

    public ExpenseModel(int id, String expenseName, String expenseGiver, String expenseDate, String expenseAmount,int trip_id) {
        ExpenseDate = expenseDate;
        this.id = id;
        this.expenseName = expenseName;
        this.expenseGiver = expenseGiver;
        this.expenseAmount = expenseAmount;
        this.trip_id=trip_id;
    }
    public ExpenseModel(String expenseName, String expenseGiver, String expenseDate, String expenseAmount,int trip_id) {
        ExpenseDate = expenseDate;
        this.expenseName = expenseName;
        this.expenseGiver = expenseGiver;
        this.expenseAmount = expenseAmount;
        this.trip_id=trip_id;
    }

    public ExpenseModel() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }



    public String getExpenseGiver() {
        return expenseGiver;
    }

    public void setExpenseGiver(String expenseGiver) {
        this.expenseGiver = expenseGiver;
    }



    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }



    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }



}
