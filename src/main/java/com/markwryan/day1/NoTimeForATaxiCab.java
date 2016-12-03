package com.markwryan.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent of Code Day 1
 */
public class NoTimeForATaxiCab {

    private Direction currentDirection;
    private List<Coordinates> visitedCoordinates;
    private int xAxis;
    private int yAxis;

    private NoTimeForATaxiCab() {
        visitedCoordinates = new ArrayList<>();
        currentDirection = Direction.NORTH;
        xAxis = 0;
        yAxis = 0;

    }
    /**
     * Part one -- find number of blocks to the end location
     * @param directions - Input directions
     * @return blocks away
     */
    private int findDistance(String[] directions) {
        for (String direction : directions) {
            Turn turn = Turn.fromEntry(direction);
            currentDirection = Direction.getDirectionFromTurn(currentDirection, turn);

            int distance = Integer.parseInt(direction.substring(1));

            updateCoordinates(currentDirection, distance);
        }

        return Math.abs(xAxis) + Math.abs(yAxis);
    }


    /**
     * Part two -- end location is actually the first duplicate
     * @param directions - input directions
     * @return blocks away
     */
    private int findDistanceFirstDuplicateLocation(String [] directions) {
        for (String direction : directions) {
            Turn turn = Turn.fromEntry(direction);
            currentDirection = Direction.getDirectionFromTurn(currentDirection, turn);

            int distance = Integer.parseInt(direction.substring(1));
            updateCoordinates(currentDirection, distance);


            for(int i = 0; i < visitedCoordinates.size(); i++) {
                Coordinates currentCoordinate = visitedCoordinates.get(i);
                List<Coordinates> subList = new ArrayList<>();
                subList.addAll(visitedCoordinates);
                subList.remove(i);
                if(subList.contains(currentCoordinate)) {
                    return Math.abs(currentCoordinate.getX() + Math.abs(currentCoordinate.getY()));
                }
            }

        }
        return -1;
    }

    private void updateCoordinates(Direction currentDirection, int distance) {
        switch (currentDirection) {
            case NORTH:
                for(int i = 0; i < distance; i++) {
                    visitedCoordinates.add(new Coordinates(xAxis, yAxis + i));
                }
                yAxis += distance;
                break;
            case EAST:
                for(int i = 0; i < distance; i++) {
                    visitedCoordinates.add(new Coordinates(xAxis + i, yAxis));
                }
                xAxis += distance;
                break;
            case SOUTH:
                for(int i = 0; i < distance; i++) {
                    visitedCoordinates.add(new Coordinates(xAxis, yAxis - i));
                }
                yAxis -= distance;
                break;
            case WEST:
                for(int i = 0; i < distance; i++) {
                    visitedCoordinates.add(new Coordinates(xAxis - i , yAxis));
                }
                xAxis -= distance;
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day1/day1.txt";
        List<String> input = Files.readAllLines(Paths.get(fileName));
        String[] directions = input.get(0).split(", ");

        //Part 1
        NoTimeForATaxiCab pt1Mapper = new NoTimeForATaxiCab();
        int endLocation = pt1Mapper.findDistance(directions);
        System.out.println("Part One ending location is " + endLocation + " bloacks away.");

        //Part 2
        NoTimeForATaxiCab pt2Mapper = new NoTimeForATaxiCab();
        int firstDuplicate = pt2Mapper.findDistanceFirstDuplicateLocation(directions);
        System.out.println("Part two ending location is " + firstDuplicate + " bloacks away.");
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        public static Direction getDirectionFromTurn(Direction currentDirection, Turn turn) {
            switch (currentDirection) {
                case NORTH:
                    return Turn.LEFT == turn ? WEST : EAST;
                case EAST:
                    return Turn.LEFT == turn ? NORTH : SOUTH;
                case SOUTH:
                    return Turn.LEFT == turn ? EAST : WEST;
                case WEST:
                    return Turn.LEFT == turn ? SOUTH : NORTH;
            }
            throw new IllegalArgumentException();
        }
    }

    private enum Turn {
        LEFT,
        RIGHT;

        public static Turn fromEntry(String entry) {
            return entry.startsWith("L") ? LEFT : RIGHT;
        }
    }

    private class Coordinates {
        private int x, y;
        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Coordinates)) {
                return false;
            }

            Coordinates that = (Coordinates) o;

            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
