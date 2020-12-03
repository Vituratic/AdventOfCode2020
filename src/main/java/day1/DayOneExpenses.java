package day1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DayOneExpenses {

  public static void main(String[] args) {
    partTwo();
  }

  private static void partOne() {
    try {
      List<Integer> numbers = FileUtils.readLines(new File("src/main/resources/day1/expenseReport.txt"), StandardCharsets.UTF_8)
              .stream().map(Integer::parseInt).collect(Collectors.toList());
      outer:for (int i = 0; i < numbers.size(); i++) {
        int a = numbers.get(i);
        for (int j = 1; j < numbers.size(); j++) {
          int b = numbers.get(j);
          if (a + b == 2020) {
            System.out.println("a:" + a + " b:" + b + " a*b:" + a * b);
            break outer;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void partTwo() {
    try {
      List<Integer> numbers = FileUtils.readLines(new File("src/main/resources/day1/expenseReport.txt"), StandardCharsets.UTF_8)
              .stream().map(Integer::parseInt).collect(Collectors.toList());
      outer:for (int i = 0; i < numbers.size(); i++) {
        int a = numbers.get(i);
        for (int j = 1; j < numbers.size(); j++) {
          int b = numbers.get(j);
          for (int k = 2; k < numbers.size(); k++) {
            int c = numbers.get(k);
            if (a + b + c == 2020) {
              System.out.println("a:" + a + " b:" + b + " c:" + c + " a*b*c:" + a * b * c);
              break outer;
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
