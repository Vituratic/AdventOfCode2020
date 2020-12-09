package day7;

import java.util.LinkedList;
import java.util.List;

public class Bag {

    private String color;
    private List<Bag> children;

    public Bag(String color) {
        this.color = color;
        this.children = new LinkedList<>();
    }

    public void addChild(Bag child) {
        children.add(child);
    }

    public boolean containsChildWithColor(String color) {
        for (Bag child : children) {
            if (child.color.equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    public String getColor() {
        return this.color;
    }

    public List<Bag> getChildren() {
        return this.children;
    }
}
