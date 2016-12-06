package com.markwryan.day5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Advent of Code 2015 Day 5 [SLOW AS BALLS]
 */
public class HowAboutANiceGameOfChess {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String doorId = "wtnhxymk";
        Character[] password = new Character[8];
        int index = 0;
        int found = 0;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        StringBuilder sb;
        Pattern pattern = Pattern.compile("00000[0-7].*");
        while (found < 8) {
            byte[] hash = digest.digest((doorId + index).getBytes());
            sb = new StringBuilder();
            for (byte c : hash) {
                sb.append(String.format("%02X", c));
            }
            final String hex = sb.toString();
            if (pattern.matcher(hex).matches()) {
                char p = hex.charAt(6);
                String pos = String.valueOf(hex.charAt(5));

                int position = Integer.parseInt(pos);
                if (password[position] == null) {
                    password[position] = p;
                    System.out.println(position + ": " + p);
                    found++;
                }

            }
            index++;
        }
        for (char entry : password) {
            System.out.print(entry);
        }
    }

}
