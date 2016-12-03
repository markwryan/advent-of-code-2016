package com.markwryan.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Advent of Code Day 2
 */
public class BathroomSecurity {
    private int[][] assumedKeypad;
    private char[][] actualKeypad;
    private int row, column;

    private BathroomSecurity() {
        /*
            123
            456
            789
         */
        assumedKeypad = new int[3][3];
        assumedKeypad[0][0] = 1;
        assumedKeypad[0][1] = 2;
        assumedKeypad[0][2] = 3;
        assumedKeypad[1][0] = 4;
        assumedKeypad[1][1] = 5;
        assumedKeypad[1][2] = 6;
        assumedKeypad[2][0] = 7;
        assumedKeypad[2][1] = 8;
        assumedKeypad[2][2] = 9;

        /*
            1
          2 3 4
        5 6 7 8 9
          A B C
            D
         */
        actualKeypad = new char[5][5];
        actualKeypad[0][0] = ' ';
        actualKeypad[0][1] = ' ';
        actualKeypad[0][2] = '1';
        actualKeypad[0][3] = ' ';
        actualKeypad[0][4] = ' ';

        actualKeypad[1][0] = ' ';
        actualKeypad[1][1] = '2';
        actualKeypad[1][2] = '3';
        actualKeypad[1][3] = '4';
        actualKeypad[1][4] = ' ';

        actualKeypad[2][0] = '5';
        actualKeypad[2][1] = '6';
        actualKeypad[2][2] = '7';
        actualKeypad[2][3] = '8';
        actualKeypad[2][4] = '9';

        actualKeypad[3][0] = ' ';
        actualKeypad[3][1] = 'A';
        actualKeypad[3][2] = 'B';
        actualKeypad[3][3] = 'C';
        actualKeypad[3][4] = ' ';

        actualKeypad[4][0] = ' ';
        actualKeypad[4][1] = ' ';
        actualKeypad[4][2] = 'D';
        actualKeypad[4][3] = ' ';
        actualKeypad[4][4] = ' ';


        row = 1;
        column = 1;
    }

    private int getCodeEntry(char[] directions) {
        for (char direction : directions) {
            switch (direction) {
                case 'U':
                    if (row > 0) {
                        row--;
                    }
                    break;
                case 'D':
                    if (row < 2) {
                        row++;
                    }
                    break;
                case 'L':
                    if (column > 0) {
                        column--;
                    }
                    break;
                case 'R':
                    if (column < 2) {
                        column++;
                    }
                    break;
            }
        }
        return assumedKeypad[row][column];
    }

    private char getActualCodeEntry(char[] directions) {
        for (char direction : directions) {
            switch (direction) {
                case 'U':
                    if (row > 0 && actualKeypad[row - 1][column] != ' ') {
                        row--;
                    }
                    break;
                case 'D':
                    if (row < 4 && actualKeypad[row + 1][column] != ' ') {
                        row++;
                    }
                    break;
                case 'L':
                    if (column > 0 && actualKeypad[row][column - 1] != ' ') {
                        column--;
                    }
                    break;
                case 'R':
                    if (column < 4 && actualKeypad[row][column + 1] != ' ') {
                        column++;
                    }
                    break;
            }
        }
        return actualKeypad[row][column];
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day2/day2.txt";
        List<String> input = Files.readAllLines(Paths.get(fileName));
        //Part 1
        String code = "Bathroom code is ";
        BathroomSecurity bathroomSecurity = new BathroomSecurity();
        for (String entry : input) {
            int key = bathroomSecurity.getCodeEntry(entry.toCharArray());
            code = code + key;
        }
        System.out.println(code);

        //Part 2
        code = "The actual bathroom code is ";
        bathroomSecurity = new BathroomSecurity();
        input = Files.readAllLines(Paths.get(fileName));
        for (String entry : input) {
            char key = bathroomSecurity.getActualCodeEntry(entry.toCharArray());
            code = code + key;
        }
        System.out.println(code);
    }
}
