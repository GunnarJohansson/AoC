package src.day03;

import src.TextParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class day3 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        List<Integer> numbers = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        try {
            input = tx.readFromFile("src/day03/day3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3}(,\\d{1,3})*\\)");
        Matcher matcher = pattern.matcher(input);
        Pattern fullPattern = Pattern.compile("mul\\(\\d{1,3}(,\\d{1,3})*\\)|don't\\(\\)|do\\(\\)");
        Matcher fullMatcher = fullPattern.matcher(input);
        while (fullMatcher.find()) {
            strings.add(fullMatcher.group(0));
        }
        boolean calculate = true;
        long resultFull = 0;
        while (!strings.isEmpty()) {
            String current = strings.removeFirst();
            if(current.equals("don't()")) {
                calculate = false;
            }
            else if (current.equals("do()")) {
                calculate = true;
            }
            else if (calculate) {
                resultFull += calculateNumbersInString(current);
            }
        }
        System.out.println("Part 2: "+ resultFull);
        while(matcher.find())
        {
            numbers.add(calculateNumbersInString(matcher.group(0)));
        }
        long result = 0;
        for(int num : numbers) {
            result += num;
        }
        System.out.println("Part 1: "+ result);
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
                number = number * calc;
            }
            if(String.valueOf(search.charAt(i)).equals(")")) {
                i = search.length();
            }
        }
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
