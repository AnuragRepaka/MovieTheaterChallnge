package com.movie.theater;

import java.util.ArrayList;

public class ReservationRequest {
    ArrayList<Seat> allocatedSeatsList = new ArrayList<>();
    private String id;
    private int noOfRequestedSeats;

    public ReservationRequest(final String id, final int noOfRequestedSeats) {
        this.id = id;
        this.noOfRequestedSeats = noOfRequestedSeats;
    }

    public int getNoOfRequestedSeats() {
        return this.noOfRequestedSeats;
    }

    public void addSeat(Seat seat) {
        this.allocatedSeatsList.add(seat);
    }

    public String getId() {
        return id;
    }
}
