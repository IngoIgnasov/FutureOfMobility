package com.example.markusleemet.garage48;

public class BusInfo {
    private String number;
    private String destination;
    private String arriveingTime;


    public BusInfo(String number, String destination, String arriveingTime) {
        this.number = number;
        this.destination = destination;
        this.arriveingTime = arriveingTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getArriveingTime() {
        return arriveingTime;
    }

    public void setArriveingTime(String arriveingTime) {
        this.arriveingTime = arriveingTime;
    }
}
