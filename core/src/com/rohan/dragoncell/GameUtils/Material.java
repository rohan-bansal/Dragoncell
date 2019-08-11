package com.rohan.dragoncell.GameUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;

public class Material implements Json.Serializable {

    public String name;
    public String description;
    public int ID;
    public String obtainMethod;
    public int rarity;
    public boolean unpickupable;
    public int levelNeeded = 1;
    private Sprite sprite;

    public boolean canBeFilled = false;
    public boolean isOre = false;
    public boolean isSeed = false;
    public boolean discovered = false;
    public boolean canBeCrafted = false;
    public boolean canBeJuiced = false;
    public boolean canBeGround = false;
    public boolean isFollowingMouse = false;
    public boolean isFuel = false;
    public int slotNumber = 0;

    public int fuelPos = 0, forgePos = 0;

    public ArrayList<Material> recipe = new ArrayList<Material>();

    public Material smeltInto;

    public Material juicedInto;

    public Material seedDrop;

    private ArrayList<Material> liquidFill = new ArrayList<Material>();
    private Material currentLiquid;

    public ArrayList<Material> grinderRecipe = new ArrayList<Material>();


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

    public Material() {}

    public Material(String name, String path, String description, int ID, int rarity, boolean... unpickupable) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.rarity = rarity;

        if(unpickupable.length != 0) {
            this.unpickupable = unpickupable[0];
        }

        String temp = name.toLowerCase();
        String temp2 = temp.replaceAll(" ", "_");
        this.sprite = new Sprite(new Texture(path));
        this.sprite.setSize(32, 32);
    }

    public Material(Material material) {
        this.name = material.name;
        this.levelNeeded = material.levelNeeded;
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

        this.canBeFilled = material.canBeFilled;
        this.canBeCrafted = material.canBeCrafted;
        this.isOre = material.isOre;
        this.isSeed = material.isSeed;
        this.discovered = material.discovered;
        this.obtainMethod = material.obtainMethod;
        this.canBeJuiced = material.canBeJuiced;
        this.isFollowingMouse = material.isFuel;
        this.canBeGround = material.canBeGround;

        if(material.canBeCrafted) {
            this.recipe = material.recipe;
        }
        if(material.canBeFilled) {
            this.liquidFill = material.liquidFill;
        }
        if(material.isOre) {
            this.smeltInto = material.smeltInto;
        }
        if(material.isSeed) {
            this.seedDrop = material.seedDrop;
        }
        if(material.canBeJuiced) {
            this.juicedInto = material.juicedInto;
        }
        if(material.canBeGround) {
            this.grinderRecipe = material.grinderRecipe;
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Material setVariables(Material material) {
        material.canBeFilled = canBeFilled;
        material.canBeCrafted = canBeCrafted;
        material.levelNeeded = levelNeeded;
        material.isOre = isOre;
        material.isSeed = isSeed;
        material.discovered = discovered;
        material.obtainMethod = obtainMethod;
        material.canBeJuiced = canBeJuiced;
        material.isFuel = isFuel;
        material.canBeGround = canBeGround;

        if(canBeCrafted) {
            material.recipe = recipe;
        }
        if(canBeFilled) {
            material.liquidFill = liquidFill;
        }
        if(isOre) {
            material.smeltInto = smeltInto;
        }
        if(isSeed) {
            material.seedDrop = seedDrop;
        }
        if(canBeGround) {
            material.grinderRecipe = grinderRecipe;
        }
        return material;
    }

    public Material setFuel(boolean fuel) {
        this.isFuel = fuel;
        return this;
    }

    public Material setLevelNeeded(int level) {
        this.levelNeeded = level;
        return this;
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
        return this;
    }

    Material setSeedDrop(Material food, int foodNumber) {
        this.isSeed = true;
        this.seedDrop = food;
        return this;
    }

    Material setCanBeFilled(boolean canBeFilled, Material[] liquid) {
        this.liquidFill.addAll(Arrays.asList(liquid));
        this.canBeFilled = canBeFilled;
        return this;
    }

    Material setCombinerRecipe(Material[] materials) {
        this.grinderRecipe.addAll(Arrays.asList(materials));
        this.canBeGround = true;
        return this;
    }

    Material setJuicingRecipe(Material juicedInto) {
        this.juicedInto = juicedInto;
        this.canBeJuiced = true;
        return this;
    }

    @Override
    public void write(Json json) {
        json.writeValue("name", name);
        json.writeValue("description", description);
        json.writeValue("ID", ID);
        json.writeValue("obtainMethod", obtainMethod);
        json.writeValue("rarity", rarity);
        json.writeValue("unpickupable", unpickupable);
        json.writeValue("levelNeeded", levelNeeded);
        json.writeValue("spritePath", "Materials/" + name.toLowerCase().replaceAll(" ", "_") + ".png");
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        name = jsonData.getString("name");
        description = jsonData.getString("description");
        ID = jsonData.getInt("ID");
        obtainMethod = jsonData.getString("obtainMethod");
        rarity = jsonData.getInt("rarity");
        unpickupable = jsonData.getBoolean("unpickupable");
        levelNeeded = jsonData.getInt("levelNeeded");
        sprite = new Sprite(new Texture(jsonData.getString("spritePath")));

        sprite.setSize(32, 32);
    }
}
