package com.example.suleman_pc.detour.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suleman_pc.detour.Model.TripModel;
import com.example.suleman_pc.detour.R;

import java.util.ArrayList;

/**
 * Created by suleman-pc on 3/1/2018.
 */

public class EditTripsGridAdapter extends ArrayAdapter<TripModel>  {
    Context context;
    ArrayList<TripModel> mcontact;


    public EditTripsGridAdapter(Context context, ArrayList<TripModel> tripModel){
        super(context, R.layout.single_trip_gridview, tripModel);
        this.context=context;
        this.mcontact= tripModel;
    }

    public static class  Holder{
        TextView nameFV;
        ImageView pic;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        TripModel data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        Holder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {


            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_trip_gridview, parent, false);

            viewHolder.nameFV = (TextView) convertView.findViewById(R.id.trip_name);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.imgView);
            viewHolder.date=convertView.findViewById(R.id.trip_date);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }


        viewHolder.nameFV.setText(data.getFName());
        viewHolder.pic.setImageBitmap(convertToBitmap(data.getImage()));
        viewHolder.date.setText(data.getDate());


        // Return the completed view to render on screen
        return convertView;
    }
    //get bitmap image from byte array

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
}
