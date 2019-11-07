package com.movie.theater;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class FileReaderAndWriter {
    static File inputFile;

    public static ArrayList<ReservationRequest> readFromFile() {
        ArrayList<ReservationRequest> temp = new ArrayList<>();
        int sum = 0;
        int limit = Main.ROWS * Main.COLUMNS - (Main.COLUMNS);
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int counter = 1;
        while (counter < 9999) {
            int r = random.nextInt(Main.COLUMNS) + 1;
            if (sum + r > limit) {
                break;
            }
            int l = String.valueOf(counter).length();
            if (l == 4) {
                builder.append("R");
            } else if (l == 3) {
                builder.append("R0");
            } else if (l == 2) {
                builder.append("R00");
            } else if (l == 1) {
                builder.append("R000");
            }
            builder.append(counter);
            builder.append(" ");
            builder.append(r);
            builder.append("\n");
            sum = sum + r;
            counter++;
        }

        System.out.println(builder.toString());
        inputFile = new File("input.txt");
        try {
            Writer writer = new BufferedWriter(new FileWriter(inputFile));
            writer.write(builder.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                temp.add(new ReservationRequest(data[0], Integer.parseInt(data[1])));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return temp;
    }

    //temp code
    public static ArrayList<ReservationRequest> readData() {
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
        for (ReservationRequest request : requests) {
            StringBuilder builder = new StringBuilder();
            for (Seat s : request.allocatedSeatsList) {
                builder.append(s.toString());
                builder.append(" ");
            }
            System.out.println(request.getId() + " " + builder.toString());
        }
    }

    public static void writeOutputToFile(ArrayList<ReservationRequest> requests) {

        try {
            String input = inputFile.getAbsolutePath().split("/")[inputFile.getAbsolutePath().split("/").length - 1];
            String output = inputFile.getAbsolutePath().replace(input, "") + "output.txt";
            File outputFile = new File(output);
            Writer writer = new BufferedWriter(new FileWriter(outputFile));
            for (ReservationRequest request : requests) {
                StringBuilder builder = new StringBuilder();
                for (Seat s : request.allocatedSeatsList) {
                    builder.append(s.toString());
                    builder.append(" ");
                }
                String text = request.getId() + " " + builder.toString();
                writer.write(text);
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
