package com.rohan.dragoncell.GameUtils;

import com.badlogic.gdx.graphics.Color;
import com.rohan.dragoncell.GameScenes.MainScreen;

import java.util.ArrayList;
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

    public static final HashMap<String, Float> fuel = new HashMap<String, Float>() {{
            put("Coal", 20f);
            put("Wood", 30f);
            put("Hardened Wood", 25f);
            put("Wooden Stick", 35f);
    }};

    public static final HashMap<String, Float> presserFuel = new HashMap<String, Float>() {{
        put("Small Blade", 20f);
        put("Large Blade", 12f);
    }};

    public static final ArrayList<String> ore = new ArrayList<String>() {{
        add("stone");
        add("copper ore");
        add("iron ore");
        add("sasmite ore");
        add("crimstone ore");
        add("sand");
        add("welded sand");
    }};

    public static final ArrayList<String> juiceable = new ArrayList<String>() {{
        add("blackberry");
        add("green apple");
        add("cherry");
        add("carrot");
        add("cactus");
    }};

    public static final HashMap<String, Integer> juicedInto = new HashMap<String, Integer>() {{
        put("Blackberry", 43);
        put("Green Apple", 42);
        put("Cherry", 45);
        put("Carrot", 41);
        put("Cactus", 47);
    }};

    // 1 woodland
    // 2 woodland river
    // 3 desert
    // 4 beach
    // 5 orefield
    // 6 island

    // room #, up, right, down, left

    // biome type, room #

    public static Color convert(long hex) {
        float a = (hex & 0xFF000000L) >> 24;
        float r = (hex & 0xFF0000L) >> 16;
        float g = (hex & 0xFF00L) >> 8;
        float b = (hex & 0xFFL);
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public static final HashMap<Integer, String[]> areas = new HashMap<Integer, String[]>() {{
        put(1, new String[] {"3 2", "2 3", "5 4", "5 5"});
        put(2, new String[] {"3 6", "1 7", "1 1", "4 8"});
        put(3, new String[] {"2 2", "2 3", "5 4", "1 1"});
        put(4, new String[] {"1 1", "2 3", "5 4", "5 5"});
        put(5, new String[] {"2 2", "1 1", "5 4", "5 5"});
        put(6, new String[] {"5 11", "5 11", "3 2", "4 8"});
        put(7, new String[] {"5 9", "5 10", "2 3", "3 2"});
        put(8, new String[] {"4 8", "3 2", "4 8", "  "});

    }};

    public static final HashMap<Integer, String> getBiomeByInt = new HashMap<Integer, String>() {{
        put(1, "Woodland");
        put(2, "Woodland River");
        put(3, "Desert");
        put(4, "Beach");
        put(5, "Ore Field");
        put(6, "Island");
    }};

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
