package com.example.suleman_pc.detour;

/**
 * Created by suleman-pc on 2/6/2018.
 */

public class Single_Trip {
    public void setSingle_trip_Name(String single_trip_Name) {
        this.single_trip_Name = single_trip_Name;
    }

    public String getSingle_trip_Name() {
        return single_trip_Name;
    }

    String single_trip_Name;

    public void setSingle_trip_image(int single_trip_image) {
        this.single_trip_image = single_trip_image;
    }

    public int getSingle_trip_image() {
        return single_trip_image;
    }

    int single_trip_image;

    public Single_Trip(String trip_name, int single_trip_image)
    {
        this.single_trip_image=single_trip_image;
        this.single_trip_Name =trip_name;
    }

}
