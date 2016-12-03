package com.markwryan.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by mark on 12/3/16.
 */
public class DirectionMapper {

    private Direction currentDirection = Direction.NORTH;
    private int xAxis = 0;
    private int yAxis = 0;

    private int findDistance(String[] directions) {
        for (String direction : directions) {
            Turn turn = Turn.fromEntry(direction);
            currentDirection = Direction.getDirectionFromTurn(currentDirection, turn);

            int distance = Integer.parseInt(direction.substring(1));

            switch (currentDirection) {
                case NORTH:
                    yAxis += distance;
                    break;
                case EAST:
                    xAxis += distance;
                    break;
                case SOUTH:
                    yAxis -= distance;
                    break;
                case WEST:
                    xAxis -= distance;
                    break;
            }
        }

        return Math.abs(xAxis) + Math.abs(yAxis);
    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day1/day1.txt";
        List<String> input = Files.readAllLines(Paths.get(fileName));
        String[] directions = input.get(0).split(", ");
        DirectionMapper mapper = new DirectionMapper();
        int blocks = mapper.findDistance(directions);
        System.out.println(blocks);
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
}
