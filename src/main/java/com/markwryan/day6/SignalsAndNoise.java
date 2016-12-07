package com.markwryan.day6;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllLines;

/**
 * Advent of Code Day 6
 */
public class SignalsAndNoise {

    public static void main(String[] args) throws IOException {
        Position[] positions = new Position[8];
        String fileName = "src/main/resources/day6/day6.txt";
        List<String> inputs = readAllLines(Paths.get(fileName));
        for(String input : inputs) {
            char[] places = input.toCharArray();
            for(int i=0; i < places.length; i++) {
                if(positions[i] != null) {

                    Map<Character, Integer> entries = positions[i].getEntries();
                    if(entries.containsKey(places[i])) {
                        entries.put(places[i], entries.get(places[i]) + 1);
                    }
                    else {
                        entries.put(places[i], 1);
                    }
                    positions[i].setEntries(entries);
                }
                else {
                    Position position = new Position();
                    Map<Character,Integer> entries = new HashMap<>();
                    entries.put(places[i], 1);
                    position.setEntries(entries);
                    positions[i] = position;
                }
            }
        }
        //Part 1
        for(Position position : positions) {
            int maxCount = -1;
            char winningCharacter = ' ';
            for(Character key : position.entries.keySet()) {
                if(position.entries.get(key) > maxCount) {
                    winningCharacter = key;
                    maxCount = position.entries.get(key);
                }
            }
            System.out.print(winningCharacter);
        }
        //Part 2
        System.out.println("");
        for(Position position : positions) {
            int maxCount = Integer.MAX_VALUE;
            char winningCharacter = ' ';
            for(Character key : position.entries.keySet()) {
                if(position.entries.get(key) < maxCount) {
                    winningCharacter = key;
                    maxCount = position.entries.get(key);
                }
            }
            System.out.print(winningCharacter);
        }
    }

    private static class Position {
        Map<Character, Integer> entries;

        Position() {
            this.entries = new HashMap<>();
        }

        Map<Character, Integer> getEntries() {
            return entries;
        }

        void setEntries(Map<Character, Integer> entries) {
            this.entries = entries;
        }
    }
}
