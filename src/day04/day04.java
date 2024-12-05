package src.day04;

import src.TextParser;
import java.io.IOException;
public class day04 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        try {
            input = tx.readFromFile("src/day04/day04");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Counted characters in each row and columns already. It is used to build the matrix.
        //Remove Characters in the input which is not valid
        String remove = input.replaceAll("[^A-Za-z]", "");
        String[] strArray = remove.split("");
        String[][] strMatrix = toMatrix(strArray, 140, 140);
        System.out.println("PART 1: " + findWord(strMatrix));
        System.out.println("PART 2: " +findMas(strMatrix));
    }
    public static int findWord(String[][] matrix) {
        int sum = 0;
        for(int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix[r].length; c++) {
                if(matrix[r][c] != null) {
                    //RIGHT
                    if (c + 3 < matrix[r].length &&
                            matrix[r][c].equals("X") &&
                            matrix[r][c + 1].equals("M") &&
                            matrix[r][c + 2].equals("A") &&
                            matrix[r][c + 3].equals("S")
                    ) {
                        sum++;
                    }
                    //LEFT
                    if (c - 3 >= 0 &&
                            matrix[r][c].equals("X") &&
                            matrix[r][c-1].equals("M") &&
                            matrix[r][c-2].equals("A") &&
                            matrix[r][c-3].equals("S")
                    ) {
                        sum++;
                    }
                    //DOWN
                    if (r + 3 < matrix.length &&
                            matrix[r][c].equals("X") &&
                            matrix[r + 1][c].equals("M") &&
                            matrix[r + 2][c].equals("A") &&
                            matrix[r + 3][c].equals("S")
                    ) {
                        sum++;
                    }
                    //UP
                    if (r - 3 >= 0 &&
                            matrix[r][c].equals("X") &&
                            matrix[r - 1][c].equals("M") &&
                            matrix[r - 2][c].equals("A") &&
                            matrix[r - 3][c].equals("S")
                    ) {
                        sum++;
                    }
                    // Diagonal RIGHT & DOWN
                    if (c + 3 < matrix[r].length && r + 3 < matrix.length &&
                            matrix[r][c].equals("X") &&
                            matrix[r + 1][c + 1].equals("M") &&
                            matrix[r + 2][c + 2].equals("A") &&
                            matrix[r + 3][c + 3].equals("S")) {
                        sum++;
                    }
                    // Diagonal LEFT & DOWN
                    if (c - 3 >= 0 && r + 3 < matrix.length &&
                            matrix[r][c].equals("X") &&
                            matrix[r + 1][c - 1].equals("M") &&
                            matrix[r + 2][c - 2].equals("A") &&
                            matrix[r + 3][c - 3].equals("S")) {
                        sum++;
                    }
                    //Diagonal left and up
                    if (c - 3 >= 0 && r - 3 >= 0 &&
                            matrix[r][c].equals("X") &&
                            matrix[r-1][c-1].equals("M") &&
                            matrix[r-2][c-2].equals("A") &&
                            matrix[r-3][c-3].equals("S")
                    ) {
                        sum++;
                    }
                    //Diagonal right and up
                    if (r - 3 >= 0 && c + 3 < matrix[r].length &&
                            matrix[r][c].equals("X") &&
                            matrix[r-1][c+1].equals("M") &&
                            matrix[r-2][c+2].equals("A") &&
                            matrix[r-3][c+3].equals("S")
                    ) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }
    public static int findMas(String[][] matrix) {
        int sum = 0;
        for(int r = 0; r < matrix.length; r++) {
            for(int c = 0; c < matrix[r].length; c++) {
                //M S
                // A
                //M S
                if(c + 2 < matrix[r].length && r + 2 < matrix.length
                && matrix[r][c].equals("M")
                && matrix[r][c+2].equals("S")
                && matrix[r+1][c+1].equals("A")
                && matrix[r+2][c].equals("M")
                && matrix[r+2][c+2].equals("S"))
                {
                    sum++;
                }
                //S S
                // A
                //M M
                if(c + 2 < matrix[r].length && r + 2 < matrix.length
                        && matrix[r][c].equals("S")
                        && matrix[r][c+2].equals("S")
                        && matrix[r+1][c+1].equals("A")
                        && matrix[r+2][c].equals("M")
                        && matrix[r+2][c+2].equals("M"))
                {
                    sum++;
                }
                //S M
                // A
                //S M
                if(c + 2 < matrix[r].length && r + 2 < matrix.length
                        && matrix[r][c].equals("S")
                        && matrix[r][c+2].equals("M")
                        && matrix[r+1][c+1].equals("A")
                        && matrix[r+2][c].equals("S")
                        && matrix[r+2][c+2].equals("M"))
                {
                    sum++;
                }
                //M M
                // A
                //S S
                if(c + 2 < matrix[r].length && r + 2 < matrix.length
                        && matrix[r][c].equals("M")
                        && matrix[r][c+2].equals("M")
                        && matrix[r+1][c+1].equals("A")
                        && matrix[r+2][c].equals("S")
                        && matrix[r+2][c+2].equals("S"))
                {
                    sum++;
                }
            }
        }
        return sum;
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