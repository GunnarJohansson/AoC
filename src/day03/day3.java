package src.day03;

import src.TextParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class day3 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        List<Integer> numbers = new ArrayList<>();

        try {
            input = tx.readFromFile("src/day03/day3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int i = input.indexOf("mul(");
        while(i != -1) {
            String search = input.substring(i);
            //System.out.println(search);
            if(validateString(search)) {
                numbers.add(calculateNumbersInString(search));
            }
            i = input.indexOf("mul(", i+4);
        }
        long result = 0;
        for(int num : numbers) {
            result += num;
            System.out.println(num);
            //System.out.println(result);
        }
        System.out.println(result);
    }
    public static boolean validateString(String search) {
        boolean valid = false;
        for(int i = 4; i < search.length(); i++) {
            while (Character.isDigit(search.charAt(i))) {
                i++;
            }
            if (String.valueOf(search.charAt(i)).equals(",")) {
                i++;
                while (Character.isDigit(search.charAt(i))) {
                    i++;
                }
                if (String.valueOf(search.charAt(i)).equals(")")) {
                    valid = true;
                    i = search.length();
                } else {
                    valid = false;
                    i = search.length();
                }
            }
        }
        return valid;
    }
    public static int calculateNumbersInString(String search) {
        Queue<Integer> queue = new LinkedList<>();
        int number = 0;
        for(int i = 4; i < search.length(); i++) {
            while(Character.isDigit(search.charAt(i))) {
                int n = Integer.parseInt(String.valueOf(search.charAt(i)));
                queue.add(n);
                i++;
            }
            if(String.valueOf(search.charAt(i)).equals(",")) {
                number = calculateNumberInQueue(queue);
            }
            if(String.valueOf(search.charAt(i)).equals(")")) {
                int calc = calculateNumberInQueue(queue);
                /*System.out.println(number);
                System.out.println(calc);*/
                number = number * calc;
            }
            if(String.valueOf(search.charAt(i)).equals(")")) {
                i = search.length();
            }
        }
        System.out.println(number);
        return number;
    }
    public static int calculateNumberInQueue(Queue<Integer> queue) {
        int number = 0;
        while(!queue.isEmpty()) {
            number = number * 10 + queue.poll();
        }
        return number;
    }
}
