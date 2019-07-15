package com.rohan.dragoncell.GameUtils;

// RARITY SCALE
// 1 - common
// 2 - uncommon
// 3 - rare
// 4 - epic
// 5 - legendary
// 6 - mystic

import com.badlogic.gdx.Gdx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MaterialsList {

    public final Material STICK = new Material("Wooden Stick", "Can be obtained from trees or carving", 1, 1).setFuel(true);
    public final Material STONE = new Material("Stone", "Hard material obtained from mining", 2, 1);
    public final Material AMBER = new Material("Amber", "Might contain a mosquito", 3, 3);
    public final Material DIRT = new Material("Dirt", "Diggy diggy hole", 4, 1).setFuel(true);
    public final Material WOOD = new Material("Wood", "Obtained from trees through sawing", 5, 1);
    public final Material SASMITE_ORE = new Material("Sasmite Ore", "Created from Earth's seismic motions", 6, 3);
    public final Material SASMITE_BAR = new Material("Sasmite Bar", "Very hard and shock resistant metal", 7, 3);
    public final Material COAL = new Material("Coal", "Powers almost everything", 8, 1).setFuel(true);
    public final Material CRIMSTONE_ORE = new Material("Crimstone Ore", "Epic mining drop", 9, 4);
    public final Material CRIMSTONE_BAR = new Material("Crimstone Bar", "Contains the essence of the Earth's core", 10, 4);
    public final Material IRON_ORE = new Material("Iron Ore", "One of the most abundant ores", 11, 2);
    public final Material IRON_INGOT = new Material("Iron Ingot", "Important crafting material", 12, 2);
    public final Material SEEDS = new Material("Seeds", "The most basic farming material", 13, 1);
    public final Material TORCH = new Material("Torch", "Creates light wherever it goes", 14, 1);
    public final Material HARDENED_STONE = new Material("Hardened Stone", "Much better building material", 15, 1);
    public final Material FLINT = new Material("Flint", "Found near rivers", 16, 1);
    public final Material FIRESTARTER = new Material("Firestarter", "Rubbing it on itself creates a spark", 17, 2);
    public final Material BLACKBERRY = new Material("Blackberry", "Found in abundance on bushes", 18, 1);
    public final Material GREEN_APPLE = new Material("Green Apple", "Commonly found on trees", 19, 1);
    public final Material CARROT = new Material("Carrot", "Found near rivers", 20, 1);
    public final Material HARDENED_WOOD = new Material("Hardened Wood", "Can hold much more weight", 21, 1).setFuel(true);
    public final Material CARROT_SEEDS = new Material("Carrot Seeds", "Grows multiple carrots", 22, 2);
    public final Material WHEAT = new Material("Wheat", "Most abundant crop in the world", 23, 1);
    public final Material COPPER_ORE = new Material("Copper Ore", "Stands out with its gold sheen", 24, 2);
    public final Material COPPER_INGOT = new Material("Copper Ingot", "Conducts electricity very well", 25, 2);
    public final Material BOOK = new Material("Book", "Filled with knowledge", 26, 2);
    public final Material BOTTLE = new Material("Bottle", "Can be filled with various liquids", 27, 2);
    public final Material WATER = new Material("Water", "Debug Item", 28, 1, true);
    public final Material SAND = new Material("Sand", "Near beaches and near rivers", 29, 2);
    public final Material GLASS = new Material("Glass", "Clear, see through version of sand", 30, 2);
    public final Material FIRE = new Material("FIRE", "Debug Item", 31, 1, true);
    public final Material LEATHER = new Material("Leather", "Obtained from cows and horses", 32, 2);
    public final Material PAPER = new Material("Paper", "The pen is mightier than the sword", 33, 2);
    public final Material RUBBER = new Material("Rubber", "The material of the future", 34, 4);
    public final Material WOODEN_BOWL = new Material("Wooden Bowl", "Can hold items", 35, 1);
    public final Material WELDED_SAND = new Material("Welded Sand", "Extremely packed sand. Very heavy", 36, 2).setDiscovered(true);
    public final Material THICK_GLASS = new Material("Thick Glass", "2 inches thicker", 37, 2);
    public final Material STEEL_INGOT = new Material("Steel Ingot", "Industrial grade metal", 38, 2);
    public final Material NAILS = new Material("Nails", "Hold a lot of things together", 39, 2);
    public final Material OIL = new Material("Oil", "Very slick", 40, 3).setFuel(true);
    public final Material CARROT_JUICE = new Material("Carrot Juice", "21 carrots in a bottle", 41, 1);
    public final Material APPLE_JUICE = new Material("Apple Cider", "Squashed apples", 42, 1);
    public final Material BERRY_JUICE = new Material("Berry Juice", "Tastes very good", 43, 1);


    public ArrayList<Material> materialList = new ArrayList<Material>();
    public HashMap<String, Boolean> discoveredMaterials = new HashMap<String, Boolean>();

    public MaterialsList() {
        initRecipes();

        Field[] fields = MaterialsList.class.getDeclaredFields();
        try {
            for(Field field : fields) {
                if (field.getType() == Material.class) {
                    ((Material) field.get(this)).discovered = true;
                    materialList.add((Material) field.get(this));
                    discoveredMaterials.put(((Material) field.get(this)).name, ((Material) field.get(this)).discovered);
                }
            }
        } catch (Exception e) {
            Gdx.app.log("Materials", "ArrayList Initialization Failed");
        }
    }

    public Material getMaterialByID(int ID) {
        for(Material material : materialList) {
            if(material.ID == ID) {
                return material;
            }
        }
        return null;
    }

    private void initRecipes() {

        STICK.setObtainMethod(ObtainMethods.TREE).setDiscovered(true);
        STONE.setObtainMethod(ObtainMethods.MINING).setSmelting(HARDENED_STONE, 1).setDiscovered(true);
        AMBER.setObtainMethod(ObtainMethods.MINING);
        DIRT.setObtainMethod(ObtainMethods.SHOVEL).setDiscovered(true);
        WOOD.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, STICK}).setDiscovered(true);
        SASMITE_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(SASMITE_BAR, 1);
        SASMITE_BAR.setObtainMethod(ObtainMethods.FORGE);
        COAL.setObtainMethod(ObtainMethods.MINING).setDiscovered(true);
        CRIMSTONE_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(CRIMSTONE_BAR, 1);
        CRIMSTONE_BAR.setObtainMethod(ObtainMethods.MINING);
        IRON_ORE.setObtainMethod(ObtainMethods.MINING).setSmelting(IRON_INGOT, 1);
        IRON_INGOT.setObtainMethod(ObtainMethods.MINING).setDiscovered(true);
        SEEDS.setObtainMethod(ObtainMethods.FARMING).setSeedDrop(WHEAT, 3).setRecipe(new Material[] {WHEAT, WHEAT, WHEAT});
        TORCH.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, COAL, FIRESTARTER}).setDiscovered(true);
        FLINT.setObtainMethod(ObtainMethods.SHOVEL).setDiscovered(true);
        FIRESTARTER.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {FLINT, IRON_INGOT, STICK}).setDiscovered(true);
        HARDENED_STONE.setObtainMethod(ObtainMethods.FORGE);
        BLACKBERRY.setObtainMethod(ObtainMethods.TREE).setJuicingRecipe(BERRY_JUICE);
        GREEN_APPLE.setObtainMethod(ObtainMethods.TREE).setJuicingRecipe(APPLE_JUICE);
        CARROT.setObtainMethod(ObtainMethods.FARMING).setJuicingRecipe(CARROT_JUICE);
        HARDENED_WOOD.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {WOOD, WOOD, WOOD});
        CARROT_SEEDS.setObtainMethod(ObtainMethods.FARMING).setSeedDrop(CARROT, 2).setRecipe(new Material[] {CARROT, CARROT, CARROT});
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
        PAPER.setObtainMethod(ObtainMethods.GRINDER).setCombinerRecipe(new Material[] {WOOD, STICK});
        STEEL_INGOT.setObtainMethod(ObtainMethods.GRINDER).setCombinerRecipe(new Material[] {IRON_INGOT, COAL});
        NAILS.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STEEL_INGOT, STICK});
        RUBBER.setObtainMethod(ObtainMethods.TREE);
        WOODEN_BOWL.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {STICK, STICK, STICK, HARDENED_WOOD});
        THICK_GLASS.setObtainMethod(ObtainMethods.FORGE);
        WELDED_SAND.setObtainMethod(ObtainMethods.WORKBENCH).setRecipe(new Material[] {SAND, SAND, SAND, SAND, SAND, SAND, SAND, SAND}).setSmelting(THICK_GLASS, 1);
        OIL.setObtainMethod(ObtainMethods.JUICER);
        CARROT_JUICE.setObtainMethod(ObtainMethods.JUICER);
        APPLE_JUICE.setObtainMethod(ObtainMethods.JUICER);
        BERRY_JUICE.setObtainMethod(ObtainMethods.JUICER);

    }
}
