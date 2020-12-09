package day9;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.LongStream;

public class DayNineEncoding {

    public static void main(String[] args) throws IOException {
        List<Long> lines = getLines();
        long firstNotMatchingRules = getFirstNotMatchingRules(lines);
        System.out.println("Part 1: " + firstNotMatchingRules);
        long encryptionWeakness = getEncryptionWeakness(lines, firstNotMatchingRules);
        System.out.println("Part 2: " + encryptionWeakness);
    }

    private static long getEncryptionWeakness(List<Long> lines, long invalidNumber) {
        for (int i = 0; i < lines.size() - 2; i++) {
            for (int j = i + 1; j < lines.size() - 1; j++) {
                LongStream stream = lines.subList(i, j + 1).stream().mapToLong(l -> l);
                long sum = stream.sum();
                stream = lines.subList(i, j + 1).stream().mapToLong(l -> l);
                if (sum == invalidNumber) {
                    long[] longs = stream.sorted().toArray();
                    return longs[0] + longs[longs.length - 1];
                }
                if (sum > invalidNumber) {
                    break;
                }
            }
        }
        throw new NoSuchElementException();
    }

    private static long getFirstNotMatchingRules(List<Long> lines) {
        for (int i = 25; i < lines.size(); i++) {
            long toAddUpTo = lines.get(i);
            if (!isPossibleSum(toAddUpTo, lines.subList(i - 25, i))) {
                return toAddUpTo;
            }
        }
        throw new NoSuchElementException();
    }

    private static boolean isPossibleSum(long toAddUpTo, List<Long> subList) {
        for (long i : subList) {
            for (long j : subList) {
                if (i != j && i + j == toAddUpTo) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<Long> getLines() throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day9/input.txt"), StandardCharsets.UTF_8);
        List<Long> result = new LinkedList<>();
        for (String line : lines) {
            result.add(Long.parseLong(line));
        }
        return result;
    }
}
