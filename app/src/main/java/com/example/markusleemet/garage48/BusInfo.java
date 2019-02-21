package com.example.markusleemet.garage48;

public class BusInfo implements Comparable<BusInfo> {
    private String number;
    private String destination;
    private int arriveingTime;
    private String startingStation;

    @Override
    public int compareTo(BusInfo o) {
        return Integer.compare(this.arriveingTime, o.arriveingTime);
    }

    public BusInfo(String number, String startingStation, String destination, int arriveingTime) {
        this.number = number;
        this.startingStation = startingStation;
        this.destination = destination;
        this.arriveingTime = arriveingTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartingStation() {
        return startingStation;
    }


    public String getDestination() {
        return destination;
    }

    public String getTripPath() {
        return startingStation + "-...-" + destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getArriveingTime() {
        return arriveingTime;
    }

    public String getArriveingTimeAsString() {
        if (arriveingTime < 60) {
            return "<1m";
        } else {
            return Integer.toString(arriveingTime / 60) + "m";
        }
    }

    public void tickDown() {
        this.arriveingTime--;
    }

    public void setArriveingTime(int arriveingTime) {
        this.arriveingTime = arriveingTime;
    }

}
