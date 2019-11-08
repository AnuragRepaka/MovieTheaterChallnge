package com.movie.theater;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test case class for Main.java
 *
 * @author anurag repaka
 */
public class MainTest {

    /*
     * Maximum rows in theater is 26.
     * */

    @Test
    public void rowsInTheaterLesserThanTwentySixTest() {
        Assert.assertTrue(Main.ROWS < 26);
    }

    @Test
    public void seatAllotmentTestPositiveTest() {
        ReservationRequest request = new ReservationRequest("R0001", 4);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request);
        Main.setSeatingArrangements(list);
        ArrayList<Seat> seats = new ArrayList<Seat>();
        seats.add(new Seat("J", 1));
        seats.add(new Seat("J", 2));
        seats.add(new Seat("J", 3));
        seats.add(new Seat("J", 4));
        ArrayList<Seat> allocatedSeats = request.getAllocatedSeatsList();
        for (int i = 0; i < request.getNoOfRequestedSeats(); i++) {
            Assert.assertEquals(seats.get(i).toString(), allocatedSeats.get(i).toString());
        }
    }

    @Test
    public void seatAllocationRequestSeatsGreaterThanRowLengthTest() {
        ReservationRequest request1 = new ReservationRequest("R0001", 24);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request1);
        Main.setSeatingArrangements(list);
        ArrayList<Seat> seatsList1 = new ArrayList<Seat>();
        seatsList1.add(new Seat("J", 1));
        seatsList1.add(new Seat("J", 2));
        seatsList1.add(new Seat("J", 3));
        seatsList1.add(new Seat("J", 4));
        seatsList1.add(new Seat("J", 5));
        seatsList1.add(new Seat("J", 6));
        seatsList1.add(new Seat("J", 7));
        seatsList1.add(new Seat("J", 8));
        seatsList1.add(new Seat("J", 9));
        seatsList1.add(new Seat("J", 10));
        seatsList1.add(new Seat("J", 11));
        seatsList1.add(new Seat("J", 12));
        seatsList1.add(new Seat("J", 13));
        seatsList1.add(new Seat("J", 14));
        seatsList1.add(new Seat("J", 15));
        seatsList1.add(new Seat("J", 16));
        seatsList1.add(new Seat("J", 17));
        seatsList1.add(new Seat("J", 18));
        seatsList1.add(new Seat("J", 19));
        seatsList1.add(new Seat("J", 20));
        seatsList1.add(new Seat("I", 1));
        seatsList1.add(new Seat("I", 2));
        seatsList1.add(new Seat("I", 3));
        seatsList1.add(new Seat("I", 4));
        ArrayList<Seat> allocatedSeats1 = request1.getAllocatedSeatsList();
        for (int i = 0; i < request1.getNoOfRequestedSeats(); i++) {
            assertEquals(seatsList1.get(i).toString(), allocatedSeats1.get(i).toString());
        }
    }

    /*
     * Sitting together is the first preference of customers
     * */

    @Test
    public void allocateSeatsTogetherPriorityTest() {
        ReservationRequest request1 = new ReservationRequest("R0001", 10);
        ReservationRequest request2 = new ReservationRequest("R0002", 12);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request1);
        list.add(request2);
        Main.setSeatingArrangements(list);
        ArrayList<Seat> seatsList1 = new ArrayList<Seat>();
        seatsList1.add(new Seat("J", 1));
        seatsList1.add(new Seat("J", 2));
        seatsList1.add(new Seat("J", 3));
        seatsList1.add(new Seat("J", 4));
        seatsList1.add(new Seat("J", 5));
        seatsList1.add(new Seat("J", 6));
        seatsList1.add(new Seat("J", 7));
        seatsList1.add(new Seat("J", 8));
        seatsList1.add(new Seat("J", 9));
        seatsList1.add(new Seat("J", 10));
        ArrayList<Seat> seatsList2 = new ArrayList<Seat>();
        seatsList2.add(new Seat("I", 1));
        seatsList2.add(new Seat("I", 2));
        seatsList2.add(new Seat("I", 3));
        seatsList2.add(new Seat("I", 4));
        seatsList2.add(new Seat("I", 5));
        seatsList2.add(new Seat("I", 6));
        seatsList2.add(new Seat("I", 7));
        seatsList2.add(new Seat("I", 8));
        seatsList2.add(new Seat("I", 9));
        seatsList2.add(new Seat("I", 10));
        seatsList2.add(new Seat("I", 11));
        seatsList2.add(new Seat("I", 12));
        ArrayList<Seat> allocatedSeats1 = request1.getAllocatedSeatsList();
        for (int i = 0; i < request1.getNoOfRequestedSeats(); i++) {
            Assert.assertEquals(seatsList1.get(i).toString(), allocatedSeats1.get(i).toString());
        }
        ArrayList<Seat> allocatedSeats2 = request2.getAllocatedSeatsList();
        for (int i = 0; i < request2.getNoOfRequestedSeats(); i++) {
            Assert.assertEquals(seatsList2.get(i).toString(), allocatedSeats2.get(i).toString());
        }
    }

    /*
     * No partial seat allotment in a single reservation request.
     * */

    @Test
    public void noPartialSeatAllocationToSingleRequestTest() {
        ReservationRequest request1 = new ReservationRequest("R0001", 25);
        ReservationRequest request2 = new ReservationRequest("R0002", 15);
        ReservationRequest request3 = new ReservationRequest("R0003", 45);
        ReservationRequest request4 = new ReservationRequest("R0004", 25);
        ReservationRequest request5 = new ReservationRequest("R0005", 35);
        ReservationRequest request6 = new ReservationRequest("R0006", 25);
        ReservationRequest request7 = new ReservationRequest("R0007", 25);//195
        ReservationRequest request8 = new ReservationRequest("R0008", 15);//no partial allocation
        ReservationRequest request9 = new ReservationRequest("R0009", 5);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request1);
        list.add(request2);
        list.add(request3);
        list.add(request4);
        list.add(request5);
        list.add(request6);
        list.add(request7);
        list.add(request8);
        list.add(request9);
        Main.setSeatingArrangements(list);
        Assert.assertTrue(request8.getAllocatedSeatsList().isEmpty());
    }

    /*
     * In case all the requested seats could not be allocated in single row, then assign seats in a largest seats available row.
     * */

    @Test
    public void allocateSeatsToLargestAvailableGroupOfSeatsTest() {
        ReservationRequest request1 = new ReservationRequest("R0001", 15);
        ReservationRequest request2 = new ReservationRequest("R0002", 16);
        ReservationRequest request3 = new ReservationRequest("R0003", 12);  //request 11 will be allocated here
        ReservationRequest request4 = new ReservationRequest("R0004", 15);
        ReservationRequest request5 = new ReservationRequest("R0005", 18);
        ReservationRequest request6 = new ReservationRequest("R0006", 19);
        ReservationRequest request7 = new ReservationRequest("R0007", 20);
        ReservationRequest request8 = new ReservationRequest("R0008", 12);  // request 12 will be allocated here
        ReservationRequest request9 = new ReservationRequest("R0009", 10);
        ReservationRequest request10 = new ReservationRequest("R0010", 15); //no allocation to this request
        ReservationRequest request11 = new ReservationRequest("R0011", 6);
        ReservationRequest request12 = new ReservationRequest("R0012", 7);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request1);
        list.add(request2);
        list.add(request3);
        list.add(request4);
        list.add(request5);
        list.add(request6);
        list.add(request7);
        list.add(request8);
        list.add(request9);
        list.add(request10);
        list.add(request11);
        list.add(request12);
        Main.setSeatingArrangements(list);
        ArrayList<Seat> seatsList1 = new ArrayList<Seat>();
        seatsList1.add(new Seat("H", 13));
        seatsList1.add(new Seat("H", 14));
        seatsList1.add(new Seat("H", 15));
        seatsList1.add(new Seat("H", 16));
        seatsList1.add(new Seat("H", 17));
        seatsList1.add(new Seat("H", 18));

        ArrayList<Seat> seatsList2 = new ArrayList<Seat>();
        seatsList2.add(new Seat("C", 13));
        seatsList2.add(new Seat("C", 14));
        seatsList2.add(new Seat("C", 15));
        seatsList2.add(new Seat("C", 16));
        seatsList2.add(new Seat("C", 17));
        seatsList2.add(new Seat("C", 18));
        seatsList2.add(new Seat("C", 19));

        ArrayList<Seat> allocatedSeats1 = request11.getAllocatedSeatsList();
        for (int i = 0; i < request11.getNoOfRequestedSeats(); i++) {
            Assert.assertEquals(seatsList1.get(i).toString(), allocatedSeats1.get(i).toString());
        }
        ArrayList<Seat> allocatedSeats2 = request12.getAllocatedSeatsList();
        for (int i = 0; i < request12.getNoOfRequestedSeats(); i++) {
            Assert.assertEquals(seatsList2.get(i).toString(), allocatedSeats2.get(i).toString());
        }
    }

    @Test
    public void maximumTheaterUtilizationTest() {
        ReservationRequest request1 = new ReservationRequest("R0001", 25);
        ReservationRequest request2 = new ReservationRequest("R0002", 15);
        ReservationRequest request3 = new ReservationRequest("R0003", 45);
        ReservationRequest request4 = new ReservationRequest("R0004", 25);
        ReservationRequest request5 = new ReservationRequest("R0005", 35);
        ReservationRequest request6 = new ReservationRequest("R0006", 25);
        ReservationRequest request7 = new ReservationRequest("R0007", 35);
        ReservationRequest request8 = new ReservationRequest("R0008", 15);
        ReservationRequest request9 = new ReservationRequest("R0009", 5);
        ReservationRequest request10 = new ReservationRequest("R0009", 4);  //194
        ReservationRequest request11 = new ReservationRequest("R0009", 12);
        ReservationRequest request12 = new ReservationRequest("R0009", 2);
        ReservationRequest request13 = new ReservationRequest("R0009", 5);
        ReservationRequest request14 = new ReservationRequest("R0009", 4);  //200
        ReservationRequest request15 = new ReservationRequest("R0009", 4);
        ReservationRequest request16 = new ReservationRequest("R0009", 4);
        ArrayList<ReservationRequest> list = new ArrayList<ReservationRequest>();
        list.add(request1);
        list.add(request2);
        list.add(request3);
        list.add(request4);
        list.add(request5);
        list.add(request6);
        list.add(request7);
        list.add(request8);
        list.add(request9);
        list.add(request10);
        list.add(request11);
        list.add(request12);
        list.add(request13);
        list.add(request14);
        list.add(request15);
        list.add(request16);
        Main.setSeatingArrangements(list);
        Assert.assertTrue(Main.theaterCompletelyOccupied);
    }
}