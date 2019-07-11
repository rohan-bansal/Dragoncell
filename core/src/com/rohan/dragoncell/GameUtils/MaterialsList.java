package com.rohan.dragoncell.GameUtils;

// RARITY SCALE
// 1 - common
// 2 - uncommon
// 3 - rare
// 4 - epic
// 5 - legendary
// 6 - mystic

public class MaterialsList {

    public final Material STICK = new Material("Wooden Stick", "Can be obtained from trees or carving", 1, 1);
    public final Material STONE = new Material("Stone", "Hard material obtained from mining", 2, 1);
    public final Material AMBER = new Material("Amber", "Rare mining drop", 3, 3);
    public final Material DIRT = new Material("Dirt", "Diggy diggy hole", 4, 1);
    public final Material WOOD = new Material("Wood", "Obtained from trees through sawing", 5, 1);
    public final Material SASMITE_ORE = new Material("Sasmite Ore", "Rare mining drop", 6, 3);
    public final Material SASMITE_BAR = new Material("Sasmite Bar", "Very hard and shock resistant metal", 7, 3);
    public final Material COAL = new Material("Coal", "Powers almost everything", 8, 1);
    public final Material CRIMSTONE_ORE = new Material("Crimstone Ore", "Epic mining drop", 9, 4);
    public final Material CRIMSTONE_BAR = new Material("Crimstone Bar", "Contains the essence of the Earth's core", 10, 4);
    public final Material IRON_ORE = new Material("Iron Ore", "Uncommon mining drop", 10, 2);
    public final Material IRON_INGOT = new Material("Iron Ingot", "Important crafting material", 11, 2);
    public final Material SEEDS = new Material("Seeds", "The most basic farming material", 12, 1);
    public final Material TORCH = new Material("Torch", "Creates light wherever it goes", 13, 1);
    public final Material HARDENED_STONE = new Material("Hardened Stone", "Much better building material", 14, 1);
    public final Material FLINT = new Material("Flint", "Found near rivers", 15, 1);
    public final Material FIRESTARTER = new Material("Firestarter", "Rubbing it on itself creates a spark", 16, 2);
    public final Material BLACKBERRY = new Material("Blackberry", "Found in abundance on bushes", 17, 1);
    public final Material GREEN_APPLE = new Material("Green Apple", "Commonly found on trees", 18, 1);
    public final Material CARROT = new Material("Carrot", "Found near rivers", 19, 1);
    public final Material HARDENED_WOOD = new Material("Hardened Wood", "Can hold much more weight", 20, 1);
    public final Material CARROT_SEEDS = new Material("Carrot Seeds", "Grows multiple carrots", 21, 2);
    public final Material WHEAT = new Material("Wheat", "Most abundant crop in the world", 22, 1);
    public final Material COPPER_ORE = new Material("Copper Ore", "Uncommon mining drop", 23, 2);
    public final Material COPPER_INGOT = new Material("Copper Ingot", "Conducts electricity very well", 24, 2);
    public final Material BOOK = new Material("Book", "Filled with knowledge", 25, 2);
    public final Material BOTTLE = new Material("Bottle", "Can be filled with various liquids", 26, 2);
    public final Material WATER = new Material("Water", "Debug Item", 27, 1, true);
    public final Material SAND = new Material("Sand", "Can only be found on beaches and near rivers", 28, 2);
    public final Material GLASS = new Material("Glass", "Clear, see through version of sand", 29, 2);
    public final Material FIRE = new Material("FIRE", "Debug Item", 30, 1, true);
    public final Material LEATHER = new Material("Leather", "Obtained from cows and horses", 31, 2);
    public final Material PAPER = new Material("Paper", "The pen is mightier than the sword", 32, 2);
    public final Material RUBBER = new Material("Rubber", "The material of the future", 33, 2);
    public final Material WOODEN_BOWL = new Material("Wooden Bowl", "Can hold items", 34, 1);
    public final Material WELDED_SAND = new Material("Welded Sand", "Extremely packed sand. Very heavy", 35, 2);
    public final Material THICK_GLASS = new Material("Thick Glass", "2 inches thicker", 36, 2);



    public MaterialsList() {
        initRecipes();
    }

    private void initRecipes() {

        STICK.setObtainMethod(ObtainMethods.TREE);
        STONE.setObtainMethod(ObtainMethods.MINING).setSmelting(HARDENED_STONE, 2);
        AMBER.setObtainMethod(ObtainMethods.MINING);
        DIRT.setObtainMethod(ObtainMethods.SHOVEL);
        WOOD.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, STICK});
        SASMITE_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(SASMITE_BAR, 1);
        SASMITE_BAR.setObtainMethod(ObtainMethods.FORGE);
        COAL.setObtainMethod(ObtainMethods.MINING);
        CRIMSTONE_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(CRIMSTONE_BAR, 1);
        CRIMSTONE_BAR.setObtainMethod(ObtainMethods.MINING);
        IRON_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(IRON_INGOT, 1);
        IRON_INGOT.setObtainMethod(ObtainMethods.MINING);
        SEEDS.setObtainMethod(ObtainMethods.SHOVEL).setSeedDrop(WHEAT, 3);
        TORCH.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, COAL, FIRESTARTER});
        FLINT.setObtainMethod(ObtainMethods.SHOVEL);
        FIRESTARTER.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {FLINT, IRON_INGOT, STICK});
        HARDENED_STONE.setObtainMethod(ObtainMethods.FORGE);
        BLACKBERRY.setObtainMethod(ObtainMethods.BUSHES);
        GREEN_APPLE.setObtainMethod(ObtainMethods.TREE); //
        CARROT.setObtainMethod(ObtainMethods.FARMING);
        HARDENED_WOOD.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {WOOD, WOOD, WOOD});
        CARROT_SEEDS.setObtainMethod(ObtainMethods.SHOVEL).setSeedDrop(CARROT, 2);
        WHEAT.setObtainMethod(ObtainMethods.FARMING);
        COPPER_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(COPPER_INGOT, 1);
        COPPER_INGOT.setObtainMethod(ObtainMethods.MINING);
        BOOK.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {LEATHER, LEATHER, PAPER, PAPER});
        BOTTLE.setObtainMethod(ObtainMethods.WORKBENCH).setCanBeFilled(true, new Material [] {WATER}).setRecipe(new Material[] {GLASS, GLASS, GLASS, WOOD});
        SAND.setObtainMethod(ObtainMethods.SHOVEL).setSmelting(GLASS, 1);
        GLASS.setObtainMethod(ObtainMethods.WORKBENCH);
        FIRE.setObtainMethod(ObtainMethods.UNOBTAINABLE);
        WATER.setObtainMethod(ObtainMethods.UNOBTAINABLE);
        LEATHER.setObtainMethod(ObtainMethods.ANIMALS);
        PAPER.setObtainMethod(ObtainMethods.GRINDER).setGrinderRecipe(new Material[] {WOOD, STICK});
        RUBBER.setObtainMethod(ObtainMethods.TREE);
        WOODEN_BOWL.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, STICK, STICK, HARDENED_WOOD});
        THICK_GLASS.setObtainMethod(ObtainMethods.FORGE);
        WELDED_SAND.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {SAND, SAND, SAND, SAND, SAND, SAND, SAND, SAND}).setSmelting(THICK_GLASS, 2);
    }
}
