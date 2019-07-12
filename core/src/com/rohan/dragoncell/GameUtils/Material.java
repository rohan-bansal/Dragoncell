package com.rohan.dragoncell.GameUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;

public class Material {

    public String name;
    public String description;
    public int ID;
    public String obtainMethod;
    public int rarity;
    public boolean unpickupable;
    private Sprite sprite;

    private boolean canBeFilled;
    private boolean isOre;
    private boolean isSeed;

    private ArrayList<Material> recipe = new ArrayList<Material>();

    private Material smeltInto;
    private int smeltNumber;

    private Material seedDrop;
    private int foodNumber;

    private ArrayList<Material> liquidFill = new ArrayList<Material>();
    private Material currentLiquid;

    private ArrayList<Material> grinderRecipe = new ArrayList<Material>();


    public Material(String name, String description, int ID, int rarity, boolean... unpickupable) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.rarity = rarity;

        if(unpickupable.length != 0) {
            this.unpickupable = unpickupable[0];
        }

        String temp = name.toLowerCase();
        String temp2 = temp.replaceAll(" ", "_");
        this.sprite = new Sprite(new Texture(Gdx.files.internal("Materials/" + temp2 + ".png")));
        this.sprite.setSize(32, 32);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void setCenter(float x, float y, boolean... notCenter) {
        if(notCenter.length > 0) {
            sprite.setPosition(x, y);
        } else {
            sprite.setCenter(x, y);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    Material setObtainMethod(String method) {
        this.obtainMethod = method;
        return this;
    }

    Material setRecipe(Material[] recipe) {
        this.recipe.addAll(Arrays.asList(recipe));
        return this;
    }

    Material setSmelting(Material ingot, int smeltNumber) {
        this.isOre = true;
        this.smeltInto = ingot;
        this.smeltNumber = smeltNumber;
        return this;
    }

    Material setSeedDrop(Material food, int foodNumber) {
        this.isSeed = true;
        this.seedDrop = food;
        this.foodNumber = foodNumber;
        return this;
    }

    Material setCanBeFilled(boolean canBeFilled, Material[] liquid) {
        this.liquidFill.addAll(Arrays.asList(liquid));
        this.canBeFilled = canBeFilled;
        return this;
    }

    Material setGrinderRecipe(Material[] materials) {
        this.grinderRecipe.addAll(Arrays.asList(materials));
        return this;
    }
}
