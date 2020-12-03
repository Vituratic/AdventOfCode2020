package day3;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DayThreeTrajectory {

  public static void main(String[] args) {
    //    partOne();
    partTwo();
  }

  private static void partOne() {
    try {
      List<String> lines =
          FileUtils.readLines(
              new File("src/main/resources/day3/input.txt"), StandardCharsets.UTF_8);
      int amountOfTrees = 0;
      int position = 1;
      for (String line : lines) {
        if (position > line.length()) {
          position = position % line.length();
        }
        if (line.charAt(position - 1) == '#') {
          amountOfTrees++;
        }
        position += 1;
      }
      System.out.println("amount of trees:" + amountOfTrees);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void partTwo() {
    try {
      List<String> lines =
          FileUtils.readLines(
              new File("src/main/resources/day3/input.txt"), StandardCharsets.UTF_8);
      BigInteger amountOfTrees = new BigInteger(String.valueOf(getAmountOfTrees(lines, 1)));
      System.out.println("amount for 1:" + amountOfTrees);
      for (int i = 3; i <= 7; i += 2) {
          int tmp = getAmountOfTrees(lines, i);
        System.out.println("amount for " + i + ":" + tmp);
        amountOfTrees = amountOfTrees.multiply(new BigInteger(String.valueOf(tmp)));
      }
      int tmp = getAmountOfTreesSkip(lines);
      System.out.println("amount for skip: " + tmp);
      amountOfTrees = amountOfTrees.multiply(new BigInteger(String.valueOf(tmp)));
      System.out.println("multiplied:" + amountOfTrees);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static int getAmountOfTrees(List<String> lines, int toAddToPosition) {
    int position = 1;
    int amountOfTrees = 0;
    for (String line : lines) {
      if (position > line.length()) {
        position %= line.length();
      }
      if (line.charAt(position - 1) == '#') {
        amountOfTrees++;
      }
      position += toAddToPosition;
    }
    return amountOfTrees;
  }

  private static int getAmountOfTreesSkip(List<String> lines) {
      int position = 1;
      int amountOfTrees = 0;
      boolean shouldSkip = false;
      for (String line : lines) {
          if (shouldSkip) {
              shouldSkip = false;
              continue;
          }
          if (position > line.length()) {
              position %= line.length();
          }
          if (line.charAt(position - 1) == '#') {
              amountOfTrees++;
          }
          position++;
          shouldSkip = true;
      }
      return amountOfTrees;
  }
}
