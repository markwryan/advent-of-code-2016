package com.markwryan.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mark on 12/9/16.
 */
public class TwoFactorAuthentication {

    private static final int rows = 6;
    private static final int columns = 50;
    private Pixel[][] screen;
    private Pattern rectPattern, rotateRowPattern, rotateColumnPattern;
    private static final String rectRegex = "(\\d+)x(\\d+)";
    private static final String rotateRowRegex = "y=(\\d+) by (\\d+)";
    private static final String rotateColumnRegex = "x=(\\d+) by (\\d+)";

    TwoFactorAuthentication() {
        screen = new Pixel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                screen[i][j] = new Pixel();
            }
        }
        rectPattern = Pattern.compile(rectRegex);
        rotateRowPattern = Pattern.compile(rotateRowRegex);
        rotateColumnPattern = Pattern.compile(rotateColumnRegex);

    }

    int partOne(List<String> inputs) {
        for (String line : inputs) {
            if (line.startsWith("rect")) {
                Matcher m = rectPattern.matcher(line);
                m.find();
                int column = Integer.parseInt(m.group(1));
                int row = Integer.parseInt(m.group(2));
                addRect(row, column);
            } else if (line.startsWith("rotate row")) {
                Matcher m = rotateRowPattern.matcher(line);
                m.find();
                int row = Integer.parseInt((m.group(1)));
                int amount = Integer.parseInt(m.group(2));

                rightShiftRow(row, amount);
            } else if (line.startsWith("rotate column")) {
                Matcher m = rotateColumnPattern.matcher(line);
                m.find();
                int column = Integer.parseInt((m.group(1)));
                int amount = Integer.parseInt(m.group(2));
                downShiftColumn(column, amount);
            }
        }

        int totalLit = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (screen[i][j].lit) {
                    totalLit++;
                }
            }
        }
        for(int i = 0; i < rows; i++) {
            System.out.println("");
            for(int j = 0; j < columns; j++) {
                if(screen[i][j].lit) {
                    System.out.print('0');
                }else {
                    System.out.print(' ');
                }
            }
        }
        return totalLit;
    }

    void addRect(int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                screen[i][j].lit = true;
            }
        }
    }

    void rightShiftRow(int row, int amount) {
        for (int i = 0; i < columns; i++) {
            if (!screen[row][i].visited && screen[row][i].lit) {
                int destination = i + amount;

                if (destination >= columns) {
                    destination = destination - columns;
                }

                screen[row][i].lit = screen[row][i].hasTwo ? true : false;
                screen[row][i].visited = false;
                if (!screen[row][destination].lit) {
                    screen[row][destination].lit = true;
                    screen[row][destination].visited = true;
                } else {
                    screen[row][destination].hasTwo = true;
                    screen[row][destination].visited = false;
                }
            }
        }
        for (int j = 0; j < columns; j++) {
            screen[row][j].hasTwo = false;
            screen[row][j].visited = false;
        }
    }

    void downShiftColumn(int column, int amount) {
        for (int i = 0; i < rows; i++) {
            if (!screen[i][column].visited && screen[i][column].lit) {
                int destination = i + amount;
                if (destination >= rows) {
                    destination = destination - rows;
                }
                screen[i][column].lit = screen[i][column].hasTwo ? true : false;
                screen[i][column].visited = false;
                if (!screen[destination][column].lit) {
                    screen[destination][column].lit = true;
                    screen[destination][column].visited = true;
                } else {
                    screen[destination][column].hasTwo = true;
                    screen[destination][column].visited = false;
                }

            }
        }
        for (int j = 0; j < rows; j++) {
            screen[j][column].hasTwo = false;
            screen[j][column].visited = false;
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day8/day8.txt";
        List<String> inputs = Files.readAllLines(Paths.get(fileName));
        TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
        int part1 = twoFactorAuthentication.partOne(inputs);
        System.out.println("Part 1: " + part1);
    }

    private class Pixel {
        boolean lit, visited, hasTwo;

        Pixel() {
            this.lit = false;
            this.visited = false;
            this.hasTwo = false;
        }
    }
}
