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

        for (String triangleInput : input) {
            Triangle triangle = Triangle.fromInput(triangleInput);
            if (triangle.isValid()) {
                possibleTriangles.add(triangle);
            }
        }

        System.out.println(possibleTriangles.size() + " possible triangles.");

        //Part 2
        List<Triangle> verticallySpecifiedTriangles = new ArrayList<>();
        for (int i = 0; i < input.size() - 2; i = i + 3) {
            String line1 = input.get(i);
            String line2 = input.get(i + 1);
            String line3 = input.get(i + 2);

            List<Triangle> inputTriangles = Triangle.fromInputs(line1, line2, line3);

            for(Triangle triangle : inputTriangles) {
                if(triangle.isValid()) {
                    verticallySpecifiedTriangles.add(triangle);
                }
            }

        }
        System.out.println(verticallySpecifiedTriangles.size() + " possible triangles specified vertically.");

    }


    private static class Triangle {
        int a, b, c;

        public Triangle(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean isValid() {
            if (a + b > c) {
                if (b + c > a) {
                    if (a + c > b) {
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
                    Integer.parseInt(entries[1]), Integer.parseInt(entries[2]));
        }

        /*
        Entries listed as
            T1  T2  T3
            T1  T2  T3
            T1  T2  T3
         */
        public static List<Triangle> fromInputs(String input1, String input2, String input3) {
            List<Triangle> triangles = new ArrayList<>();
            String[] entries1 = input1.trim().split("\\W+");
            String[] entries2 = input2.trim().split("\\W+");
            String[] entries3 = input3.trim().split("\\W+");

            for(int i = 0; i < 3; i++) {
                triangles.add(new Triangle(Integer.parseInt(entries1[i]),
                        Integer.parseInt(entries2[i]), Integer.parseInt(entries3[i])));
            }
            return triangles;
        }
    }
}
