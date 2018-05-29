package com.example.suleman_pc.detour;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.suleman_pc.detour.SharedPreferences.PrefManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackingMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseAuth mAuth;
    String userId, userName, userEmail;
    private static final int MY_PERMISSION_CODE = 1000;
    double lati;
    double longi;
    Switch shareLocationSwitch;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    PrefManager stateSavePrefrence;
    NotificationCompat.Builder mBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tracking_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        stateSavePrefrence=new PrefManager(this);
        shareLocationSwitch = findViewById(R.id.sharelocation_switch);
         mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .setContentTitle("Share Location Enabled")
                        .setContentText("Hello, you are currently sharing your location with circle.");
        shareLocationSwitch.setChecked(stateSavePrefrence.getLocationSwitchState());
        final NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        shareLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateSavePrefrence.saveLocationSwitchState("true");

                    mNotificationManager.notify(001, mBuilder.build());
                    myRef.child(userId).child("ShareLocation").setValue(stateSavePrefrence.getLocationSwitchState());


                } else {
//                    mBuilder=new android.support.v7.app.NotificationCompat.Builder(getApplicationContext())
//                            .setAutoCancel(true);
                    mNotificationManager.cancel(001);
                    stateSavePrefrence.saveLocationSwitchState("false");
                    myRef.child(userId).child("ShareLocation").setValue("false");

                }
            }
        });

        getUserDetails();


    }

    private void getUserDetails() {
        mAuth = FirebaseAuth.getInstance();
    FirebaseUser user= mAuth.getCurrentUser();
        assert user != null;
        userId = user.getUid();
        userName = user.getDisplayName();
        userEmail = user.getEmail();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                mMap.setMyLocationEnabled(true);

                LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                if (location != null) {
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                    markerOptions.position(new LatLng(location.getLatitude(),location.getLongitude()));
//                    markerOptions.title("My Location");
//                    mMap.addMarker(markerOptions);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                    lati = location.getLatitude();
                    longi = location.getLongitude();
//                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title("Marker in Sydney"));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users");
                    myRef.child(userId).child("latitude").setValue(lati);
                    myRef.child(userId).child("longitude").setValue(longi);
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_PERMISSION_CODE);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef = database.getReference("users");
//        myRef.setValue("DeTour");
        database.getReference("App_Title").setValue("DeTour");
        myRef.child(userId).child("name").setValue(userName);
        myRef.child(userId).child("email").setValue(userEmail);
        myRef.child(userId).child("latitude").setValue(lati);
        myRef.child(userId).child("longitude").setValue(longi);
        onMapReady(mMap);

//        myRef.child(userId).child("circles").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                ArrayList<String> circles = (ArrayList<String>)dataSnapshot.getValue();
//                if (circles == null)
//                {
//                    circles = new ArrayList<String>();
//
//                }
//
//                circles.add("123456");
//                myRef.child(userId).child("circles").setValue(circles);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }
}
