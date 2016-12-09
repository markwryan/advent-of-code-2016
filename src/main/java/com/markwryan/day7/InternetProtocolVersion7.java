package com.markwryan.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advent of Code Day 7
 */
public class InternetProtocolVersion7 {
    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day7/day7.txt";
        List<String> inputs = Files.readAllLines(Paths.get(fileName));
        partOne(inputs);
        partTwo(inputs);
    }

    private static void partOne(List<String> inputs) throws IOException {
        int count = 0;
        String regex = "(\\w)(?!\\1)(\\w)\\2\\1";
        String hyperRegex = "\\[[\\w]*(\\w)(\\w)\\2\\1[\\w]*]";
        Pattern pattern = Pattern.compile(regex);
        Pattern noHyper = Pattern.compile(hyperRegex);
        for (String line : inputs) {
            if (pattern.matcher(line).find() && !noHyper.matcher(line).find()) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void partTwo(List<String> inputs) throws IOException {
        int count = 0;
        String regex = "\\[[\\w]*(\\w)(?!\\1)(\\w)\\1[\\w]*[]]";
        Pattern pattern = Pattern.compile(regex);
        for (String line : inputs) {
            Matcher rawBAB = pattern.matcher(line);
            String filteredLine = line.replaceAll("\\[\\w*]", "_");
            while (rawBAB.find()) {
                String babRegex = "(\\w)(?!\\1)(\\w)\\1";
                Matcher babMatch = Pattern.compile(babRegex).matcher(rawBAB.group());
                int index = 0; //Use index to not get hit by pattern overlaps.
                while (babMatch.find(index)) {
                    String bab = babMatch.group();
                    if (filteredLine.contains("" + bab.charAt(1) + bab.charAt(0) + bab.charAt(1))) {
                        count++;
                        break;
                    }
                    index++;
                }
            }
        }
        System.out.println(count);
    }
}
