package com.example.suleman_pc.detour.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.suleman_pc.detour.Model.ExpenseModel;
import com.example.suleman_pc.detour.R;

import java.util.ArrayList;

/**
 * Created by suleman-pc on 2/12/2018.
 */

public class ExpenseAdpater extends ArrayAdapter<ExpenseModel> {
    Context context;
    ArrayList<ExpenseModel> mexpenses;


    public ExpenseAdpater(Context context, ArrayList<ExpenseModel> tripModel){
        super(context, R.layout.single_expense_layout, tripModel);
        this.context=context;
        this.mexpenses = tripModel;
    }

    public  class  Holder{
        TextView name;
        TextView date;
        TextView amount;
        TextView giver;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position

        ExpenseModel data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
       Holder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
           viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_expense_layout, parent, false);
            viewHolder.name = convertView.findViewById(R.id.expense_name);
            viewHolder.date = convertView.findViewById(R.id.expense_date);
            viewHolder.amount=convertView.findViewById(R.id.expense_amount);
            viewHolder.giver=convertView.findViewById(R.id.expense_giver);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }

        assert data != null;
        viewHolder.name.setText(data.getExpenseName());
        viewHolder.date.setText(data.getExpenseDate());
        viewHolder.amount.setText("RS: "+data.getExpenseAmount());
        viewHolder.giver.setText(data.getExpenseGiver());

        // Return the completed view to render on screen
        return convertView;
    }





}