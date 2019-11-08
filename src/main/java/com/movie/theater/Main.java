package com.movie.theater;

import java.util.ArrayList;

/**
 * Main --- Main class, which handles logic of reading reservation requests from input file,
 * theater seating allotment and writing to output file.
 *
 * @author anurag
 */
public class Main {

    public static final int ROWS = 10;
    public static final int COLUMNS = 20;
    public static String[][] seatingArrangements = new String[ROWS][COLUMNS];
    public static RowsStatus[] rows = new RowsStatus[ROWS];
    public static boolean theaterCompletelyOccupied = false;

    public static void main(String[] args) {

        if (args.length == 0) {
            /* Executes when the input file path is not provided in command line argument */
            System.out.println("Input file path is not given, has to be passed as an argument");
            return;
        }
        String filePath = args[0];
        /* Reads reservation requests data from file */
        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readFromFile(filePath);
//        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readData();
        setSeatingArrangements(requests);
        printSeatAllotment();
        FileReaderAndWriter.printOutput(requests);
        System.out.println(FileReaderAndWriter.writeOutputToFile(requests));
    }

    private static void initializeRows() {
        for (int i = 0; i < rows.length; i++)
            rows[i] = new RowsStatus();
    }

    public static void setSeatingArrangements(ArrayList<ReservationRequest> requests) {

        //for each request
        //check rows no of remaining seats from last,
        //if remaining > requested ...update 2d array, update rows array, update seat for request
        //else iterate through next row near to screen
        //else (continuous failed) assign to max remaining seats row & recursively.
        int totalSeatsOccupied = 0;
        int maxSeatsCanBeOccupied = Main.ROWS * Main.COLUMNS;
        initializeRows();
        for (ReservationRequest request : requests) {
            if (maxSeatsCanBeOccupied == totalSeatsOccupied) {
                theaterCompletelyOccupied = true;
                break;
            } else if (maxSeatsCanBeOccupied < totalSeatsOccupied + request.getNoOfRequestedSeats()) {
                continue;
            }
            boolean isSeatsAllocated = false;
            if (request.getNoOfRequestedSeats() <= Main.COLUMNS) {  //full group assignment
                for (int i = ROWS - 1; i >= 0; i--) {
                    if (rows[i].getRemainingSeats() >= request.getNoOfRequestedSeats()) {
                        int count = request.getNoOfRequestedSeats();
                        while (count > 0) {
                            int pos = rows[i].getEmptyPosition();
                            Seat s = new Seat(Seat.getMap().get(i), pos + 1);
//                            s.setStartAlphabet(Seat.getMap().get(i));
//                            s.setColumn(pos + 1);
                            seatingArrangements[i][pos] = s.toString();
                            rows[i].updateEmptyPosition();
                            rows[i].decrementRemainingSeats();

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
                    Seat s = new Seat(Seat.getMap().get(maxRemainingSeatsRowIndex), pos + 1);
//                    s.setStartAlphabet(Seat.getMap().get(maxRemainingSeatsRowIndex));
//                    s.setColumn(pos + 1);
                    seatingArrangements[maxRemainingSeatsRowIndex][pos] = s.toString();
                    rows[maxRemainingSeatsRowIndex].updateEmptyPosition();
                    rows[maxRemainingSeatsRowIndex].decrementRemainingSeats();

                    request.addSeat(s);
                    seatsTobeFilled--;
                }

                while (seatsTobeFilled > 0 && rows[secondMaxRemainingRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[secondMaxRemainingRowIndex].getEmptyPosition();
                    Seat s = new Seat(Seat.getMap().get(secondMaxRemainingRowIndex), pos + 1);
//                    s.setStartAlphabet(Seat.getMap().get(secondMaxRemainingRowIndex));
//                    s.setColumn(pos + 1);
                    seatingArrangements[secondMaxRemainingRowIndex][pos] = s.toString();
                    rows[secondMaxRemainingRowIndex].updateEmptyPosition();
                    rows[secondMaxRemainingRowIndex].decrementRemainingSeats();

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
