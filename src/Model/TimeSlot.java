package Model;

import java.io.Serializable;
import java.time.LocalTime;

public class TimeSlot implements Serializable {
    private int dayOfWeek;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(int dayOfWeek, String location, LocalTime startTime, LocalTime endTime) {
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "dayOfWeek: " + dayOfWeek +
                "| location: '" + location + '\'' +
                "| startTime: " + startTime +
                "| endTime: " + endTime;
    }
}
