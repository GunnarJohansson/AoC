import java.io.IOException;
import java.util.Arrays;

public class day2 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        try {
            input = tx.readFromFile("day2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Convert to reports
        String[] reports = input.split(System.lineSeparator());
        int safe = 0;
        for (String report : reports) {
            //Convert reports to int array
            int[] levels = Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt).toArray();
            if (isIncreasing(levels)) {
                safe++;
            } else {
                for (int j = 0; j < levels.length; j++) {
                    if (isIncreasing(removeIndex(levels, j))) {
                        safe++;
                        break;
                    }
                }
            }
        }
        for (String report : reports) {
            //Convert reports to int array
            int[] levels = Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt).toArray();
            if (isDecreasing(levels)) {
                safe++;
            } else {
                for (int j = 0; j < levels.length; j++) {
                    if (isDecreasing(removeIndex(levels, j))) {
                        safe++;
                        break;
                    }
                }
            }
        }
        System.out.println(safe);
    }
    public static boolean isIncreasing(int[] levels) {
        for (int i = 0; i < levels.length - 1; i++) {
            int previousNum = levels[i];
            int nextNum = levels[i + 1];
            int difference = nextNum - previousNum;

            if (previousNum >= nextNum || difference < 1 || difference > 3) {
                return false;
            }
        }
        return true;
    }
    public static boolean isDecreasing(int[] levels) {
        for (int i = 0; i < levels.length - 1; i++) {
            int previousNum = levels[i];
            int nextNum = levels[i + 1];
            int difference = nextNum - previousNum;

            if (previousNum <= nextNum || difference > -1 || difference < -3) {
                return false;
            }
        }
        return true;
    }
    public static int[] removeIndex(int[] levels, int n) {
        int[] newLevels = new int[levels.length-1];
        for(int i = 0, j = 0; i < levels.length; i++) {
            if(i == n) {
                continue;
            }
            newLevels[j++] = levels[i];
        }
        return newLevels;
    }
}