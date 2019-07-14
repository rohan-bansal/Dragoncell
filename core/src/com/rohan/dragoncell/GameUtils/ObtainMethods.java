package com.rohan.dragoncell.GameUtils;

import java.util.HashMap;

public class ObtainMethods {

    public static final String TREE = "tree";

    public static final String FARMING = "farming";
    public static final String HANDSAW = "sawing";
    public static final String GRINDER = "grinding";
    public static final String ANIMALS = "animals";
    public static final String WORKBENCH = "crafting";
    public static final String MINING = "mining";
    public static final String FORGE = "smelting";
    public static final String CENTRIFUGE = "potions";
    public static final String JUICER = "juicing";
    public static final String SAWMILL = "sawing";
    public static final String LOOM = "weaving";
    public static final String STOVE = "food";
    public static final String SHOVEL = "digging";
    public static final String UNOBTAINABLE = "unobtainable";

    public static final HashMap<Integer, String> rarities = new HashMap<Integer, String>() {{
        put(1, "Common");
        put(2, "Uncommon");
        put(3, "Rare");
        put(4, "Epic");
        put(5, "Legendary");
        put(6, "Mystic");
    }};

}
