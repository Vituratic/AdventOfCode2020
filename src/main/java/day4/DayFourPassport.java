package day4;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DayFourPassport {

    public static void main(String[] args) {
        partOne();
    }

    private static void partOne() {
        try {
            List<String> lines = FileUtils.readLines(new File("src/main/resources/day4/input.txt"), StandardCharsets.UTF_8);
            String passport = "";
            int validPassports = 0;
            for (String line : lines) {
                passport += line;
                passport += " ";
                if (line.isEmpty()) {
                    if (confirmValid(passport)) {
                        validPassports++;
                    } else {
                        System.out.println(passport);
                        getMissing(passport);
                        System.out.println();
                    }
                    passport = "";
                }
            }
            System.out.println(validPassports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getMissing(String passport) {
        List<String> missing = new LinkedList<>();
        missing.add("byr");
        missing.add("iyr");
        missing.add("eyr");
        missing.add("hgt");
        missing.add("hcl");
        missing.add("ecl");
        missing.add("pid");
        missing.add("cid");
        for (String entry : passport.split(" ")) {
            missing.remove(entry.split(":")[0]);
        }
        String m = "";
        for (String s : missing) {
            m += s;
            m += " ";
        }
        System.out.println(m);
    }

    private static boolean confirmValid(String passport) {
        String[] entries = passport.split(" ");
        List<String> fields = new LinkedList<>();
        for (String entry : entries) {
            String[] split = entry.split(":");
            fields.add(split[0]);
        }
        fields.remove("cid");
        return fields.size() == 7;
        /*
        return fields.contains("byr")
                && fields.contains("eyr")
                && fields.contains("iyr")
                && fields.contains("hgt")
                && fields.contains("hcl")
                && fields.contains("pid")
                && fields.contains("ecl")
                ;
                */
    }
}
