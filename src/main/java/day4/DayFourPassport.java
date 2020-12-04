package day4;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DayFourPassport {

    public static void main(String[] args) {
//        partOne();
        partTwo();
    }

    private static void partOne() {
        try {
            List<String> lines = FileUtils.readLines(new File("src/main/resources/day4/input.txt"), StandardCharsets.UTF_8);
            String passport = "";
            int validPassports = 0;
            List<String> passports = new LinkedList<>();
            for (String line : lines) {
                passport += line;
                passport += " ";
                if (line.isEmpty()) {
                    passports.add(passport);
                    passport = "";
                }
            }
            passports.add(passport);
            for (String p : passports) {
                if (confirmValid(p)) {
                    validPassports++;
                }
            }
            System.out.println(validPassports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void partTwo() {
        try {
            List<String> lines = FileUtils.readLines(new File("src/main/resources/day4/input.txt"), StandardCharsets.UTF_8);
            String passport = "";
            int validPassports = 0;
            List<String> passports = new LinkedList<>();
            for (String line : lines) {
                passport += line;
                passport += " ";
                if (line.isEmpty()) {
                    passports.add(passport);
                    passport = "";
                }
            }
            passports.add(passport);
            for (String p : passports) {
                if (confirmValidTwo(p)) {
                    validPassports++;
                }
            }
            System.out.println(validPassports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean confirmValidTwo(String passport) {
        if (!confirmValid(passport)) {
            return false;
        }
        String[] split = passport.split(" ");
        for (String entry : split) {
            String[] kv = entry.split(":");
            String key = kv[0];
            String value = kv[1];
            if (!checkField(key, value)) {
                return false;
            }
        }
        return true;

    }

    private static boolean checkField(String key, String value) {
        switch (key) {
            case "byr":
                return 1920 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2002;
            case "iyr":
                return 2010 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2020;
            case "eyr":
                return 2020 <= Integer.parseInt(value) && Integer.parseInt(value) <= 2030;
            case "hgt":
                if (!value.contains("cm") && !value.contains("in")) return false;
                String unit = value.substring(value.length() - 2);
                int hgt = Integer.parseInt(value.substring(0, value.length() - 2));
                if (unit.equalsIgnoreCase("cm")) return 150 <= hgt && hgt <= 193;
                if (unit.equalsIgnoreCase("in")) return 59 <= hgt && hgt <= 76;
            case "hcl":
                return value.matches("#([0-9a-f]{6})");
            case "ecl":
                return Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value);
            case "pid":
                return value.matches("[0-9]{9}");
            case "cid": return true;
            default:
                return false;
        }
    }

    private static boolean confirmValid(String passport) {
        Set<String> missing = new HashSet<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
        String[] entries = passport.split(" ");
        for (String entry : entries) {
            String[] split = entry.split(":");
            missing.remove(split[0]);
        }
        return missing.size() == 0;
    }
}
