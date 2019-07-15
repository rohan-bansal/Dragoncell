package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.rohan.dragoncell.FileUtils.GifDecoder;
import com.rohan.dragoncell.FileUtils.Tuple;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;
import com.rohan.dragoncell.GameUtils.ObtainMethods;

import java.util.ArrayList;
import java.util.HashMap;

public class Forge {

    private Inventory inventory;
    private MaterialsList materials;

    public ArrayList<Sprite> forgeSlots = new ArrayList<Sprite>();
    public ArrayList<Sprite> fuelSlots = new ArrayList<Sprite>();
    private ArrayList<Material> forgeItems = new ArrayList<Material>();
    private ArrayList<Material> fuelItems = new ArrayList<Material>();
    private ArrayList<Material> forgeItemsToRemove = new ArrayList<Material>();
    private ArrayList<Material> fuelItemsToRemove = new ArrayList<Material>();
    private ArrayList<Material> smeltedItemsToRemove = new ArrayList<Material>();

    private BitmapFont timeDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    GlyphLayout layout = new GlyphLayout();

    public boolean smeltingActive = false;


    private ArrayList<Sprite> clearIcons = new ArrayList<Sprite>();
    private float stateTime = 0f;
    private ArrayList<Material> smeltedMaterial = new ArrayList<Material>();

    private HashMap<Integer, Tuple<Integer, Integer>> forgeSlotPositions = new HashMap<Integer, Tuple<Integer, Integer>>();
    private HashMap<Integer, Tuple<Integer, Integer>> fuelSlotPositions = new HashMap<Integer, Tuple<Integer, Integer>>();
    private HashMap<Integer, Float> smeltTime = new HashMap<Integer, Float>() {{
        put(1, 20f);
        put(2, 20f);
        put(3, 20f);
        put(4, 20f);
    }};

    private Animation<TextureRegion> smeltAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Interface/HUD/processing2.gif").read());

    private Sprite highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot2.png")));
    private Sprite highlight_fuel = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/fuelSlot2.png")));

    private Sprite clear_highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear_highlight.png")));

    public Forge(Inventory inventory, MaterialsList materials) {
        this.inventory = inventory;
        this.materials = materials;

        int xPos = 130;
        int yPos = 300;
        int incr = 1;
        while(incr < 9) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot.png")));
            temp.setCenter(xPos, yPos);
            forgeSlots.add(temp);
            xPos += 100;
            if(xPos > 500) {
                xPos = 130;
                yPos -= 100;
            }
            incr++;
        }

        int x_Pos = 80;
        int incr_ = 1;
        while(incr_ < 5) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/fuelSlot.png")));
            temp.setCenter(x_Pos, 320);
            fuelSlots.add(temp);
            x_Pos += 100;
            if(x_Pos > 500) {
                x_Pos = 130;
            }
            incr_++;
        }

        int xPos_ = 130;
        for(int x = 0; x < 4; x++) {
            Sprite tempIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear.png")));
            tempIcon.setCenter(xPos_, 100);
            clearIcons.add(tempIcon);
            xPos_ += 100;
        }

        for(Sprite slot : forgeSlots) {
            forgeSlotPositions.put(forgeSlots.indexOf(slot) + 1, new Tuple<Integer, Integer>(Math.round(slot.getX() + 25), Math.round(slot.getY() + 25)));
        }
        for(Sprite slot2 : fuelSlots) {
            fuelSlotPositions.put(fuelSlots.indexOf(slot2) + 1, new Tuple<Integer, Integer>(Math.round(slot2.getX() + 20), Math.round(slot2.getY() + 20)));
        }

        timeDrawer.getData().setScale(0.5f);
    }

    public boolean addToForgeSlot(int slot) {
        Material temp = new Material(inventory.followMaterial);
        temp.setCenter(forgeSlotPositions.get(slot).x, forgeSlotPositions.get(slot).y);
        temp.forgePos = slot;

        if(!temp.isOre) {
           return true;
        }
        if(temp.getSprite().getY() < 250) {
            return true;
        }

        for(Material material : forgeItems) {
            if(material.getSprite().getX() == temp.getSprite().getX() && material.getSprite().getY() == temp.getSprite().getY()) {
                Gdx.app.log("Forge", "Forge Drop Failed");
                return true;
            }
        }
        Gdx.app.log("Forge", "Forge Drop Successful");
        forgeItems.add(temp);
        inventory.followMaterial = null;
        return false;
    }

    public boolean addToFuelSlot(int slot) {
        Material temp = new Material(inventory.followMaterial);
        temp.setCenter(fuelSlotPositions.get(slot).x, fuelSlotPositions.get(slot).y);
        temp.fuelPos = slot;

        if(!ObtainMethods.fuel.keySet().contains(temp.name)) {
            return true;
        }

        for(Material material : fuelItems) {
            if(material.getSprite().getX() == temp.getSprite().getX() && material.getSprite().getY() == temp.getSprite().getY()) {
                Gdx.app.log("Forge", "Fuel Drop Failed");
                return true;
            }
        }

        smeltTime.put(slot, ObtainMethods.fuel.get(temp.name));

        Gdx.app.log("Forge", "Fuel Drop Successful");
        fuelItems.add(temp);
        inventory.followMaterial = null;
        return false;
    }


    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        for(Sprite slot : forgeSlots) {
            if(slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                highlight.setPosition(slot.getX(), slot.getY());
                highlight.draw(batch);
            } else {
                slot.draw(batch);
            }
        }

        for(Sprite slot : fuelSlots) {
            if(slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                highlight_fuel.setPosition(slot.getX(), slot.getY());
                highlight_fuel.draw(batch);
            } else {
                slot.draw(batch);
            }
        }

        for(Sprite icon : clearIcons) {
            if(icon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                clear_highlight.setPosition(icon.getX(), icon.getY());
                clear_highlight.draw(batch);
                if(Gdx.input.justTouched()) {
                    for(Material m : fuelItems) {
                        if(m.fuelPos == clearIcons.indexOf(icon) + 1) {
                            inventory.addItem(m);
                            fuelItemsToRemove.add(m);
                        }
                    }
                    for(Material m : forgeItems) {
                        if(m.forgePos == clearIcons.indexOf(icon) + 1) {
                            inventory.addItem(m);
                            smeltTime.put(m.forgePos, 20f);
                            forgeItemsToRemove.add(m);
                        }
                    }

                }
            } else {
                icon.draw(batch);
            }
        }

        for(Material forgeItem : forgeItems) {
            forgeItem.render(batch);
            for(Material f : fuelItems) {
                if(f.fuelPos == forgeItem.forgePos) {
                    batch.draw(smeltAnimation.getKeyFrame(stateTime), forgeItem.getSprite().getX() - 7, forgeItem.getSprite().getY() - 50);
                    smeltTime.put(forgeItem.forgePos, smeltTime.get(forgeItem.forgePos) - 0.015f);

                    layout.setText(timeDrawer, ObtainMethods.round((double) smeltTime.get(forgeItem.forgePos), 1) + "");
                    timeDrawer.draw(batch, ObtainMethods.round((double) smeltTime.get(forgeItem.forgePos), 1) + "", forgeItem.getSprite().getX() - 30, forgeItem.getSprite().getY() - 30);

                    if(smeltTime.get(forgeItem.forgePos) <= 0) {
                        smeltTime.put(forgeItem.forgePos, 20f);
                        smeltItem(forgeItem.forgePos);
                    }
                }
            }
        }

        if(fuelItems.size() > 0 && forgeItems.size() > 0) {
            smeltingActive = true;
        } else {
            smeltingActive = false;
        }

        for(Material fuelItem : fuelItems) {
            fuelItem.render(batch);
        }

        for(Material fuelItem : fuelItemsToRemove) {
            fuelItems.remove(fuelItem);
        }

        for(Material forgeItem : forgeItemsToRemove) {
            forgeItems.remove(forgeItem);
        }

        for(Material item : smeltedItemsToRemove) {
            smeltedMaterial.remove(item);
        }

        if(smeltedMaterial.size() != 0) {
            for(Material m : smeltedMaterial) {
                m.render(batch);
                if(m.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        inventory.addItem(m);
                        smeltedItemsToRemove.add(m);
                    }
                }
            }

        }
    }

    public void threadTime() {
        for(Material forgeItem : forgeItems) {
            for(Material f : fuelItems) {
                if(f.fuelPos == forgeItem.forgePos) {
                    smeltTime.put(forgeItem.forgePos, smeltTime.get(forgeItem.forgePos) - 0.015f);
                    if(smeltTime.get(forgeItem.forgePos) <= 0) {
                        smeltTime.put(forgeItem.forgePos, 20f);
                        smeltItem(forgeItem.forgePos);
                    }
                }
            }
        }
    }

    private void smeltItem(int forgePos) {
        Material tempMaterial = null;
        for(Material m : forgeItems) {
            if(m.forgePos == forgePos) {
                tempMaterial = new Material(m.smeltInto);
                forgeItemsToRemove.add(m);
            }
        }
        tempMaterial.setCenter(forgeSlots.get((forgePos - 1) + 4).getX() + 25, forgeSlots.get((forgePos - 1) + 4).getY() + 25);
        smeltedMaterial.add(tempMaterial);

        for(Material f : fuelItems) {
            if(f.fuelPos == forgePos) {
                fuelItemsToRemove.add(f);
            }
        }
    }
}
