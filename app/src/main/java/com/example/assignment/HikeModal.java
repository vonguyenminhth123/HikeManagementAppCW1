package com.example.assignment;

import java.io.Serializable;
import java.util.Date;
//first, I defined "Hike" object
public class HikeModal implements Serializable {
    private String hikeName;
    private String hikeLocation;
    private String hikeDate;
    private boolean hikeParkingAvailable;
    private String hikeLength;
    private String hikeLevel;
    private String hikeTime;
    private String hikeDescription;
    private String hikeAlert;
    private int id;

    public String getHikeName() {        return hikeName;    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getHikeLocation() {        return hikeLocation;    }

    public void setHikeLocation(String hikeLocation) {
        this.hikeLocation = hikeLocation;
    }
    public String getHikeDate(){return hikeDate;}
    public void setHikeDate(String hikeDate){this.hikeDate = hikeDate;}
    public boolean getHikeParkingAvailable(){return hikeParkingAvailable;}
    public void setHikeParkingAvailable(boolean hikeParkingAvailable){this.hikeParkingAvailable = hikeParkingAvailable;}

    public String getHikeLength() {        return hikeLength;    }

    public void setHikeLength(String hikeLength) {
        this.hikeLength = hikeLength;
    }

    public String getHikeLevel() {        return hikeLevel;    }

    public void setHikeLevel(String hikeLevel) {
        this.hikeLevel = hikeLevel;
    }

    public String getHikeTime() {        return hikeTime;    }

    public void setHikeTime(String hikeTime) {
        this.hikeTime = hikeTime;
    }

    public String getHikeDescription() {        return hikeDescription;    }

    public void setHikeDescription(String hikeDescription) {
        this.hikeDescription = hikeDescription;
    }

    public String getHikeAlert() {        return hikeAlert;    }

    public void setHikeAlert(String hikeAlert) {
        this.hikeAlert = hikeAlert;
    }

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }
    public HikeModal(int hikeId, String hikeName, String hikeLocation, String hikeDate, boolean hikeParkingAvailable,
                String hikeLength, String hikeLevel, String hikeTime, String hikeDescription,
                String hikeAlert) {
        this.id = hikeId;
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
        this.hikeDate = hikeDate;
        this.hikeParkingAvailable = hikeParkingAvailable;
        this.hikeLength = hikeLength;
        this.hikeLevel = hikeLevel;
        this.hikeTime = hikeTime;
        this.hikeDescription = hikeDescription;
        this.hikeAlert = hikeAlert;
    }
    public HikeModal(String hikeName, String hikeLocation, String hikeDate, boolean hikeParkingAvailable,
                String hikeLength, String hikeLevel, String hikeTime, String hikeDescription,
                String hikeAlert) {
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
        this.hikeDate = hikeDate;
        this.hikeParkingAvailable = hikeParkingAvailable;
        this.hikeLength = hikeLength;
        this.hikeLevel = hikeLevel;
        this.hikeTime = hikeTime;
        this.hikeDescription = hikeDescription;
        this.hikeAlert = hikeAlert;
    }
    public HikeModal(){}
}