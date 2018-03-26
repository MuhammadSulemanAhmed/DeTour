package com.example.suleman_pc.detour.Adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.suleman_pc.detour.NotePadActivity;
import com.example.suleman_pc.detour.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public NoticeAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    public int toInt(String s)
    {
        return Integer.parseInt(s);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        NoticeViewHolder holder = null;
        if (convertView == null) {
            holder = new NoticeViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.activity_listview, null);

            holder.title = (TextView) convertView.findViewById(R.id.list_title);
            holder.text = (TextView) convertView.findViewById(R.id.list_text);

            convertView.setTag(holder);
        } else {
            holder = (NoticeViewHolder) convertView.getTag();
        }
        holder.title.setId(position);
        holder.text.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        holder.title.setText(Html.fromHtml(song.get(NotePadActivity.INPUT_COLUMN_Title)));
        holder.text.setText(Html.fromHtml(song.get(NotePadActivity.INPUT_COLUMN_Text)));

        return convertView;

    }
}
