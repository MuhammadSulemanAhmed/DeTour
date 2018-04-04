package com.example.suleman_pc.detour;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suleman_pc.detour.Common1.ShareData;
import com.example.suleman_pc.detour.NearbyModel.Helper.TripsDatabaseHandler;
import com.example.suleman_pc.detour.Model.TripModel;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EditTripActivity extends AppCompatActivity {

    private EditText fname;
    private ImageView pic;
    private String f_name;
    private String t_date;
    private Bitmap bp;
    private byte[] photo;
    private byte[] pics;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Spinner spinnerDropDown;
    TripModel dataModel;
    private int sid;
    TripsDatabaseHandler dbupdate;
    String[] trip_type = {
            "",
            "Individual",
            "Group",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        //Instantiate database handler
        dbupdate = new TripsDatabaseHandler(this);
        pic = (ImageView) findViewById(R.id.pic);
        fname = (EditText) findViewById(R.id.txt1);
       TripModel model=dbupdate.getSingleTrip(ShareData.getInstance().current_TripId);

        //Date Picker and show
        dateView = findViewById(R.id.tvdate);

       try{

           fname.setText(model.getFName());
        dateView.setText(model.getDate());
        pics=model.getImage();
           //converting byte to bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(pics, 0, pics.length);
        pic.setImageBitmap(bitmap);
       }catch (Exception e){
           Toast.makeText(this,"nothung change",Toast.LENGTH_SHORT).show();
       }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        //Select Category
        spinnerDropDown = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.
                R.layout.simple_spinner_dropdown_item, trip_type);

        spinnerDropDown.setAdapter(adapter);


        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Get select item
                sid = spinnerDropDown.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), "You have selected Trip Type : " + trip_type[sid],
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }
    //date
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void buttonClicked(View v){
        int id=v.getId();

        switch(id){

            case R.id.save:

                if(fname.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Name edit text is empty, Enter name", Toast.LENGTH_LONG).show();
                }  else{
                    UpdateTrip();
                    Intent intent=new Intent(EditTripActivity.this,TripsActivity.class);
                    startActivity(intent);
                    this.finish();
                }

                break;

            case R.id.pic:
                selectImage();
                break;
        }
    }

    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                       try {
                           pic.setImageBitmap(bp);
                       }catch (Exception e){
                           Toast.makeText(getApplicationContext(),"Picture is not changed", Toast.LENGTH_LONG).show();
                       }
                    }
                }
        }
    }

    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)

    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }
    // function to get values from the Edittext and image
    private void getValues(){
        f_name = fname.getText().toString();
        photo = profileImage(bp);
        t_date=dateView.getText().toString();
    }

    //Insert data to the database
    private void UpdateTrip(){
        getValues();
        dbupdate.updateSingleTrip(new TripModel(f_name, photo,t_date),ShareData.getInstance().current_TripId);
        Toast.makeText(getApplicationContext(),"Updated successfully", Toast.LENGTH_LONG).show();
    }
}
