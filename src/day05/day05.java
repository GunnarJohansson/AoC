package src.day05;

import src.TextParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class day05 {
    public static void main(String[] args) {
        TextParser tx = new TextParser();
        String input;
        List<String> rules = new ArrayList<>();
        List<String> updates = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        List<Integer> numbersPart2 = new ArrayList<>();
        try {
            input = tx.readFromFile("src/day05/day05");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] rulesAndUpdates = input.split("\n");

        for (String rulesAndUpdate : rulesAndUpdates) {
            if (rulesAndUpdate.length() == 6) {
                rules.add(rulesAndUpdate);
            }
            if (rulesAndUpdate.length() > 6) {
                updates.add(rulesAndUpdate);
            }
        }
        for(String u : updates) {
            //Part 1:
            if(checkRules(rules,u)) {
                numbers.add(middleOfArray(u));
            }
            //Part 2:
            else {
                System.out.println(Arrays.toString(correctOrder(rules,u)));
                numbersPart2.add(middleOfArray(correctOrder(rules, u)));
            }

        }
        int sum = 0;
        int sum2 = 0;
        for(Integer n : numbers) {
            sum += n;
        }
        for(Integer n : numbersPart2) {
            sum2 += n;
        }
        System.out.println(sum);
        System.out.println(sum2);
    }
    public static int middleOfArray(String update) {
        String remove = update.replaceAll("[^0-9,]", "");
        String[] updates = remove.split(",");
        int n = updates.length;
        int mid = n / 2;
        return Integer.parseInt(updates[mid]);
    }
    public static int middleOfArray(String[] updates) {
        int n = updates.length;
        int mid = n / 2;
        return Integer.parseInt(updates[mid]);
    }
    public static boolean checkRules(List<String> rules, String update) {
        String remove = update.replaceAll("[^0-9,]", "");
        String[] updates = remove.split(",");
        List<Tuple> intRules = listOfRules(rules);
        boolean valid = true;
        //For each rule, check that it is valid
        for(int i = 0; i < intRules.size(); i++) {
            //Get values of the rules
            int firstRule = (int) intRules.get(i).getFirst();
            int secondRule = (int) intRules.get(i).getSecond();
            for(int j = 0; j < updates.length; j++) {
                int firstValue = Integer.parseInt(updates[j]);
                //Check if the first Rule is in the update instructions
                if(firstRule == firstValue) {
                    //Check <--- if we find secondRule
                    for(int k = j; k > 0; k--) {
                        int secondValue = Integer.parseInt(updates[k]);
                        if(secondRule == secondValue) {
                            valid = false;
                            break;
                        }
                    }
                }
                if(secondRule == firstValue) {
                    //Check ----->
                    for(int l = j; l < updates.length; l++) {
                        int secondValue = Integer.parseInt(updates[l]);
                        if(firstRule == secondValue) {
                            valid = false;
                            break;
                        }
                    }
                }
            }
        }
        return valid;
    }
    public static String[] correctOrder(List<String> rules, String update) {
        boolean correctionDone;
        String remove = update.replaceAll("[^0-9,]", "");
        String[] updates = remove.split(",");
        do {
            correctionDone = false;
            List<Tuple> intRules = listOfRules(rules);
            boolean valid = true;
            //For each rule, check that it is valid
            for (int i = 0; i < intRules.size(); i++) {
                //Get values of the rules
                int firstRule = (int) intRules.get(i).getFirst();
                int secondRule = (int) intRules.get(i).getSecond();
                for (int j = 0; j < updates.length; j++) {
                    int firstValue = Integer.parseInt(updates[j]);
                    //Check if the first Rule is in the update instructions
                    if (firstRule == firstValue) {
                        //Check <--- if we find secondRule
                        for (int k = j; k > 0; k--) {
                            int secondValue = Integer.parseInt(updates[k]);
                            if (secondRule == secondValue) {
                                String temp = updates[j];
                                updates[j] = updates[k];
                                updates[k] = temp;
                                correctionDone = true;
                            }
                        }
                    }
                    if (secondRule == firstValue) {
                        //Check ----->
                        for (int l = j; l < updates.length; l++) {
                            int secondValue = Integer.parseInt(updates[l]);
                            if (firstRule == secondValue) {
                                String temp = updates[j];
                                updates[j] = updates[l];
                                updates[l] = temp;
                                correctionDone = true;
                            }
                        }
                    }
                }
            }
        } while (correctionDone);
        return updates;
    }
    public static List<Tuple> listOfRules(List<String> rules) {
        List<Tuple> tupleRules = new ArrayList<>();
        for(String r : rules) {
            String remove = r.replaceAll("[^0-9|]", "");
            String [] temp = remove.split("\\|");
            int first = Integer.parseInt(temp[0]);
            int second = Integer.parseInt(temp[1]);
            tupleRules.add(new Tuple<Integer,Integer>(first, second));
        }
        return tupleRules;
    }
}