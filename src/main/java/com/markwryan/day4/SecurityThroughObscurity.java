package com.markwryan.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Advent of Code 2016 Day 4
 */
public class SecurityThroughObscurity {

    private static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k',
            'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day4/day4.txt";
        List<String> inputs = Files.readAllLines(Paths.get(fileName));
        int sectorTotal = 0;
        for(String input : inputs) {
            String checksum = getChecksum(input);
            String roomCode = getRoomCode(input);
            int sectorNumber = getSectorNumber(input);
            if(isValid(roomCode, checksum)) {
                sectorTotal += sectorNumber;
            }
        }

        System.out.println("Total of Valid Sectors: " + sectorTotal);
    }

    private static boolean isValid(String roomCode, String checksum) {
        if(roomCode == null || checksum == null) {
            return false;
        }

        List<CharacterCount> characterCounts = getCharacterCounts(roomCode);
        char[] checksumEntries = checksum.toCharArray();

        for(int i =0; i < checksumEntries.length; i++) {
            if(characterCounts.get(i).character != checksumEntries[i]) {
                return false;
            }
        }

        return true;
    }

    private static List<CharacterCount> getCharacterCounts(String roomCode) {
        List<CharacterCount> occurrences = new ArrayList<>();
        String[] roomCodeEntries = roomCode.split("-");
        for(String roomCodeEntry : roomCodeEntries) {
            char[] entries = roomCodeEntry.toCharArray();
            for(char entry : entries) {
                boolean exists = false;
                for(CharacterCount count : occurrences) {
                    if(count.character == entry) {
                        count.update();
                        exists = true;
                    }
                }
                if(!exists) {
                    occurrences.add(new CharacterCount(entry));
                }
            }
        }
        occurrences.sort(CharacterCount::compare);

        return occurrences;
    }

    private static String getChecksum(String input) {
        int checksumStartingBracket = input.indexOf('[');
        if(checksumStartingBracket >= 0) {

        return input.substring(checksumStartingBracket + 1, input.length() -1);
        }
        return null;
    }

    private static String getRoomCode(String input) {
        int dashIndex = input.lastIndexOf('-');
        if(dashIndex >= 0) {
            return input.substring(0, input.lastIndexOf('-'));
        }
        return null;
    }

    private static int getSectorNumber(String input) {
        int dashIndex = input.lastIndexOf('-');
        int bracketIndex = input.lastIndexOf('[');
        if(dashIndex >= 0 && bracketIndex >= 0 ){

        String sector = input.substring(input.lastIndexOf('-') + 1, input.indexOf('['));
        return Integer.parseInt(sector);

        }
        return -1;
    }

    private static class CharacterCount {
        char character;
        int count;

        CharacterCount(char character) {
            this.character = character;
            this.count = 1;
        }

        void update() {
            this.count++;
        }

        static int compare(CharacterCount c1, CharacterCount c2) {
            int c =  c2.count - c1.count;
            if(c == 0) {
                int c1Index = -1;
                int c2Index = -1;
                for(int i =0; i < alphabet.length; i++) {
                    if(c1.character == alphabet[i]) {
                        c1Index = i;
                    }
                }

                for(int i =0; i < alphabet.length; i++) {
                    if(c2.character == alphabet[i]) {
                        c2Index = i;
                    }
                }

                return c1Index - c2Index;
            }
            return c;
        }
    }
}
