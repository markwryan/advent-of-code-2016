package com.markwryan.day9;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

/**
 * Created by Mark on 12/12/2016.
 */
public class ExplosivesInCyberspace {

    private static final String regex = "\\((\\d+)x(\\d+)\\)";
    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day9/day9.txt";
        List<String> inputs = readAllLines(Paths.get(fileName));

        System.out.println(partOne(inputs).length());
        System.out.println(partTwo(inputs));
    }

    static String partOne(List<String> inputs) {
        Pattern pattern = Pattern.compile(regex);
        String uncompressed = "";
        for (String input : inputs) {
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                int characters = Integer.parseInt(matcher.group(1));
                int repeat = Integer.parseInt(matcher.group(2));
                String compressed = input.substring(matcher.end(), matcher.end() + characters);

                uncompressed += input.substring(0, matcher.start());
                for (int i = 0; i < repeat; i++) {
                    uncompressed += compressed;
                }
                int cutPoint = matcher.end() + characters;
                input = input.substring(cutPoint);
                matcher.reset(input);
            }
            uncompressed += input;
        }
        return uncompressed;
    }

    static long partTwo(List<String> inputs) {
        long length = 0;
        for(String input : inputs) {
            int i = 0;
            int[] weights = new int[input.length()];
            for(int k = 0; k < weights.length; k++) {
                weights[k] = 1;
            }
            while(i < input.length()) {
                if(input.charAt(i) == '(') {
                    String sub = input.substring(i, input.indexOf(')', i) + 1);
                    int multSign = sub.indexOf('x');
                    int characters = Integer.parseInt(sub.substring(1, multSign));
                    int weight = Integer.parseInt(sub.substring(multSign + 1, sub.length()-1));
                    int scanStart = i + sub.length();
                    for(int j = scanStart; j < scanStart + characters; j++) {
                        for(char c : alphabet) {
                            char test = input.charAt(j);
                            if(test == c) {
                                weights[j] = weights[j] * weight;
                                break;
                            }
                        }
                    }
                    i = i +  sub.length();

                } else {
                    for(char c : alphabet) {
                        if(input.charAt(i) == c) {
                            length += weights[i];
                            break;
                        }
                    }
                    i++;
                }
            }
        }
        return length;
    }

    private static char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K',
            'L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
}