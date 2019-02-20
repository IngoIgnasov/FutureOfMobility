package com.example.markusleemet.garage48;

public class OnTheBussInfo {
    private String stationName;
    private String time;


    public OnTheBussInfo(String stationName, String time) {
        this.stationName = stationName;
        this.time = time;
    }



    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
