package com.movie.theater;

import java.util.HashMap;

public class Seat {

    private static HashMap<Integer, String> map = new HashMap<>();

    static {
        map.put(0, "A");
        map.put(1, "B");
        map.put(2, "C");
        map.put(3, "D");
        map.put(4, "E");
        map.put(5, "F");
        map.put(6, "G");
        map.put(7, "H");
        map.put(8, "I");
        map.put(9, "J");
        map.put(10, "K");
    }

    private String startAlphabet;
    private int column;

    public static HashMap<Integer, String> getMap() {
        return map;
    }

    public void setStartAlphabet(String startAlphabet) {
        this.startAlphabet = startAlphabet;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String toString() {
        return startAlphabet + column;
    }
}
