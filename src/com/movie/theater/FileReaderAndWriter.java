package com.movie.theater;

import java.util.ArrayList;

public class FileReaderAndWriter {

    public static ArrayList<ReservationRequest> readData() {

        //temp code
        ArrayList<ReservationRequest> temp = new ArrayList<>();
        temp.add(new ReservationRequest("R001", 4));
        temp.add(new ReservationRequest("R002", 3));
        temp.add(new ReservationRequest("R003", 4));
        temp.add(new ReservationRequest("R004", 3));
        temp.add(new ReservationRequest("R005", 5));
        temp.add(new ReservationRequest("R006", 6));
        return temp;
    }

    public static void printOutput(ArrayList<ReservationRequest> requests) {
        for (ReservationRequest request : requests
        ) {
            StringBuilder builder = new StringBuilder();
            for (Seat s : request.allocatedSeatsList
            ) {
                builder.append(s.toString());
                builder.append(" ");
            }
            System.out.println(request.getId() + " " + builder.toString());
        }
    }

}
