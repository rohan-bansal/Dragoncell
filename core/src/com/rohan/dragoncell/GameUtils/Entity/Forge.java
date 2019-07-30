package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.rohan.dragoncell.FileUtils.GifDecoder;
import com.rohan.dragoncell.FileUtils.Tuple;
import com.rohan.dragoncell.GameUtils.ItemStack;
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
    private ArrayList<ItemStack> forgeItems = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> fuelItems = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> forgeItemsToRemove = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> fuelItemsToRemove = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> smeltedItemsToRemove = new ArrayList<ItemStack>();
    private BitmapFont itemCounter = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);

    private BitmapFont timeDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    GlyphLayout layout = new GlyphLayout();

    public boolean smeltingActive = false;


    private ArrayList<Sprite> clearIcons = new ArrayList<Sprite>();
    private float stateTime = 0f;
    private ArrayList<ItemStack> smeltedMaterial = new ArrayList<ItemStack>();

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
        itemCounter.getData().setScale(0.5f);
    }

    public boolean addToForgeSlot(int slot) {
        ItemStack temp = new ItemStack(new Material(inventory.followMaterial.stackedItem), inventory.followMaterial.count);
        temp.stackedItem.setCenter(forgeSlotPositions.get(slot).x, forgeSlotPositions.get(slot).y);
        temp.stackedItem.forgePos = slot;

        if(!temp.stackedItem.isOre) {
           return true;
        }
        if(temp.stackedItem.getSprite().getY() < 250) {
            return true;
        }

        for(ItemStack material : forgeItems) {
            if(material.stackedItem.getSprite().getX() == temp.stackedItem.getSprite().getX() && material.stackedItem.getSprite().getY() == temp.stackedItem.getSprite().getY()) {
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
        ItemStack temp = new ItemStack(new Material(inventory.followMaterial.stackedItem), inventory.followMaterial.count);
        temp.stackedItem.setCenter(fuelSlotPositions.get(slot).x, fuelSlotPositions.get(slot).y);
        temp.stackedItem.fuelPos = slot;

        if(!ObtainMethods.fuel.keySet().contains(temp.stackedItem.name)) {
            return true;
        }

        for(ItemStack material : fuelItems) {
            if(material.stackedItem.getSprite().getX() == temp.stackedItem.getSprite().getX() && material.stackedItem.getSprite().getY() == temp.stackedItem.getSprite().getY()) {
                Gdx.app.log("Forge", "Fuel Drop Failed");
                return true;
            }
        }

        smeltTime.put(slot, ObtainMethods.fuel.get(temp.stackedItem.name));

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
                    for(ItemStack m : fuelItems) {
                        if(m.stackedItem.fuelPos == clearIcons.indexOf(icon) + 1) {
                            inventory.addItem(m.stackedItem, m.count);
                            fuelItemsToRemove.add(m);
                        }
                    }
                    for(ItemStack m : forgeItems) {
                        if(m.stackedItem.forgePos == clearIcons.indexOf(icon) + 1) {
                            inventory.addItem(m.stackedItem, m.count);
                            smeltTime.put(m.stackedItem.forgePos, 20f);
                            forgeItemsToRemove.add(m);
                        }
                    }

                }
            } else {
                icon.draw(batch);
            }
        }

        for(ItemStack forgeItem : forgeItems) {
            forgeItem.stackedItem.render(batch);
            if(forgeItem.count > 1) {
                itemCounter.draw(batch, forgeItem.count + "", forgeItem.stackedItem.getSprite().getX() + 26, forgeItem.stackedItem.getSprite().getY() + 8);
            }
            for(ItemStack f : fuelItems) {
                if(f.stackedItem.fuelPos == forgeItem.stackedItem.forgePos) {
                    batch.draw(smeltAnimation.getKeyFrame(stateTime), forgeItem.stackedItem.getSprite().getX() - 7, forgeItem.stackedItem.getSprite().getY() - 50);

                    smeltTime.put(forgeItem.stackedItem.forgePos, smeltTime.get(forgeItem.stackedItem.forgePos) - 0.015f);

                    layout.setText(timeDrawer, ObtainMethods.round((double) smeltTime.get(forgeItem.stackedItem.forgePos), 1) + "");
                    timeDrawer.draw(batch, ObtainMethods.round((double) smeltTime.get(forgeItem.stackedItem.forgePos), 1) + "",
                            forgeItem.stackedItem.getSprite().getX() - 30, forgeItem.stackedItem.getSprite().getY() - 30);

                    if(smeltTime.get(forgeItem.stackedItem.forgePos) <= 0) {
                        if(f.count > 1) {
                            smeltTime.put(forgeItem.stackedItem.forgePos, ObtainMethods.fuel.get(f.stackedItem.name));
                        } else {
                            smeltTime.put(forgeItem.stackedItem.forgePos, 20f);
                        }
                        smeltItem(forgeItem.stackedItem.forgePos);
                    }
                }
            }
        }

        if(fuelItems.size() > 0 && forgeItems.size() > 0) {
            smeltingActive = true;
        } else {
            smeltingActive = false;
        }

        for(ItemStack fuelItem : fuelItems) {
            fuelItem.stackedItem.render(batch);
            if(fuelItem.count > 1) {
                itemCounter.draw(batch, fuelItem.count + "", fuelItem.stackedItem.getSprite().getX() + 26, fuelItem.stackedItem.getSprite().getY() + 8);
            }
        }

        for(ItemStack fuelItem : fuelItemsToRemove) {
            fuelItems.remove(fuelItem);
        }

        for(ItemStack forgeItem : forgeItemsToRemove) {
            forgeItems.remove(forgeItem);
        }

        for(ItemStack item : smeltedItemsToRemove) {
            smeltedMaterial.remove(item);
        }

        if(smeltedMaterial.size() != 0) {
            for(ItemStack m : smeltedMaterial) {
                m.stackedItem.render(batch);
                if(m.count > 1) {
                    itemCounter.draw(batch, m.count + "", m.stackedItem.getSprite().getX() + 26, m.stackedItem.getSprite().getY() + 8);
                }
                if(m.stackedItem.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        inventory.addItem(m.stackedItem, m.count);
                        smeltedItemsToRemove.add(m);
                    }
                }
            }

        }
    }

    public void threadTime() {
        for(ItemStack forgeItem : forgeItems) {
            for(ItemStack f : fuelItems) {
                if(f.stackedItem.fuelPos == forgeItem.stackedItem.forgePos) {
                    smeltTime.put(forgeItem.stackedItem.forgePos, smeltTime.get(forgeItem.stackedItem.forgePos) - 0.015f);
                    if(smeltTime.get(forgeItem.stackedItem.forgePos) <= 0) {
                        if(f.count > 1) {
                            smeltTime.put(forgeItem.stackedItem.forgePos, ObtainMethods.fuel.get(f.stackedItem.name));
                        } else {
                            smeltTime.put(forgeItem.stackedItem.forgePos, 20f);
                        }
                        smeltItem(forgeItem.stackedItem.forgePos);
                    }
                }
            }
        }
    }

    private void smeltItem(int forgePos) {
        Material tempMaterial = null;
        for(ItemStack m : forgeItems) {
            if(m.stackedItem.forgePos == forgePos) {
                tempMaterial = new Material(m.stackedItem.smeltInto);
                if(m.count == 1) {
                    forgeItemsToRemove.add(m);
                } else {
                    m.count -= 1;
                }
            }
        }
        tempMaterial.setCenter(forgeSlots.get((forgePos - 1) + 4).getX() + 25, forgeSlots.get((forgePos - 1) + 4).getY() + 25);

        boolean countAdd = false;
        for(ItemStack item_ : smeltedMaterial) {
            if(item_.stackedItem.getSprite().getX() == tempMaterial.getSprite().getX()) {
                item_.count += 1;
                countAdd = true;
            }
        }
        if(!countAdd) {
            smeltedMaterial.add(new ItemStack(tempMaterial, 1));
        }

        for(ItemStack f : fuelItems) {
            if(f.stackedItem.fuelPos == forgePos) {
                if(f.count == 1) {
                    fuelItemsToRemove.add(f);
                } else {
                    f.count -= 1;
                }
            }
        }
    }
}
