package day7;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DaySevenHaversacks {

    public static void main(String[] args) throws IOException {
        List<String> lines = FileUtils.readLines(new File("src/main/resources/day7/input.txt"), StandardCharsets.UTF_8);
        int amountOfBagsAbleToHoldShinyGold = getAmountOfBagsAbleToHoldShinyGold(lines);
        System.out.println("Part 1: " + amountOfBagsAbleToHoldShinyGold);
        int amountOfBagsWithinOneShinyGold = getAmountOfBagsWithinOneShinyGold(lines);
        System.out.println("Part 2: " + amountOfBagsWithinOneShinyGold);
    }

    private static int getAmountOfBagsWithinOneShinyGold(List<String> lines) {
        List<Bag> allBags = getAllBags(lines);
        Bag shinyGold = null;
        for (Bag bag : allBags) {
            if (bag.getColor().equalsIgnoreCase("shiny gold")) {
                shinyGold = bag;
                break;
            }
        }
        List<Bag> bagsWithinShinyGold = new LinkedList<>(shinyGold.getChildren());
        List<Bag> toGetChildrenFrom = new LinkedList<>();
        for (Bag bag : bagsWithinShinyGold) {
            toGetChildrenFrom.add(getBagFromColor(bag.getColor(), allBags));
        }
        do {
            List<Bag> toAdd = new LinkedList<>();
            for (Bag bag : toGetChildrenFrom) {
                toAdd.addAll(bag.getChildren());
            }
            toGetChildrenFrom = new LinkedList<>();
            for (Bag bag : toAdd) {
                toGetChildrenFrom.add(getBagFromColor(bag.getColor(), allBags));
            }
            bagsWithinShinyGold.addAll(toAdd);
        } while (toGetChildrenFrom.size() > 0);
        return bagsWithinShinyGold.size();
    }

    private static Bag getBagFromColor(String color, List<Bag> allBags) {
        for (Bag bag : allBags) {
            if (bag.getColor().equalsIgnoreCase(color)) {
                return bag;
            }
        }
        return null;
    }

    private static int getAmountOfBagsAbleToHoldShinyGold(List<String> lines) {
        List<Bag> allBags = getAllBags(lines);
        List<Bag> bagsContainingShinyGold = getAllBagsContainingChildWithColor(allBags, "shiny gold");
        List<Bag> toCheck = new LinkedList<>(bagsContainingShinyGold);
        Set<String> colorChecked = new HashSet<>();
        do {
            List<Bag> toAdd = new LinkedList<>();
            for (Bag bag : toCheck) {
                if (colorChecked.contains(bag.getColor())) {
                    continue;
                }
                colorChecked.add(bag.getColor());
                toAdd.addAll(getAllBagsContainingChildWithColor(allBags, bag.getColor()));
            }
            toCheck = new LinkedList<>(toAdd);
        } while (toCheck.size() > 0);
        return colorChecked.size();
    }

    private static List<Bag> getAllBagsContainingChildWithColor(List<Bag> allBags, String color) {
        List<Bag> bagsContainingColor = new LinkedList<>();
        for (Bag bag : allBags) {
            if (bag.containsChildWithColor(color)) {
                bagsContainingColor.add(bag);
            }
        }
        return bagsContainingColor;
    }

    private static List<Bag> getAllBags(List<String> lines) {
        List<Bag> bags = new LinkedList<>();
        for (String line : lines) {
            String[] split = line.split("contain");
            String bagColor = split[0].substring(0, split[0].indexOf(" bag")).trim();
            String content = split[1];
            String[] contents = content.split(",");
            Bag bag = new Bag(bagColor);
            for (String singleContent : contents) {
                if (singleContent.trim().substring(0, 2).equalsIgnoreCase("no")) {
                    continue;
                }
                int amountOfBags = Integer.parseInt(String.valueOf(singleContent.trim().charAt(0)));
                String color = singleContent.substring(2, singleContent.indexOf(" bag")).trim();
                for (int i = 0; i < amountOfBags; i++) {
                    bag.addChild(new Bag(color));
                }
            }
            bags.add(bag);
        }
        return bags;
    }
}
