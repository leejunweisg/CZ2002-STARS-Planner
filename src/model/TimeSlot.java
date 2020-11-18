package model;

import java.io.Serializable;

public class TimeSlot implements Serializable {
    private int dayOfWeek;
    private String location;
    private int startTime;
    private int endTime;

    public TimeSlot(int dayOfWeek, String location, int startTime, int endTime) {
        this.dayOfWeek = dayOfWeek;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
