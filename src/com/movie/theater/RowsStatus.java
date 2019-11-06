package com.movie.theater;

public class RowsStatus {
    private int remainingSeats = Main.COLUMNS;
    private int startAllocPosition = 0;

    public RowsStatus() {
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public int getStartAllocPosition() {
        return startAllocPosition;
    }

    public void setStartAllocPosition(int startAllocPosition) {
        this.startAllocPosition = startAllocPosition;
    }

    public void incrementStartAllocPos() {
        this.startAllocPosition = this.startAllocPosition + 1;
    }

    public void decrementRemainingSeats() {
        this.remainingSeats = this.remainingSeats - 1;
    }
}
