package com.example.alasti;

public class AlarmDetails {

    private String alarmTime;
    private Boolean Expanded;
    private int[] requestCodes;

    public AlarmDetails(String alarmTime, int[] requestCodes) {
        this.alarmTime = alarmTime;
        this.requestCodes = requestCodes;
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
}
