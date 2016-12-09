package com.markwryan.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by mark on 12/8/16.
 */
public class InternetProtocolVersion7 {

    public static void main(String[] args) throws IOException {
        int count = 0;
        String fileName = "src/main/resources/day7/day7.txt";
        List<String> inputs = Files.readAllLines(Paths.get(fileName));

        String regex = "(\\w)(?!\\1)(\\w)\\2\\1";
        String hyperRegex = "\\[[\\w]*(\\w)(\\w)\\2\\1[\\w]*]";

        Pattern pattern = Pattern.compile(regex);
        Pattern noHyper = Pattern.compile(hyperRegex);

        for(String line : inputs) {
            if(pattern.matcher(line).find() && !noHyper.matcher(line).find()) {
                count++;
            }
        }
        System.out.println(count);
    }
}
