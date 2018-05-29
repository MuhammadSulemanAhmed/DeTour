package com.example.suleman_pc.detour.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suleman_pc.detour.Model.TripMembers;
import com.example.suleman_pc.detour.R;

import java.util.ArrayList;

public class GroupMemberAdapter extends ArrayAdapter<TripMembers> {
    Context context;
    ArrayList<TripMembers> groupMember;


    public GroupMemberAdapter(Context context, ArrayList<TripMembers> tripGroup){
        super(context, R.layout.single_member_item, tripGroup);
        this.context=context;
        this.groupMember = tripGroup;
    }

    public  class  Holder{
        TextView name;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position

        TripMembers data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Holder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.single_member_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.singlegroupMember);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupMemberAdapter.Holder) convertView.getTag();
        }

        viewHolder.name.setText(data.getMemberName());

        // Return the completed view to render on screen
        return convertView;
    }

}
