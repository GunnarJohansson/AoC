package src.day06;

import src.TextParser;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Part 1
//Plan of attack!
//Step 1: Create a matrix
//Step 2: Map locations of "#"
//Step 3: Find starting position: ^
//Step 4: Move location until at obstacle "#"
//Step 5: Mark visited locations in a set
//Step 6: Return how many in set.

//Part 2 - Who needs logic when we can time stuff!
//Try all locations in the matrix and return position if time is too long
public class day06 {
    static String direction = "^";
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;

        try {
            input = tx.readFromFile("src/day06/day06");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Remove unwanted input, and create Matrix
        String remove = input.replaceAll("[\n\r]", "");
        String[][] matrix = toMatrix(remove.split(""), 10, 10);
        HashMap<Point, String> hm = mapMatrix(matrix);
        hm.put(new Point(1,8), "#");
        Point start = findStart(matrix);
        Set<Point> visited = move(hm, start);
        System.out.println(visited.size());
        /*int sum = 0;
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[x].length; y++) {
                if(start.equals(new Point(x,y-1))) {
                    break;
                }
                Point point = new Point(x, y);
                //Add obstacle to map
                //See if there is a cycle
                //remove it
                hm.put(point, "#");
                sum += move2(hm, start);
                hm.remove(point);
            }
        }
        System.out.println(sum);*/
    }
    public static Set<Point> move(HashMap<Point, String> hm, Point pos) {
        Set<Point> visited = new HashSet<>();
        List<Point> path = new ArrayList<Point>();
        boolean canMove = true;
        while(canMove) {
            visited.add(new Point(pos));
            path.add(new Point(pos));

            switch (direction) {
                case "^": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x - 1, pos.y);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = ">";
                    }
                    break;
                }
                case ">": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x, pos.y + 1);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "v";
                    }
                    break;
                }
                case "v": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x+1, pos.y);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "<";
                    }
                    break;
                }
                case "<": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x, pos.y-1);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "^";
                    }
                    break;
                }
            }
            if(isCycle(path)) {
                System.out.println("We found a cycle");
                break;
            }
            canMove = isWithInBounds(pos);
        }
        return visited;
    }
    public static int move2(HashMap<Point, String> hm, Point pos) {
        List<Point> path = new ArrayList<Point>();
        int r = 0;
        boolean canMove = true;
        while(canMove) {
            path.add(new Point(pos));

            switch (direction) {
                case "^": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x - 1, pos.y);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = ">";
                    }
                    break;
                }
                case ">": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x, pos.y + 1);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "v";
                    }
                    break;
                }
                case "v": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x+1, pos.y);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "<";
                    }
                    break;
                }
                case "<": {
                    System.out.println("x: " + pos.x + " y: " + pos.y);
                    Point next = new Point(pos.x, pos.y-1);
                    if (!isPointMapped(hm, next)) {
                        pos.setLocation(next);
                    } else {
                        direction = "^";
                    }
                    break;
                }
            }
            if(isCycle(path)) {
                System.out.println("We found a cycle");
                r = 1;
                break;
            }
            canMove = isWithInBounds(pos);
        }
        return r;
    }
    public static boolean isCycle(List<Point> path) {
        int sequenceLength = 2;
        if (path.size() < sequenceLength * 2) {
            return false;
        }
        for (int i = 0; i <= path.size() - sequenceLength * 2; i++) {
            boolean match = true;
            for (int j = 0; j < sequenceLength; j++) {
                if (!path.get(i + j).equals(path.get(path.size() - sequenceLength + j))) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }
    public static boolean isWithInBounds(Point pos) {
        int x = pos.x;
        int y = pos.y;
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
    public static boolean isPointMapped(HashMap<Point, String> hm, Point pos) {
        if(hm.get(pos) == null) {
            return false;
        }
        return hm.get(pos).equals("#");
    }
    public static Point findStart(String[][] matrix) {
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[x].length; y++) {
                if(matrix[x][y].equals("^")) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }
    public static HashMap<Point, String> mapMatrix(String[][] matrix) {
        HashMap<Point, String> hm = new HashMap<Point, String>();
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[x].length; y++) {
                if(matrix[x][y].equals("#")) {
                    hm.put(new Point(x, y), "#");
                }
            }
        }
        return hm;
    }
    public static String[][] toMatrix(String[] array, int col, int row){
        String[][] newMatrix = new String[col][row];
        for (int r = 0; r < newMatrix.length; r++){
            for (int c = 0; c < newMatrix[r].length; c++){
                int index = r * newMatrix.length + c;
                newMatrix[r][c] = array[index];
            }
        }
        return newMatrix;
    }
}