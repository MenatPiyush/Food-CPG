package com.food.cpg.inventory;

public enum Unit {
    GRAM("g", 1.0),
    LB("lb", 453.592),
    FL_OZ("fl oz", 29.574),
    GAL("gal", 3785.412),
    L("L", 1000.0),
    PIECE("Pc", 1);

    private final String alias;
    private final double gramValue;

    Unit(String alias, double gramValue) {
        this.alias = alias;
        this.gramValue = gramValue;
    }

    public static Unit from(String unitValue) {
        for (Unit unit : values()) {
            if (unit.getAlias().equalsIgnoreCase(unitValue)) {
                return unit;
            }
        }
        return PIECE;
    }

    public String getAlias() {
        return this.alias;
    }

    public double getGramValue() {
        return gramValue;
    }
}
