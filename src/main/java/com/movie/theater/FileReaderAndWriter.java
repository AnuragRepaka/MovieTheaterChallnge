package com.movie.theater;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * FileReaderAndWriter --- It contains methods that reads reservation requests from input file, generate random input data,
 * validates the input data and write to output file.
 *
 * @author anurag repaka
 */

public class FileReaderAndWriter {
    static File inputFile;

    public static ArrayList<ReservationRequest> readFromFile(String inputFilePath) {
        ArrayList<ReservationRequest> transactionsList = new ArrayList<ReservationRequest>();
        inputFile = new File(inputFilePath);
        writeRandomInputSeatingsToFile();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (!isValidData(line)) {
                    continue;
                }
                String[] data = line.split(" ");
                transactionsList.add(new ReservationRequest(data[0], Integer.parseInt(data[1])));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactionsList;
    }

    public static boolean isValidData(String line) {
        if (line.trim().isEmpty()) {
            //validates white spaces and empty input
            return false;
        }
        String[] data = line.split(" ");
        if (data.length != 2) {
            //validates invalid input length
            return false;
        }
        try {
            int value = Integer.parseInt(data[1]);
            if (value <= 0) {
                //validates negative numbers
                return false;
            }
        } catch (NumberFormatException e) {
            //validates the datatype of input other than int.
            return false;
        }
        return true;
    }

    private static String randomInputGenerator() {
        int sum = 0;
        int limit = Main.ROWS * Main.COLUMNS;
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int counter = 1;
        while (counter < 9999) {
            int r = random.nextInt(Main.COLUMNS) + 5;   //more than row length
            //remove r for crossing the limit, seating capacity > requested seats
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
        return builder.toString();
    }

    private static void writeRandomInputSeatingsToFile() {
        String inputText = randomInputGenerator();
        //System.out.println(inputText);
        try {
            Writer writer = new BufferedWriter(new FileWriter(inputFile));
            writer.write(inputText);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //temp data
    public static ArrayList<ReservationRequest> readData() {
        ArrayList<ReservationRequest> temp = new ArrayList<ReservationRequest>();
        temp.add(new ReservationRequest("R001", 25));
        temp.add(new ReservationRequest("R002", 12));
        temp.add(new ReservationRequest("R003", 40));
        temp.add(new ReservationRequest("R004", 32));
        temp.add(new ReservationRequest("R005", 26));
        temp.add(new ReservationRequest("R006", 12));
        temp.add(new ReservationRequest("R007", 48));//195
        temp.add(new ReservationRequest("R008", 25));
        temp.add(new ReservationRequest("R009", 3));
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

    public static String writeOutputToFile(ArrayList<ReservationRequest> requests) {
        //for manual input
        if (inputFile == null)
            inputFile = new File("/home/anurag/WorkSpace/MovieTheaterChallnge/input.txt");
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
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
