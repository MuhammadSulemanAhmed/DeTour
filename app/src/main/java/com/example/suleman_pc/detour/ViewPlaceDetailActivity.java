package com.example.suleman_pc.detour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.suleman_pc.detour.NearbyModel.PlaceDetail;
import com.example.suleman_pc.detour.Remote.IGoogleAPIService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPlaceDetailActivity extends AppCompatActivity {
ImageView photo;
IGoogleAPIService mService;
RatingBar rating;
TextView place_address,place_name,openong_hour;
Button btnViewOnMap;
PlaceDetail mPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place_detail);
        mService=Common.getGoogleAPIService();
        photo=findViewById(R.id.photo);
        rating=findViewById(R.id.ratingBar);
        place_address=findViewById(R.id.place_address);
        place_name=findViewById(R.id.place_name);
        openong_hour=findViewById(R.id.place_open_hour);
        btnViewOnMap=findViewById(R.id.btn_on_map);
        //empty all view
        place_name.setText("");
        place_address.setText("");
        openong_hour.setText("");
        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });
       //photo
        if(Common.currentResult.getPhotos() != null && Common.currentResult.getPhotos().length >0)
        {
        Picasso.with(this)
                .load(getPhotoofPlace(Common.currentResult.getPhotos()[0].getPhoto_reference(),1000))
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(photo);
        }
        //rating
        if(Common.currentResult.getRating() != null && !TextUtils.isEmpty(Common.currentResult.getRating()))
        {
            rating.setRating(Float.parseFloat(Common.currentResult.getRating()));
        }
        else
        {
            rating.setVisibility(View.GONE);
        }
        //opening hour
        if(Common.currentResult.getOpening_hours() != null)
        {
            openong_hour.setText("Open Now: "+Common.currentResult.getOpening_hours().getOpen_now());
        }
        else
        {
            openong_hour.setVisibility(View.GONE);
        }
//user service to fetch
        mService.getDetailPlaces(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
                        mPlace=response.body();
                        place_address.setText(mPlace.getResult().getFormatted_address());
                        place_name.setText(mPlace.getResult().getName());
                    }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });
    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder url=new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid="+place_id);
        url.append("&key="+getResources().getString(R.string.google_maps_key));
        return url.toString();
    }

    private String getPhotoofPlace(String photos,int maxWidth) {
        StringBuilder url=new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth="+maxWidth);
        url.append("&photoreference="+photos);
        url.append("&key="+getResources().getString(R.string.google_maps_key));
        return  url.toString();

    }
}
