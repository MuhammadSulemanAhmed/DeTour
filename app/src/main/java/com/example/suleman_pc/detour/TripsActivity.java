package com.example.suleman_pc.detour;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Adapter.TripsGridAdapter;
import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.TripMembers;
import com.example.suleman_pc.detour.Model.TripModel;
import com.example.suleman_pc.detour.SharedPreferences.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;


public class TripsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TripsDatabaseHandler db;
    private GridView lv;
    private TripsGridAdapter data;
    private TripModel dataModel;
    private Dialog dailoge;
    private Dialog dialogKey;
    private Dialog emptyKeyDialoge;
    private Dialog changeKeyDialoge;
    private Dialog forgotKeyDialoge;
    ProgressDialog progressDialog;
    PrefManager sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new TripsDatabaseHandler(this);
        dailoge = new Dialog(this);
        dialogKey = new Dialog(this);
        forgotKeyDialoge = new Dialog(this);
        emptyKeyDialoge = new Dialog(this);
        changeKeyDialoge = new Dialog(this);
        progressDialog = new ProgressDialog(this);
        sharedPref = new PrefManager(this);
        lv = (GridView) findViewById(R.id.list1);
        FloatingActionButton btn = findViewById(R.id.addnew);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripsActivity.this, AddNewTripActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ShowRecords();
//            String keyCheck=new PrefManager(context);
        if (sharedPref.getExpenseKey().equals("")) {
            ShowEmptyKeyDialoge();
        }
    }

    private void ShowEmptyKeyDialoge() {
        emptyKeyDialoge.setContentView(R.layout.add_key_startup_dialoge);
        emptyKeyDialoge.show();
        Button btn = emptyKeyDialoge.findViewById(R.id.cancel_dialoge);
        Button btnadd = emptyKeyDialoge.findViewById(R.id.add_key);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogKey();
                emptyKeyDialoge.dismiss();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyKeyDialoge.dismiss();
            }
        });
    }

    private void ShowRecords() {
        final ArrayList<TripModel> tripModels = new ArrayList<>(db.getAllTrips());
        data = new TripsGridAdapter(this, tripModels);

        lv.setAdapter(data);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = tripModels.get(position);
                Intent intent = new Intent(TripsActivity.this, TripDetailExpenseActivity.class);
                ShareData.getInstance().current_TripId = dataModel.getID();
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel = tripModels.get(position);
                ShareData.getInstance().current_TripId = dataModel.getID();
                String tripType = db.getTripType(dataModel.getID());
                if (tripType.equals("Group Trip")) {
                    Toast.makeText(getApplicationContext(), "Trip Is group", Toast.LENGTH_LONG).show();
                    Showpopup(view);
                } else {
                    Toast.makeText(getApplicationContext(), "Trip Is Individual", Toast.LENGTH_LONG).show();
                    Showpopup1(view);
                }
                return true;
            }
        });
    }

    public void Showpopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.trip_long_click_group_menu);
        popupMenu.show();
    }


    public void Showpopup1(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);

        popupMenu.getMenuInflater().inflate(R.menu.trip_long_click_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
//        popupMenu.inflate(R.menu.trip_long_click_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_trip:
                Intent intent = new Intent(TripsActivity.this, EditTripActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_trip:
                dailog();
                break;
            case R.id.groupMember:
                Intent intent2 = new Intent(TripsActivity.this, AddTripMemberActivity.class);
                startActivity(intent2);
                break;
            case R.id.addgroupMember:
                AddMemberDialouge();
                break;
            default:
                return false;
        }
        return false;
    }

    //
    private void AddMemberDialouge() {

        dailoge.setContentView(R.layout.add_members_dialouge);
        final EditText edt = (EditText) dailoge.findViewById(R.id.edit1);
        final Button btnAdd = dailoge.findViewById(R.id.addMemeber);
        edt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newMember = edt.getText().toString().trim();
                if (newMember.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter the name!", Toast.LENGTH_SHORT).show();
                } else {
                    db.addMembers(new TripMembers(newMember, ShareData.getInstance().current_TripId));
                    Toast.makeText(getApplicationContext(), "Member Added Succefully!", Toast.LENGTH_SHORT).show();
                    edt.setText("");
                }

            }
        });
        dailoge.show();

    }

    public void delete() {
        db.deleteTrip(ShareData.getInstance().current_TripId);
        db.deleteAllExpense(ShareData.getInstance().current_TripId);
        db.deleteAllMembers(ShareData.getInstance().current_TripId);
        data.remove(dataModel);
        data.notifyDataSetChanged();
    }

    public void dailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Trip");
        builder.setMessage("Are you sure to delete trip?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                delete();
                Toast.makeText(getApplicationContext(), "Sucessfully Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expense_key, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        ;
        switch (item.getItemId()) {
//            case R.id.action_settings:
//            break;
            case R.id.addkey:
                if (sharedPref.getExpenseKey() != null) {
                    Toast.makeText(getApplicationContext(), "Key is already set", Toast.LENGTH_SHORT).show();

                } else {
                    DialogKey();
                }

                break;
            case R.id.changekey:
                ChangeKeyDialoge();
//                Toast.makeText(getApplicationContext(), "Change Key?", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgot_key:
                forgotDialoge();
                break;

        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        return true;
    }

    private void forgotDialoge() {
        forgotKeyDialoge.setContentView(R.layout.forgot_key_dialoge);
        forgotKeyDialoge.show();


        final TextView tvemail = forgotKeyDialoge.findViewById(R.id.forgot_email);
        final TextView tvpassword = forgotKeyDialoge.findViewById(R.id.forgot_password);
        Button authbtn = forgotKeyDialoge.findViewById(R.id.btn_authn);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            tvemail.setText(user.getEmail());
            tvemail.setEnabled(false);
            tvemail.clearFocus();
            tvpassword.setFocusable(true);
        }

        authbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pass = tvpassword.getText().toString().trim();
                if (pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    AuthCredential credential = GoogleAuthProvider
                            .getCredential(user.getEmail(), pass);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                            Log.d(TAG, "User re-authenticated.");
                                    progressDialog.dismiss();
                                    forgotKeyDialoge.dismiss();
                                    Toast.makeText(getApplicationContext(), "User authenticated Successfully", Toast.LENGTH_SHORT).show();
                                    DialogKey();
                                }
                            });
                }
            }
        });

    }

    private void ChangeKeyDialoge() {
        changeKeyDialoge.setContentView(R.layout.change_key_dialoge);
        final EditText editTextold = changeKeyDialoge.findViewById(R.id.old_key_edittext);
        final EditText editTextnew = changeKeyDialoge.findViewById(R.id.new_key_edittext);
        final Button changeKeyBtn = changeKeyDialoge.findViewById(R.id.change_key_btn);
        changeKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextold.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter the old Key", Toast.LENGTH_SHORT).show();
                } else if (editTextnew.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter the new Key", Toast.LENGTH_SHORT).show();

                }

                String oldKey = sharedPref.getExpenseKey();
                String oldKey2 = editTextold.getText().toString().trim();

                if (oldKey.equals(oldKey2)) {
                    String newKey = editTextnew.getText().toString().trim();
                    sharedPref.saveExpenseKey(newKey);
                    Toast.makeText(getApplicationContext(), "Key Changed Successfully.", Toast.LENGTH_SHORT).show();
                    changeKeyDialoge.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Key is invalid! Please enter the correct key", Toast.LENGTH_SHORT).show();
                    editTextold.setText("");
                    editTextnew.setText("");
                }
            }

        });

        changeKeyDialoge.show();


    }

    private void DialogKey() {
        dialogKey.setContentView(R.layout.add_key_dialog);
        final EditText editText = dialogKey.findViewById(R.id.edittextkey);
        final Button btn = dialogKey.findViewById(R.id.addKeybtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String key = editText.getText().toString().trim();
                if (key.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter key", Toast.LENGTH_SHORT).show();

                } else {
                    new PrefManager(getApplicationContext()).saveExpenseKey(key);
                    Toast.makeText(getApplicationContext(), "Key Saved Successfully.", Toast.LENGTH_SHORT).show();
                    dialogKey.dismiss();
                }
            }
        });
        dialogKey.show();
    }
}
