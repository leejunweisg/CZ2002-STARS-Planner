package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents the time and location of a lesson.
 * TimeSlot is combined with LessonType to represent a lesson.
 */
public class TimeSlot implements Serializable {

    /**
     * The day of the week that the lesson is held on.
     * <p>
     * Value of 1 means Monday, 2 means Tuesday, 3 means Wednesday, 4 means Thursday, 5 means Friday, 6 means Saturday, 7 means Sunday.
     */
    private int dayOfWeek;

    /**
     * The location that the lesson is conducted in.
     */
    private String location;

    /**
     * The start time of the lesson.
     * Follows the hh:mm format.
     */
    private LocalTime startTime;

    /**
     * The end time of the lesson.
     * {@link  #endTime} cannot be earlier than {@link #startTime}.
     * Follows the hh:mm format.
     */
    private LocalTime endTime;

    /**
     * Constructor for TimeSlot.
     * @param dayOfWeek This TimeSlot's day of the week.
     * @param location This TimeSlot's location.
     * @param startTime This TimeSlot's start time.
     * @param endTime  This TimeSlot's  end time.
     */
    public TimeSlot(int dayOfWeek, String location, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets this TimeSlot's day of the week.
     * @return This TimeSlot's day of the week.
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Changes this TimeSlot's day of the week.
     * @param dayOfWeek This TimeSlot's new day of the week.
     */
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Gets this TimeSlot's location.
     * @return This TimeSlot's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Changes this TimeSlot's location.
     * @param location This TimeSlot's new location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets this TimeSlot's start time.
     * @return This TimeSlot's start time.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Changes this TimeSlot's start time.
     * @param startTime This TimeSlot's new start time.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets this TimeSlot's end time.
     * @return This TimeSlot's end time.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Changes this TimeSlot's end time.
     * @param endTime This TimeSlot's new end time.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the formatted attribute information of this TimeSlot in a string type.
     * The format is as follows:
     * <p>
     * dayOfWeek: {@link #dayOfWeek}| location: {@link #location}| startTime: {@link #startTime}| endTime: {@link #endTime}
     */
    @Override
    public String toString() {
        return  "dayOfWeek: " + dayOfWeek +
                "| location: '" + location + '\'' +
                "| startTime: " + startTime +
                "| endTime: " + endTime;
    }
}
