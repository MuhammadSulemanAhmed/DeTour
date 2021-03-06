package com.example.suleman_pc.detour.Model;

import java.sql.Date;

/**
 * Created by suleman-pc on 3/1/2018.
 */

public class TripModel {
    //private variables
    int _id;
    String _fname;
    byte[] _img;
    String date;
    String _type;


    // Empty constructor
    public TripModel() {

    }

    // constructor
    public TripModel(int id, String fname, byte[] img, String date, String type) {
        this._id = id;
        this._fname = fname;
        this._img = img;
        this.date = date;
        this._type = type;

    }

    // constructor
    public TripModel(String fname, byte[] img, String date, String type) {

        this._fname = fname;
        this._img = img;
        this.date = date;
        this._type = type;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting first name
    public String getFName() {
        return this._fname;
    }

    // setting first name
    public void setFName(String fname) {
        this._fname = fname;
    }

    //getting profile pic
    public byte[] getImage() {
        return this._img;
    }

    //setting profile pic

    public void setImage(byte[] b) {
        this._img = b;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

}
