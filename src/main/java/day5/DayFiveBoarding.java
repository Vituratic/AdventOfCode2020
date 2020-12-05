package day5;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DayFiveBoarding {

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day5/input.txt"), StandardCharsets.UTF_8);
        List<Integer> seatIds = getSeatIds(lines);
        int maxSeatId = seatIds.stream().max(Integer::compareTo).orElse(-1);
        int ownSeatId = seatIds.stream().sorted()
                // there has to be jump of two between the ids, but the actual successor mustn't be contained
                // since the missing successor is the own seatid
                .filter(seatId -> seatIds.contains(seatId + 2) && !seatIds.contains(seatId + 1))
                .findFirst().orElse(-1) + 1;
        System.out.println("Part 1: " + maxSeatId);
        System.out.println("Part 2: " + ownSeatId);
    }

    private static List<Integer> getSeatIds(List<String> lines) {
        List<Integer> seatIds = new LinkedList<>();
        for (String boardingPass : lines) {
            int row = getRemainingNumber(boardingPass.substring(0, 7).toCharArray(), 128);
            int column = getRemainingNumber(boardingPass.substring(7).toCharArray(), 8);
            seatIds.add((row * 8) + column);
        }
        return seatIds;
    }

    private static int getRemainingNumber(char[] charsDecidingLowerOrUpperHalf, int toExclusive) {
        int[] numbers = IntStream.range(0, toExclusive).toArray();
        for (char c : charsDecidingLowerOrUpperHalf) {
            int length = numbers.length;
            if (c == 'L' || c == 'F') {
                numbers = IntStream.range(numbers[0], numbers[length / 2]).toArray();
            } else {
                numbers = IntStream.range(numbers[length / 2], numbers[numbers.length - 1] + 1).toArray();
            }
        }
        return numbers[0];
    }
}
