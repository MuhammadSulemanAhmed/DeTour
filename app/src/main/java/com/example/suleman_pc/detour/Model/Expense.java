package com.example.suleman_pc.detour.Model;

/**
 * Created by suleman-pc on 2/12/2018.
 */

public class Expense {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getExpenseName() {
        return expenseName;
    }

    private String expenseName;

    public String getExpenseGiver() {
        return expenseGiver;
    }

    public void setExpenseGiver(String expenseGiver) {
        this.expenseGiver = expenseGiver;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    private String expenseGiver;
    private String givenAmount;
    private String expenseDate;

    public Expense(String expenseName,String expenseGiver,String expenseDate,String given_amount) {

        this.expenseDate=expenseDate;
        this.expenseGiver=expenseGiver;
        this.givenAmount = given_amount;
        this.expenseName = expenseName;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }



    public String getGivenAmount() {
        return givenAmount;
    }

    public void setGiven_amount(String  given_amount) {
        this.givenAmount = given_amount;
    }








}
