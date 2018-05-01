package com.example.suleman_pc.detour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.suleman_pc.detour.Adapter.NoticeAdapter;
import com.example.suleman_pc.detour.Helper.NotePadDBHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suleman-pc on 3/26/2018.
 */
public class NotePadActivity extends AppCompatActivity {
    ListView mobile_list;
    NotePadDBHelper mydb;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    public static final String INPUT_COLUMN_ID = "_id";
    public static final String INPUT_COLUMN_Title = "title";
    public static final String INPUT_COLUMN_Text = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);
        mydb = new NotePadDBHelper(getApplicationContext());
        mobile_list = (ListView) findViewById(R.id.mobile_list);
        loadData();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();

    }


    public void addNew(View view) {
        Intent intent = new Intent(this, Input_Note_DataActivity.class);
        startActivity(intent);
    }


    public void loadData() {
        dataList.clear();
        Cursor cursor = mydb.getAllPersons();
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put(INPUT_COLUMN_ID, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_ID)));
                map.put(INPUT_COLUMN_Title, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Title)));
                map.put(INPUT_COLUMN_Text, cursor.getString(cursor.getColumnIndex(INPUT_COLUMN_Text)));


                dataList.add(map);

                cursor.moveToNext();
            }
        }


        NoticeAdapter adapter = new NoticeAdapter(NotePadActivity.this, dataList);
        mobile_list.setAdapter(adapter);

        mobile_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(NotePadActivity.this, NoteViewActivity.class);
                i.putExtra("id", dataList.get(+position).get(INPUT_COLUMN_ID));
                i.putExtra("title", dataList.get(+position).get(INPUT_COLUMN_Title));
                i.putExtra("text", dataList.get(+position).get(INPUT_COLUMN_Text));
                startActivity(i);

            }
        });


        //  list item long press to delete -------------start-----------

        mobile_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                return onLongListItemClick(v,pos,id);
            }
            protected boolean onLongListItemClick(View v, final int pos, long id) {

                /////Display Dialog Here.......................

                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                alertDialog.setTitle("Delete...");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String a=dataList.get(+pos).get(INPUT_COLUMN_ID);
                        mydb.deleteSingleContact(a);
                        loadData();
                    }
                });
                alertDialog.show();
                return true;
            }});

        //--------finish------------
    }
}
