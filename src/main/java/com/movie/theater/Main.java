package com.movie.theater;

import java.util.ArrayList;

/**
 * Main --- Main class, which handles logic of reading reservation requests from input file,
 * theater seating allotment and writing to output file.
 *
 * @author anurag repaka
 */
public class Main {

    static final int ROWS = 10;
    static final int COLUMNS = 20;
    static boolean theaterCompletelyOccupied = false;
    private static String[][] seatingArrangements = new String[ROWS][COLUMNS];
    private static RowsStatus[] rows = new RowsStatus[ROWS];

    public static void main(String[] args) {

        if (args.length == 0) {
            /* Executes when the input file path is not provided in command line argument */
            System.out.println("Input file path is not given, has to be passed as an argument");
            return;
        }
        String filePath = args[0];
        /* Reads reservation requests data from file */
        ArrayList<ReservationRequest> requests = FileReaderAndWriter.readFromFile(filePath);
        setSeatingArrangements(requests);
        System.out.println(FileReaderAndWriter.writeOutputToFile(requests));
    }

    private static void initializeRows() {
        for (int i = 0; i < rows.length; i++)
            rows[i] = new RowsStatus();
    }

    /**
     * Handles the logic of assigning seats within a movie theater to reservation requests.
     *
     * @param requests reference to list of input reservation requests
     */
    public static void setSeatingArrangements(ArrayList<ReservationRequest> requests) {

        int totalSeatsOccupied = 0;     //counter for number of seats allocated
        int maxSeatsCanBeOccupied = Main.ROWS * Main.COLUMNS;
        initializeRows();           //initialize the theater rows with default remaining seats
        for (ReservationRequest request : requests) {
            //Each reservation request executed in the order it is received
            if (maxSeatsCanBeOccupied == totalSeatsOccupied) {
                //if theater is 100% utilized, then it is not required to process pending requests.
                theaterCompletelyOccupied = true;
                break;
            } else if (maxSeatsCanBeOccupied < totalSeatsOccupied + request.getNoOfRequestedSeats()) {
                //According to my assumption, no partial seat allocation to a single reservation request.
                continue;
            }
            boolean isSeatsAllocated = false;
            if (request.getNoOfRequestedSeats() <= Main.COLUMNS) {
                //if the requested seats are less than row length, then can assign the whole group in a single row.
                for (int i = ROWS - 1; i >= 0; i--) {   //start from back row
                    if (rows[i].getRemainingSeats() >= request.getNoOfRequestedSeats()) {
                        int count = request.getNoOfRequestedSeats();
                        while (count > 0) {
                            int pos = rows[i].getEmptyPosition();
                            Seat s = new Seat(Seat.getMap().get(i), pos + 1);
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
            while (!isSeatsAllocated) {
                /*
                 * find the largest and second largest available seats in the rows. Allocate requested seats
                 * in these rows. If all the requested seats are not allocated, then repeat the process.
                 * */

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
                    seatingArrangements[maxRemainingSeatsRowIndex][pos] = s.toString();
                    rows[maxRemainingSeatsRowIndex].updateEmptyPosition();
                    rows[maxRemainingSeatsRowIndex].decrementRemainingSeats();
                    request.addSeat(s);
                    seatsTobeFilled--;
                }

                while (seatsTobeFilled > 0 && rows[secondMaxRemainingRowIndex].getRemainingSeats() > 0) {
                    int pos = rows[secondMaxRemainingRowIndex].getEmptyPosition();
                    Seat s = new Seat(Seat.getMap().get(secondMaxRemainingRowIndex), pos + 1);
                    seatingArrangements[secondMaxRemainingRowIndex][pos] = s.toString();
                    rows[secondMaxRemainingRowIndex].updateEmptyPosition();
                    rows[secondMaxRemainingRowIndex].decrementRemainingSeats();
                    request.addSeat(s);
                    seatsTobeFilled--;
                }
                if (seatsTobeFilled == 0) {
                    //All requested seats are allocated
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
