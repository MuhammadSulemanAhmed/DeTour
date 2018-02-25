package com.example.suleman_pc.detour.Model;

/**
 * Created by suleman-pc on 2/22/2018.
 */

public class AddNewExpenseDb {
    private String ExpenseDate;
    private int id;
    private String expenseName;
    private String expenseGiver;
    private String expenseAmount;

    public AddNewExpenseDb(int id,  String expenseName, String expenseGiver,String expenseDate, String expenseAmount) {
        ExpenseDate = expenseDate;
        this.id = id;
        this.expenseName = expenseName;
        this.expenseGiver = expenseGiver;
        this.expenseAmount = expenseAmount;
    }
    public AddNewExpenseDb(  String expenseName, String expenseGiver,String expenseDate, String expenseAmount) {
        ExpenseDate = expenseDate;
        this.expenseName = expenseName;
        this.expenseGiver = expenseGiver;
        this.expenseAmount = expenseAmount;
    }

    public AddNewExpenseDb() {

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



}
