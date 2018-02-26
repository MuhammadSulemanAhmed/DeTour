package com.example.suleman_pc.detour;

/**
 * Created by suleman-pc on 2/26/2018.
 */

public class Trip {
    private String tripdate;
    private String tripName;

    public Trip(String tripName,String tripdate) {
        this.tripName = tripName;
        this.tripdate=tripdate;
    }
    public String getTripdate() {
        return tripdate;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }



    public void setTripdate(String tripdate) {
        this.tripdate = tripdate;
    }




}
