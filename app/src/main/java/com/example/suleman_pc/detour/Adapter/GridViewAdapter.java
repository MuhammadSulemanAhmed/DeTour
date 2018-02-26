package com.example.suleman_pc.detour.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.R;
import com.example.suleman_pc.detour.Trip;
import com.example.suleman_pc.detour.TripDetailActivity;
import com.example.suleman_pc.detour.TripExpenseActivity;

import java.util.Date;

/**
 * Created by suleman-pc on 2/6/2018.
 */
//BaseAdapter
public class GridViewAdapter extends BaseAdapter{
    String[]  result;
    String[] date;
    Context context;
    int [] imageId;

    private static LayoutInflater inflater=null;
    public GridViewAdapter(TripExpenseActivity mainActivity, String[] osNameList, int[] osImages, String[] datelist) {
        // TODO Auto-generated constructor stub
        date=datelist;
        result=osNameList;
        context=mainActivity;
        imageId=osImages;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
        TextView date;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.single_trip_grid, null);
        holder.os_text = rowView.findViewById(R.id.trip_name);
        holder.date=rowView.findViewById(R.id.date);
        holder.os_img = rowView.findViewById(R.id.os_images);
        holder.os_text.setText(result[position]);
        holder.date.setText(date[position]);
        holder.os_img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent intent=new Intent(, TripDetailActivity.class);
//
                v.getContext().startActivity(new Intent(context,TripDetailActivity.class));
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }
}