//2019530 A.E.W Jayatilake
package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public void dataParser() {

        int numberOfLines = getLineCount();
        int numberOfWords = getWordCount();

        readDataSet(numberOfLines, numberOfWords);


    }

    private Scanner readFile() {

        File file = new File("GameFile.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return scanner;
    }

    private void closeScanner(Scanner scanner) {

        scanner.close();

    }

    private int getLineCount() {

        Scanner scanner = readFile();

        int temp = 0;

        if (scanner != null) {

            while (scanner.hasNextLine()) {
                String print = scanner.nextLine();
                temp++;
            }

            closeScanner(scanner);
        }

        return temp;

    }

    private int getWordCount() {

        Scanner scanner = readFile();

        int temp = 0;

        if (scanner != null) {

            while (scanner.hasNext()) {
                String word = scanner.next();
                temp++;
            }

            closeScanner(scanner);
        }

        return temp;

    }

    private void readDataSet(int noOfLine, int noOfWords) {

        int[][] dataSet = new int[noOfLine][noOfWords / noOfLine];
        Scanner scanner = readFile();

        int x = 0;
        int y = 0;

        while (scanner.hasNext()) {

            String word = scanner.next();

            switch (word) {

                case ".":
                    dataSet[x][y] = 0;
                    break;
                case "0":
                    dataSet[x][y] = 1;
                    break;
                case "F":
                    Logic.endX = y;
                    Logic.endY = x;
                    break;
                case "S":
                    Logic.startX = y;
                    Logic.startY = x;
                    break;
                default:
                    System.out.println("Wrong values found");

            }


            y++;

            if (y == noOfWords / noOfLine) {
                y = 0;
                x++;
            }
        }
        Logic.dataArray = dataSet;

    }


}
