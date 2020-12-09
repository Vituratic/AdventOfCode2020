package day8;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayEightHalting {

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day8/input.txt"), StandardCharsets.UTF_8);
        int accValueFirstTimeVisitedTwice = getAccValueFirstTimeVisitedTwice(lines).getAcc();
        System.out.println("Part 1: " + accValueFirstTimeVisitedTwice);
        int accValueFixed = getAccValueFixed(lines);
        System.out.println("Part 2: " + accValueFixed);
    }

    private static int getAccValueFixed(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String command = lines.get(i).substring(0, 3);
            if (command.equalsIgnoreCase("nop") || command.equalsIgnoreCase("jmp")) {
                swapCommands(lines, i);
            }
            Result result = getAccValueFirstTimeVisitedTwice(lines);
            if (result.getState() == Result.State.FINISHED) {
                return result.getAcc();
            }
            swapCommands(lines, i);
        }
        throw new RuntimeException("No result found");
    }

    private static void swapCommands(List<String> lines, int index) {
        String line = lines.get(index);
        if (line.startsWith("jmp")) {
            lines.set(index, line.replace("jmp", "nop"));
        } else if (line.startsWith("nop")) {
            lines.set(index, line.replace("nop", "jmp"));
        }
    }

    private static Result getAccValueFirstTimeVisitedTwice(List<String> lines) {
        int acc = 0;
        Set<Integer> alreadyVisitedLines = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            if (alreadyVisitedLines.contains(i)) {
                return new Result(acc, Result.State.ABORTED);
            }
            alreadyVisitedLines.add(i);
            String line = lines.get(i);
            String[] split = line.split(" ");
            String command = split[0].trim();
            String argument = split[1].trim();
            char sign = argument.charAt(0);
            int val = Integer.parseInt(argument.substring(1));
            if (command.equalsIgnoreCase("acc")) {
                if (sign == '+') {
                    acc += val;
                } else {
                    acc -= val;
                }
            } else if (command.equalsIgnoreCase("jmp")) {
                if (sign == '+') {
                    i += val - 1; // to counter-balance the increment of the for-loop
                } else {
                    i -= val + 1; // to counter-balance the increment of the for-loop
                }
            }
        }
        return new Result(acc, Result.State.FINISHED);
    }
}
