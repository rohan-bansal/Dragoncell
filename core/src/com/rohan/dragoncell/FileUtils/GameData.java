package com.rohan.dragoncell.FileUtils;

import com.rohan.dragoncell.GameUtils.ItemStack;
import com.rohan.dragoncell.GameUtils.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {

    private int health;
    private int hearts;
    private int coins;
    private int level;
    private int subLevels;
    private int subLevelGoal;

    private boolean desertUnlocked;
    private boolean oreFieldUnlocked;
    private boolean beachUnlocked;

    private boolean presserUnlocked;
    private boolean materialsActive;

    private int IDpage;
    private int areaNumber;

    private ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
    private HashMap<String, Boolean> unlocked = new HashMap<String, Boolean>();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubLevels() {
        return subLevels;
    }

    public void setSubLevels(int subLevels) {
        this.subLevels = subLevels;
    }

    public int getSubLevelGoal() {
        return subLevelGoal;
    }

    public void setSubLevelGoal(int subLevelGoal) {
        this.subLevelGoal = subLevelGoal;
    }

    public HashMap<String, Boolean> getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(HashMap<String, Boolean> unlocked) {
        this.unlocked = unlocked;
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isDesertUnlocked() {
        return desertUnlocked;
    }

    public void setDesertUnlocked(boolean desertUnlocked) {
        this.desertUnlocked = desertUnlocked;
    }

    public boolean isOreFieldUnlocked() {
        return oreFieldUnlocked;
    }

    public void setOreFieldUnlocked(boolean oreFieldUnlocked) {
        this.oreFieldUnlocked = oreFieldUnlocked;
    }

    public boolean isBeachUnlocked() {
        return beachUnlocked;
    }

    public void setBeachUnlocked(boolean beachUnlocked) {
        this.beachUnlocked = beachUnlocked;
    }

    public boolean isPresserUnlocked() {
        return presserUnlocked;
    }

    public void setPresserUnlocked(boolean presserUnlocked) {
        this.presserUnlocked = presserUnlocked;
    }

    public boolean isMaterialsActive() {
        return materialsActive;
    }

    public void setMaterialsActive(boolean materialsActive) {
        this.materialsActive = materialsActive;
    }

    public int getIDpage() {
        return IDpage;
    }

    public void setIDpage(int IDpage) {
        this.IDpage = IDpage;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }
}
