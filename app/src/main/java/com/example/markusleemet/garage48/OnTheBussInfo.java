package com.example.markusleemet.garage48;

public class OnTheBussInfo implements Comparable<OnTheBussInfo> {
    private String stationName;
    private Integer time;


    public OnTheBussInfo(String stationName, Integer time) {
        this.stationName = stationName;
        this.time = time;
    }


    public void tickDown() {
        this.time--;
    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getArriveingTimeAsString() {
        if (time < 60) {
            return "<1m";
        } else {
            return Integer.toString(time / 60) + "m";
        }
    }

    @Override
    public int compareTo(OnTheBussInfo o) {
        return Integer.compare(this.time,o.time);
    }
}
