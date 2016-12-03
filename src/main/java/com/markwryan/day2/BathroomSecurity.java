package com.markwryan.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by mark on 12/3/16.
 */
public class BathroomSecurity {
    private int[][] keypad;
    private int row, column;

    public BathroomSecurity() {
        keypad = new int[3][3];
        keypad[0][0] = 1;
        keypad[0][1] = 2;
        keypad[0][2] = 3;
        keypad[1][0] = 4;
        keypad[1][1] = 5;
        keypad[1][2] = 6;
        keypad[2][0] = 7;
        keypad[2][1] = 8;
        keypad[2][2] = 9;

        row = 1;
        column = 1;
    }

    public int getCodeEntry(char[] directions) {
        for(Character direction : directions) {
            switch (direction) {
                case 'U':
                    if(row > 0) {
                        row--;
                    }
                    break;
                case 'D':
                    if(row < 2) {
                        row++;
                    }
                    break;
                case 'L':
                    if(column > 0) {
                        column--;
                    }
                    break;
                case 'R':
                    if(column < 2) {
                        column++;
                    }
                    break;
            }
        }
        return keypad[row][column];
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day2/day2.txt";
        List<String> input = Files.readAllLines(Paths.get(fileName));
        String code = "Bathroom code is ";
        for(String entry : input) {
            BathroomSecurity bathroomSecurity = new BathroomSecurity();
            int key = bathroomSecurity.getCodeEntry(entry.toCharArray());
            code = code + key;
        }
        System.out.println(code);
    }
}
