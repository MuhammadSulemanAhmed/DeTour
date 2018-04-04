package com.example.suleman_pc.detour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MD.ISRAFIL MAHMUD on 7/15/2017.
 */

public class NoteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);
       TextView titleview=(TextView)findViewById(R.id.title_view);
       TextView textview=(TextView)findViewById(R.id.text_view);
        Button btn=findViewById(R.id.edit_note);
        final String sub_id = getIntent().getStringExtra("id");
        final String title=getIntent().getStringExtra("title");
        final String text=getIntent().getStringExtra("text");
        titleview.setText(title);
        textview.setText(text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoteViewActivity.this, EditNoteActivity.class);
                i.putExtra("id", sub_id);
                i.putExtra("title", title);
                i.putExtra("text", text);
                startActivity(i);
            }
        });
    }
}