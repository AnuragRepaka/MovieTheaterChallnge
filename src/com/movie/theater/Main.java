package com.movie.theater;

import java.util.ArrayList;

public class Main {

    public static final int ROWS = 10;
    public static final int COLUMNS = 20;
    public static int[][] seatingArrangements = new int[ROWS][COLUMNS];

    public static void main(String[] args) {
        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readFromFile();
//        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readData();
        RowsStatus[] rows = new RowsStatus[ROWS];
        setSeatingArrangements(requests, rows);
        printSeatAllotment();
        FileReaderAndWriter.printOutput(requests);
        FileReaderAndWriter.writeOutputToFile(requests);
    }

    private static void setSeatingArrangements(ArrayList<ReservationRequest> requests, RowsStatus[] rows) {

        //for each request
        //check rows no of remaining seats from last,
        //if remaining > requested ...update 2d array, update rows array, update seat for request
        //else iterate through next row near to screen
        //else (continuous failed) assign to max remaining seats row & recursively.

        for (ReservationRequest request : requests) {
            boolean isSeatsAllocated = false;
            for (int i = ROWS - 1; i >= 0; i--) {
                if (rows[i] == null) {
                    rows[i] = new RowsStatus();
                }
                if (rows[i].getRemainingSeats() >= request.getNoOfRequestedSeats()) {
                    int count = request.getNoOfRequestedSeats();
                    while (count > 0) {
                        int pos = rows[i].getStartAllocPosition();
                        seatingArrangements[i][pos] = 1;
                        rows[i].incrementStartAllocPos();
                        rows[i].decrementRemainingSeats();
                        Seat s = new Seat();
                        s.setStartAlphabet(Seat.getMap().get(i));
                        s.setColumn(pos + 1);
                        request.addSeat(s);
                        count--;
                    }
                    isSeatsAllocated = true;
                    break;
                }
            }
            int seatsTobeFilled = request.getNoOfRequestedSeats();
            while (!isSeatsAllocated) {
                //partial allotment
                //find max, second max remaining seats rows.
                //fill them, if requestedseats != 0, then find again max & second max
                //  else set isSeatsAllocated to true

                int maxRemainingSeatsRowIndex = 0, secondMaxRemainingRowIndex = 0;
                for (int i = ROWS - 1; i >= 0; i--) {
                    if (rows[i].getRemainingSeats() > maxRemainingSeatsRowIndex) {
                        secondMaxRemainingRowIndex = maxRemainingSeatsRowIndex;
                        maxRemainingSeatsRowIndex = i;
                    } else if (rows[i].getRemainingSeats() > secondMaxRemainingRowIndex) {
                        secondMaxRemainingRowIndex = i;
                    }
                }


                while (seatsTobeFilled > 0 && rows[maxRemainingSeatsRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[maxRemainingSeatsRowIndex].getStartAllocPosition();
                    seatingArrangements[maxRemainingSeatsRowIndex][pos] = 1;
                    rows[maxRemainingSeatsRowIndex].incrementStartAllocPos();
                    rows[maxRemainingSeatsRowIndex].decrementRemainingSeats();
                    Seat s = new Seat();
                    s.setStartAlphabet(Seat.getMap().get(maxRemainingSeatsRowIndex));
                    s.setColumn(pos + 1);
                    request.addSeat(s);
                    seatsTobeFilled--;
                }

                while (seatsTobeFilled > 0 && rows[secondMaxRemainingRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[secondMaxRemainingRowIndex].getStartAllocPosition();
                    seatingArrangements[secondMaxRemainingRowIndex][pos] = 1;
                    rows[secondMaxRemainingRowIndex].incrementStartAllocPos();
                    rows[secondMaxRemainingRowIndex].decrementRemainingSeats();
                    Seat s = new Seat();
                    s.setStartAlphabet(Seat.getMap().get(secondMaxRemainingRowIndex));
                    s.setColumn(pos + 1);
                    request.addSeat(s);
                    seatsTobeFilled--;
                }
                if (seatsTobeFilled == 0) {
                    System.out.println("success");
                    isSeatsAllocated = true;
                }
            }
        }
    }

    private static void printSeatAllotment() {
        for (int i = 0; i < seatingArrangements.length; i++) {
            for (int j = 0; j < seatingArrangements[0].length; j++) {
                System.out.print(seatingArrangements[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
