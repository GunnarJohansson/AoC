package src.day01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.TextParser;
public class day1 {
    public static void main(String[] args) throws IOException {
        TextParser tx = new TextParser();
        String content = tx.readFromFile("src/day01/input");
        List<Integer> leftList = new ArrayList<Integer>();
        List<Integer> rightList = new ArrayList<Integer>();

        String[] strArray = content.split("\\s+");

        //Add every even number to leftList
        //Add every odd number to rightList
        for(int i = 0; i < strArray.length; i = i+2) {
            int leftNum = Integer.parseInt(strArray[i]);
            int rightNum = Integer.parseInt(strArray[i+1]);

            leftList.add(leftNum);
            rightList.add(rightNum);
        }
        //Sort the lists
        Collections.sort(leftList);
        Collections.sort(rightList);
        //Print the result
        System.out.println(calculateDistance(leftList, rightList));

        //PART TWO!!!!!
        int simScore = 0;
        for(Integer l : leftList) {
            int n = timesInList(l, rightList);
            simScore = simScore + l * n;
        }
        System.out.println(simScore);
    }
    //Find the times an int appear in a list
    //Return is the number of times.
    public static int timesInList(int search, List<Integer> list) {
        int n = 0;
        for(Integer l : list) {
            if(search == l) {
                n++;
            }
        }
        return n;
    }
    //Calculate the difference between sorted lists. The difference is decided by taking the max number minus the min number.
    //The result is return
    public static int calculateDistance(List<Integer> leftList, List<Integer> rightList) {
        int distance = 0;
        for(int i = 0; i < leftList.size(); i++) {
            int leftNum = leftList.get(i);
            int rightNum = rightList.get(i);

            int max = Math.max(leftNum, rightNum);
            int min = Math.min(leftNum, rightNum);

            distance = distance + max - min;
        }
        return distance;
    }
}
