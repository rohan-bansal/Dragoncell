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

    public boolean canBeFilled = false;
    public boolean isOre = false;
    public boolean isSeed = false;
    public boolean discovered = false;
    public boolean canBeCrafted = false;
    public boolean isFollowingMouse = false;
    public int slotNumber = 0;

    public ArrayList<Material> recipe = new ArrayList<Material>();

    private Material smeltInto;
    private int smeltNumber;

    private Material juicedInto;

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

    public Material(Material material) {
        this.name = material.name;
        this.description = material.description;
        this.ID = material.ID;
        this.rarity = material.rarity;

        if(material.unpickupable) {
            this.unpickupable = true;
        }

        String temp = name.toLowerCase();
        String temp2 = temp.replaceAll(" ", "_");
        this.sprite = new Sprite(new Texture(Gdx.files.internal("Materials/" + temp2 + ".png")));
        this.sprite.setSize(32, 32);
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Material setVariables(Material material) {
        material.canBeFilled = canBeFilled;
        material.canBeCrafted = canBeCrafted;
        material.isOre = isOre;
        material.isSeed = isSeed;

        if(canBeCrafted) {
            material.recipe = recipe;
        }
        if(canBeFilled) {
            material.liquidFill = liquidFill;
        }
        if(isOre) {
            material.smeltInto = smeltInto;
            material.smeltNumber = smeltNumber;
        }
        if(isSeed) {
            material.seedDrop = seedDrop;
            material.foodNumber = foodNumber;
        }
        return material;
    }

    public Material setDiscovered(boolean discovered) {
        this.discovered = discovered;
        return this;
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
        this.canBeCrafted = true;
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

    Material setCombinerRecipe(Material[] materials) {
        this.grinderRecipe.addAll(Arrays.asList(materials));
        return this;
    }

    Material setJuicingRecipe(Material juicedInto) {
        this.juicedInto = juicedInto;
        return this;
    }
}
