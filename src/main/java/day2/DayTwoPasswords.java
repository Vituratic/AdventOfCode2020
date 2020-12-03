package day2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DayTwoPasswords {

  public static void main(String[] args) {
    //    partOne();
    partTwo();
  }

  private static void partOne() {
    try {
      List<String> lines =
          FileUtils.readLines(
              new File("src/main/resources/day2/input.txt"), StandardCharsets.UTF_8);
      int amountCorrectPasswords = 0;
      for (String line : lines) {
        String[] split = line.split(":");
        String password = split[1];
        String[] policy = split[0].split(" ");
        String[] occurrences = policy[0].split("-");
        int minOccurrences = Integer.parseInt(occurrences[0]);
        int maxOccurrences = Integer.parseInt(occurrences[1]);
        char character = policy[1].trim().charAt(0);
        int actualOccurrences = 0;
        for (char c : password.toCharArray()) {
          if (c == character) {
            actualOccurrences++;
          }
        }
        if (minOccurrences <= actualOccurrences && actualOccurrences <= maxOccurrences) {
          amountCorrectPasswords++;
        }
      }
      System.out.println("Amount correct passwords: " + amountCorrectPasswords);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void partTwo() {
    try {
      List<String> lines =
          FileUtils.readLines(
              new File("src/main/resources/day2/input.txt"), StandardCharsets.UTF_8);
      int amountCorrectPasswords = 0;
      for (String line : lines) {
        String[] split = line.split(":");
        String password = split[1].trim();
        String[] policy = split[0].split(" ");
        String[] occurrences = policy[0].split("-");
        int relevantIndexOne = Integer.parseInt(occurrences[0]) - 1;
        int relevantIndexTwo = Integer.parseInt(occurrences[1]) - 1;
        char character = policy[1].trim().charAt(0);
        int amountOccurrences = 0;
        if (character == password.charAt(relevantIndexOne)) {
          amountOccurrences++;
        }
        if (character == password.charAt(relevantIndexTwo)) {
          amountOccurrences++;
        }
        if (amountOccurrences == 1) {
          amountCorrectPasswords++;
          System.out.println(line);
        }
      }
      System.out.println("Amount correct passwords: " + amountCorrectPasswords);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
