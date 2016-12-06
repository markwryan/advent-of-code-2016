package com.markwryan.day5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        while(found < 8) {
            byte[] hash = digest.digest((doorId + index).getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte c : hash) {
                sb.append(String.format("%02X", c));
            }
            String hex = sb.toString();
            if(hex.startsWith("00000")) {
                char p = hex.substring(6,7).charAt(0);
                String pos = hex.substring(5,6);
                if(pos.matches("[0-7]")) {
                    int position = Integer.parseInt(pos);
                    if(password[position] == null) {
                        password[position] = p;
                        System.out.println(position + ": " + p);
                        found++;
                    }
                }
            }
            index++;
        }
        for(char entry : password) {
            System.out.print(entry);
        }
    }

}
