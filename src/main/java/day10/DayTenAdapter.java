package day10;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class DayTenAdapter {

    private static final int[] tribonacci = {1, 1, 2, 4, 7, 13, 24, 44, 81, 149};

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day10/input.txt"), StandardCharsets.UTF_8);
        int[] sortedJolts = lines.stream().mapToInt(Integer::parseInt).sorted().toArray();
        int productOfJoltDiffs = getProductOfJoltDiffs(sortedJolts);
        System.out.println("Part 1: " + productOfJoltDiffs);
        long totalNumberOfDistinctArrangements = getTotalNumberOfDistinctArrangements(sortedJolts);
        System.out.println("Part 2: " + totalNumberOfDistinctArrangements);
    }

    private static long getTotalNumberOfDistinctArrangements(int[] sortedJolts) {
        int[] extendedSortedJolts = Arrays.copyOf(sortedJolts, sortedJolts.length + 2);
        extendedSortedJolts[extendedSortedJolts.length - 2] = 0;
        extendedSortedJolts[extendedSortedJolts.length - 1] = sortedJolts[sortedJolts.length - 1];
        Arrays.sort(extendedSortedJolts);

        long multiplier = 1;
        int currentRun = 1;
        for (int joltage : extendedSortedJolts) {
            if (arrayContains(sortedJolts, joltage + 1)) {
                currentRun++;
            } else {
                multiplier *= tribonacci[currentRun - 1];
                currentRun = 1;
            }
        }
        return multiplier;
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    private static int getProductOfJoltDiffs(int[] sortedJolts) {
        int amountOneDiff = 0;
        int amountThreeDiff = 1; // Built-in adapter
        if (sortedJolts[0] == 1) {
            amountOneDiff++;
        } else if (sortedJolts[0] == 3) {
            amountThreeDiff++;
        }
        for (int i = 1; i < sortedJolts.length; i++) {
            if (Math.abs(sortedJolts[i - 1] - sortedJolts[i]) == 1) {
                amountOneDiff++;
            } else if (Math.abs(sortedJolts[i - 1] - sortedJolts[i]) == 3) {
                amountThreeDiff++;
            }
        }
        return amountOneDiff * amountThreeDiff;
    }
}
