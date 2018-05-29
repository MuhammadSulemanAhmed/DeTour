package com.example.suleman_pc.detour.Model;

public class TripMembers {
    private int id;
    private int trip_id;
    private String memberName;

    public TripMembers(String newMember, int current_tripId) {
        this.memberName=newMember;
        this.trip_id=current_tripId;
    }

    public TripMembers() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String expenseName) {
        this.memberName = expenseName;
    }


}
