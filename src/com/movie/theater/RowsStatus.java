package com.movie.theater;

public class RowsStatus {
    private int remainingSeats;
    private int startAllocPosition;

    public RowsStatus() {
        remainingSeats = Main.COLUMNS;
        startAllocPosition = 0;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public int getEmptyPosition() {
        return startAllocPosition;
    }

    public void setStartAllocPosition(int startAllocPosition) {
        this.startAllocPosition = startAllocPosition;
    }

    public void updateEmptyPosition() {
        this.startAllocPosition = this.startAllocPosition + 1;
    }

    public void decrementRemainingSeats() {
        this.remainingSeats = this.remainingSeats - 1;
    }
}
