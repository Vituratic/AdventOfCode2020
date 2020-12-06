package day6;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaySixCustoms {

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day6/input.txt"), StandardCharsets.UTF_8);
        int positivelyAnsweredQuestions = getSumOfPositivelyAnsweredQuestions(lines);
        System.out.println("Part 1: " + positivelyAnsweredQuestions);
        int completelyAgreedQuestions = getSumOfAgreedQuestions(lines);
        System.out.println("Part 2: " + completelyAgreedQuestions);
    }

    private static int getSumOfPositivelyAnsweredQuestions(List<String> lines) {
        String group = "";
        int positivelyAnsweredQuestions = 0;
        for (String line : lines) {
            group += line;
            group += " ";
            if (line.isEmpty()) {
                positivelyAnsweredQuestions += getAmountOfPositivelyAnsweredQuestions(group);
                group = "";
            }
        }
        positivelyAnsweredQuestions += getAmountOfPositivelyAnsweredQuestions(group);
        return positivelyAnsweredQuestions;
    }

    private static int getAmountOfPositivelyAnsweredQuestions(String group) {
        Set<Character> positivelyAnsweredQuestions = new HashSet<>();
        for (char c : group.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                positivelyAnsweredQuestions.add(c);
            }
        }
        return positivelyAnsweredQuestions.size();
    }

    private static int getSumOfAgreedQuestions(List<String> lines) {
        String group = "";
        int agreedQuestions = 0;
        for (String line : lines) {
            group += line;
            group += " ";
            if (line.isEmpty()) {
                agreedQuestions += getAmountOfAgreedQuestions(group);
                group = "";
            }
        }
        agreedQuestions += getAmountOfAgreedQuestions(group);
        return agreedQuestions;
    }

    private static int getAmountOfAgreedQuestions(String group) {
        Set<Character> agreedQuestions = new HashSet<>();
        String[] eachPersonsAnswers = group.split(" ");
        for (char c : group.toCharArray()) {
            if (Character.isAlphabetic(c) && didEveryoneAnswer(c, eachPersonsAnswers)) {
                agreedQuestions.add(c);
            }
        }
        return agreedQuestions.size();
    }

    private static boolean didEveryoneAnswer(char c, String[] eachPersonsAnswers) {
        for (String aPersonsAnswers : eachPersonsAnswers) {
            if (!aPersonsAnswers.contains("" + c)) {
                return false;
            }
        }
        return true;
    }
}
