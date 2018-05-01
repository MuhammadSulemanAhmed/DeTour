package com.example.suleman_pc.detour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suleman_pc.detour.Helper.NotePadDBHelper;

public class EditNoteActivity extends AppCompatActivity {
NotePadDBHelper db;
String sub_id=null;
String title1=null;
String text2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);
        final EditText title=findViewById(R.id.note_title);
        final EditText text=findViewById(R.id.note_text);
        Button btn=findViewById(R.id.update_note);
         sub_id = getIntent().getStringExtra("id");
         title1=getIntent().getStringExtra("title");
        text2=getIntent().getStringExtra("text");
        title.setText(title1);
        text.setText(text2);
        final String ti=title.getText().toString().trim();
        final String te=text.getText().toString().trim();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.updateSingleNote(sub_id,ti,te);
//                String d= String.valueOf(db.updateSingleNote(sub_id,ti,te));
                Intent intent=new Intent(EditNoteActivity.this,NotePadActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Succesuly updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
