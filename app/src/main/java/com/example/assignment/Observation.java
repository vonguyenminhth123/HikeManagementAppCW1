package com.example.assignment;

public class Observation {
    private int observationId;
    private String observationName;
    //it can be sightings of animals, types of vegetation encountered during the hike, weather conditions,
    // conditions of the trails, etc.
    private String observationTime;
    private String observationComment;

    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }

    public String getObservationName() {
        return observationName;
    }

    public void setObservationName(String observationName) {
        this.observationName = observationName;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getObservationComment() {
        return observationComment;
    }

    public void setObservationComment(String observationComment) {
        this.observationComment = observationComment;
    }

    public Observation(int observationId, String observationName, String observationTime, String observationComment) {
        this.observationId = observationId;
        this.observationName = observationName;
        this.observationTime = observationTime;
        this.observationComment = observationComment;
    }
    public Observation(String observationName, String observationTime, String observationComment) {
        this.observationName = observationName;
        this.observationTime = observationTime;
        this.observationComment = observationComment;
    }
}
