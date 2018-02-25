package com.example.suleman_pc.detour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suleman_pc.detour.Model.Expense;
import com.example.suleman_pc.detour.R;

import java.util.ArrayList;

/**
 * Created by suleman-pc on 2/12/2018.
 */

public class ExpenseAdpater
{

//    private int listItemLayout;
//    private Intent intent;
//
//    public ExpenseAdpater(Context context, int layoutId, ArrayList<Expense> itemList) {
//        super(context, layoutId, itemList);
//        listItemLayout = layoutId;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
////       Expense item = getItem(position);
//
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(listItemLayout, parent, false);
//            viewHolder.expenseName = (TextView) convertView.findViewById(R.id.expense_name);
////            viewHolder.expenseGiver = convertView.findViewById(R.id.expense_givenBy);
////            viewHolder.date = convertView.findViewById(R.id.expense_date);
////            viewHolder.amount = convertView.findViewById(R.id.given_amount);
//            convertView.setTag(viewHolder); // view lookup cache stored in tag
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
////        Intent ii=Intent.getIntent();
//
//        // Populate the data into the template view using the data object
//        viewHolder.expenseName.setText(item.getExpenseName());
////        viewHolder.expenseGiver.setText(item.getExpenseGiver());
////        viewHolder.date.setText(item.getExpenseDate());
////        viewHolder.amount.setText(item.getGivenAmount());
//
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
//
//    public Intent getIntent() {
//        return intent;
//    }
//
//    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
//    private static class ViewHolder {
//        TextView expenseName;
//        TextView expenseGiver;
//        TextView date;
//        TextView amount;
//    }
}
