package com.markwryan.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 12/3/16.
 */
public class SquaresWithThreeSides {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day3/day3.txt";
        List<String> input = Files.readAllLines(Paths.get(fileName));
        List<Triangle> possibleTriangles = new ArrayList<>();

        for(String triangleInput : input) {
            Triangle triangle = Triangle.fromInput(triangleInput);
            if(triangle.isValid()) {
                possibleTriangles.add(triangle);
            }
        }

        System.out.println(possibleTriangles.size() + " possible triangles.");

    }


    private static class Triangle {
        int a, b, c;

        public Triangle(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean isValid() {
            if(a + b > c) {
                if(b + c > a) {
                    if(a + c > b) {
                        return true;
                    }
                }
            }
            return false;
        }

        public static Triangle fromInput(String input) {
            input = input.trim();
            String[] entries = input.split("\\W+");
            return new Triangle(Integer.parseInt(entries[0]),
                    Integer.parseInt(entries[1]),Integer.parseInt(entries[2]));
        }
    }
}
