package com.movie.theater;

import java.util.ArrayList;

public class Main {

    public static final int ROWS = 10;
    public static final int COLUMNS = 20;
    public static int[][] seatingArrangements = new int[ROWS][COLUMNS];
    public static RowsStatus[] rows = new RowsStatus[ROWS];

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Input file path is not given, has to be passed as an argument");
            return;
        }
        String filePath = args[0];
        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readFromFile(filePath);
//        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readData();
        initializeRows();
        setSeatingArrangements(requests);
        printSeatAllotment();
        FileReaderAndWriter.printOutput(requests);
        System.out.println(FileReaderAndWriter.writeOutputToFile(requests));
    }

    private static void initializeRows() {
        for (int i = 0; i < rows.length; i++)
            rows[i] = new RowsStatus();
    }

    private static void setSeatingArrangements(ArrayList<ReservationRequest> requests) {

        //for each request
        //check rows no of remaining seats from last,
        //if remaining > requested ...update 2d array, update rows array, update seat for request
        //else iterate through next row near to screen
        //else (continuous failed) assign to max remaining seats row & recursively.
        int totalSeatsOccupied = 0;
        int maxSeatsCanBeOccupied = Main.ROWS * Main.COLUMNS;
        for (ReservationRequest request : requests) {
            if (maxSeatsCanBeOccupied == totalSeatsOccupied) {
                break;
            } else if (maxSeatsCanBeOccupied < totalSeatsOccupied + request.getNoOfRequestedSeats()) {
                continue;
            }
            boolean isSeatsAllocated = false;
            if (request.getNoOfRequestedSeats() <= Main.COLUMNS) {  //full group assignment
                for (int i = ROWS - 1; i >= 0; i--) {
                    if (rows[i] == null) {
                        rows[i] = new RowsStatus();
                    }
                    if (rows[i].getRemainingSeats() >= request.getNoOfRequestedSeats()) {
                        int count = request.getNoOfRequestedSeats();
                        while (count > 0) {
                            int pos = rows[i].getEmptyPosition();
                            seatingArrangements[i][pos] = 1;
                            rows[i].updateEmptyPosition();
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
            }
            int seatsTobeFilled = request.getNoOfRequestedSeats();
            while (!isSeatsAllocated) {     //partial gp assignment
                //partial allotment
                //find max, second max remaining seats rows.
                //fill them, if requestedseats != 0, then find again max & second max
                //  else set isSeatsAllocated to true

                int maxRemainingSeatsRowIndex = ROWS - 1, secondMaxRemainingRowIndex = ROWS - 1;
                int maxSeats = rows[ROWS - 1].getRemainingSeats();
                int secondMaxSeats = maxSeats;
                for (int i = ROWS - 2; i >= 0; i--) {
                    if (rows[i].getRemainingSeats() > maxSeats) {
                        secondMaxSeats = maxSeats;
                        secondMaxRemainingRowIndex = maxRemainingSeatsRowIndex;
                        maxSeats = rows[i].getRemainingSeats();
                        maxRemainingSeatsRowIndex = i;
                    } else if (rows[i].getRemainingSeats() > secondMaxSeats) {
                        secondMaxRemainingRowIndex = i;
                        secondMaxSeats = rows[i].getRemainingSeats();
                    }
                }

                while (seatsTobeFilled > 0 && rows[maxRemainingSeatsRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[maxRemainingSeatsRowIndex].getEmptyPosition();
                    seatingArrangements[maxRemainingSeatsRowIndex][pos] = 1;
                    rows[maxRemainingSeatsRowIndex].updateEmptyPosition();
                    rows[maxRemainingSeatsRowIndex].decrementRemainingSeats();
                    Seat s = new Seat();
                    s.setStartAlphabet(Seat.getMap().get(maxRemainingSeatsRowIndex));
                    s.setColumn(pos + 1);
                    request.addSeat(s);
                    seatsTobeFilled--;
                }

                while (seatsTobeFilled > 0 && rows[secondMaxRemainingRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[secondMaxRemainingRowIndex].getEmptyPosition();
                    seatingArrangements[secondMaxRemainingRowIndex][pos] = 1;
                    rows[secondMaxRemainingRowIndex].updateEmptyPosition();
                    rows[secondMaxRemainingRowIndex].decrementRemainingSeats();
                    Seat s = new Seat();
                    s.setStartAlphabet(Seat.getMap().get(secondMaxRemainingRowIndex));
                    s.setColumn(pos + 1);
                    request.addSeat(s);
                    seatsTobeFilled--;
                }
                if (seatsTobeFilled == 0) {
                    isSeatsAllocated = true;
                }
            }
            totalSeatsOccupied = totalSeatsOccupied + request.getNoOfRequestedSeats();
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
