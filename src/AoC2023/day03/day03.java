package src.AoC2023.day03;

import src.TextParser;

import java.io.IOException;
import java.util.*;

public class day03 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        try {
            input = tx.readFromFile("src/AoC2023/day03/day03");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        String remove = input.replaceAll("[\n\r]", "");
        String[] strArray = remove.split("");
        //Input data is 140x140
        String[][] matrix = toMatrix(strArray, 10, 10);

        List<Integer> list = solveMatrix(matrix);
        int sum = 0;
        for(Integer l : list) {
            sum += l;
        }
        System.out.println(sum);
        List<Integer> listParttwo = solveMatrixForGear(matrix);
        int sumTwo = 0;
        for(Integer l : listParttwo) {
            System.out.println(l);
            sumTwo += l;
        }
        System.out.println(sumTwo);
    }
    public static List<Integer> solveMatrixForGear(String[][] matrix) {
        List<Integer> numbers = new ArrayList<>();
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c].equals("*")) {
                    Queue<Tuple> positions = gears(matrix, r, c);
                    System.out.println(positions.size());
                    if(positions.size() == 2) {
                        while(!positions.isEmpty()) {
                            Tuple<Integer, Integer> fromQueue = positions.poll();
                            int row = fromQueue.getFirst();
                            int col = fromQueue.getSecond();
                            numbers.add(calculateGears(matrix, row, col));
                        }
                    }
                    else
                    {
                        positions.clear();
                    }
                }
            }
        }
        return numbers;
    }
    //Doesn't work for part 2
    public static int calculateGears(String[][] matrix, int r, int c) {
        Queue<Integer> queue = new LinkedList<>();
        int number = 0;
        for(int j = c; j < matrix[r].length; j++) {
            if (isDigit(matrix, r, j)) {
                queue.add(Integer.parseInt(matrix[r][j]));
            } else {
                break;
            }
        }
        while(!queue.isEmpty()) {
            number = number * 10 + queue.poll();
        }
        return number;
    }
    public static Queue<Tuple> gears(String[][] matrix, int r, int c) {
        Queue<Tuple> queue = new LinkedList<>();

        // Visited set to track if the position has been added to the queue
        Set<Tuple> visited = new HashSet<>();

        // Check UP (Directly above)
        if (r - 1 >= 0 && isDigit(matrix, r - 1, c) && !visited.contains(new Tuple(r - 1, c))) {
            queue.add(new Tuple(r - 1, c));
            visited.add(new Tuple(r - 1, c));
        }

        // Check DOWN (Directly below)
        if (r + 1 < matrix.length && isDigit(matrix, r + 1, c) && !visited.contains(new Tuple(r + 1, c))) {
            queue.add(new Tuple(r + 1, c));
            visited.add(new Tuple(r + 1, c));
        }

        // Check LEFT (Directly left)
        if (c - 1 >= 0 && isDigit(matrix, r, c - 1) && !visited.contains(new Tuple(r, c - 1))) {
            queue.add(new Tuple(r, c - 1));
            visited.add(new Tuple(r, c - 1));
        }

        // Check RIGHT (Directly right)
        if (c + 1 < matrix[r].length && isDigit(matrix, r, c + 1) && !visited.contains(new Tuple(r, c + 1))) {
            queue.add(new Tuple(r, c + 1));
            visited.add(new Tuple(r, c + 1));
        }

        // Check if a number sequence exists next to the * and add the last digit (e.g., 467 -> add 7)
        if (c - 1 >= 0 && r - 1 >= 0 && isDigit(matrix, r - 1, c - 1)) {
            // Check for sequence of digits and add the last one
            if (matrix[r - 1][c - 1].matches("\\d")) {
                String sequence = "";
                // Check if adjacent cells form a number sequence (left to right or up-down)
                int i = c - 1;
                while (i >= 0 && matrix[r - 1][i].matches("\\d")) {
                    sequence += matrix[r - 1][i];
                    i--;
                }
                // If the sequence is valid, add the last digit
                if (!sequence.isEmpty()) {
                    queue.add(new Tuple(r - 1, c - 1));  // add the last digit
                }
            }
        }

        return queue;
    }


    public static List<Integer> solveMatrix(String[][] matrix) {
        List<Integer> list = new ArrayList<>();
        Queue<Tuple> queue = new LinkedList<>();
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                //If we find a digit
                if(isDigit(matrix, r, c)) {
                    queue.add(new Tuple<>(r, c));
                }
                else {
                    Queue<Tuple> temp = new LinkedList<>(queue);
                    if(isQueueValid(matrix, temp)) {
                        list.add(calculateNumberInQueue(matrix,queue));
                    }
                    else {
                        queue.clear();
                    }
                }
            }
        }
        return list;
    }
    public static boolean isQueueValid(String[][] matrix, Queue<Tuple> queue) {
        boolean isValid = false;
        while(!queue.isEmpty()) {
            Tuple<Integer, Integer> fromQueue = queue.poll();
            if(isGridValid(matrix, fromQueue.getFirst(), fromQueue.getSecond())) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    public static int calculateNumberInQueue(String[][] matrix, Queue<Tuple> queue) {
        int number = 0;
        while(!queue.isEmpty()) {
            Tuple<Integer, Integer> fromQueue = queue.poll();
            number = number * 10 + Integer.parseInt(matrix[fromQueue.getFirst()][fromQueue.getSecond()]);
        }
        return number;
    }
    public static boolean isDigit(String[][] matrix, int r, int c) {
        if(r >= 0 && r < matrix.length && c >= 0 && c < matrix[r].length) {
            return matrix[r][c].matches("\\d");
        }
        return false;
    }
    public static boolean isGridValid(String[][] matrix, int r, int c) {
        if(matrix[r][c].equals(".")) {
            return false;
        }
        // Check RIGHT
        if (c + 1 < matrix[r].length && isValidCell(matrix[r][c + 1])) {
            return true;
        }
        // Check LEFT
        if (c - 1 >= 0 && isValidCell(matrix[r][c - 1])) {
            return true;
        }
        // Check DOWN
        if (r + 1 < matrix.length && isValidCell(matrix[r + 1][c])) {
            return true;
        }
        // Check UP
        if (r - 1 >= 0 && isValidCell(matrix[r - 1][c])) {
            return true;
        }
        // Check RIGHT & DOWN
        if (c + 1 < matrix[r].length && r + 1 < matrix.length && isValidCell(matrix[r + 1][c + 1])) {
            return true;
        }
        // Check LEFT & DOWN
        if (c - 1 >= 0 && r + 1 < matrix.length && isValidCell(matrix[r + 1][c - 1])) {
            return true;
        }
        // Check LEFT & UP
        if (c - 1 >= 0 && r - 1 >= 0 && isValidCell(matrix[r - 1][c - 1])) {
            return true;
        }
        // Check RIGHT & UP
        if (c + 1 < matrix[r].length && r - 1 >= 0 && isValidCell(matrix[r - 1][c + 1])) {
            return true;
        }
        return false;

    }
    public static boolean isValidCell(String value) {
        return !value.equals(".") && !value.matches("\\d");
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