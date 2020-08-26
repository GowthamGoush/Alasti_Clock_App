package com.example.alasti;

public class AlarmDetails {

    private String alarmTime;
    private Boolean Expanded;
    private int[] requestCodes;
    private int hours;
    private int minutes;
    private int tone;
    private int switched;

    public AlarmDetails(String alarmTime, int[] requestCodes, int hours, int minutes , int tone, int switched) {
        this.alarmTime = alarmTime;
        this.requestCodes = requestCodes;
        this.hours = hours;
        this.minutes = minutes;
        this.tone = tone;
        this.switched = switched;
        Expanded = false;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Boolean getExpanded() {
        return Expanded;
    }

    public void setExpanded(Boolean expanded) {
        Expanded = expanded;
    }

    public int[] getRequestCodes() {
        return requestCodes;
    }

    public void setRequestCodes(int[] requestCodes) {
        this.requestCodes = requestCodes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getTone() {
        return tone;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public int getSwitched() {
        return switched;
    }

    public void setSwitched(int switched) {
        this.switched = switched;
    }
}
