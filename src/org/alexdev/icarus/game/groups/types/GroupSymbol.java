package org.alexdev.icarus.game.groups.types;

public class GroupSymbol {
    private int id;
    private String valueA;
    private String valueB;

    public GroupSymbol(int id, String valueA, String valueB) {
        this.id = id;
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public int getId() {
        return id;
    }

    public String getValueA() {
        return valueA;
    }

    public String getValueB() {
        return valueB;
    }
}
