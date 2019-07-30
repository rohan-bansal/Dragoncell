package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.GameUtils.ItemStack;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.ObtainMethods;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;

public class Inventory {

    private Player player;

    private ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    private ArrayList<ItemStack> materialsToRemove = new ArrayList<ItemStack>();
    private int slotHovered = 1;
    private int slotSelected = 1;
    private int slotToFill;
    private Sprite highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/invSlot2.jpg")));
    private Sprite select = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/invSlot3.jpg")));
    private SpriteBatch invBatch = new SpriteBatch();

    public ItemStack followMaterial = null;
    private int slotNumberForFollow = 0;
    private ItemStack hoveredMaterial = null;

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);

    private BitmapFont itemCounter = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont followItemCounter = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);

    GlyphLayout layout = new GlyphLayout();


    public Inventory(Player player) {
        this.player = player;

        int xPos = 625;
        int yPos = 680;
        int incr = 1;
        while(incr < 37) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/invSlot.jpg")));
            temp.setCenter(xPos, yPos);
            slots.add(temp);
            xPos += 60;
            if(xPos > 960) {
                xPos = 625;
                yPos -= 60;
            }
            incr++;
        }
        slotToFill = 1;

        itemCounter.getData().setScale(0.5f);

        nameDrawer.getData().setScale(2f);
        nameDrawer.setColor(Color.GOLD);


    }

    public void addItem(Material item, int... count) {
        Gdx.app.log("Inventory", item.name + " added to inventory");
        Material item_ = new Material(item);
        item_ = item.setVariables(item_);
        item_.setCenter(slots.get(slotToFill - 1).getX() + 25, slots.get(slotToFill - 1).getY() + 25);

        for(ItemStack item__ : inventory) {
            if(item__.stackedItem.name.equals(item.name)) {
                if(count.length > 0) {
                    item__.count += count[0];
                } else {
                    item__.addItem();
                }
                refreshInventory();
                return;
            }
        }
        if (slotToFill + 1 <= slots.size()) {
            slotToFill += 1;
        }

        if(count.length > 0) {
            item_.slotNumber = slotToFill - 1;
            inventory.add(new ItemStack(item_, count[0]));
        } else {
            item_.slotNumber = slotToFill - 1;
            inventory.add(new ItemStack(item_, 1));
        }

        refreshInventory();
    }

    public void removeItem() {

        try {
            ItemStack temp = inventory.get(slotSelected - 1);
            if(temp.count > 1) {
                inventory.get(inventory.indexOf(temp)).count -= 1;
                Gdx.app.log("Inventory", temp.stackedItem.name + " size reduced by 1");
            } else {
                inventory.get(slotSelected - 1).stackedItem.slotNumber = 0;
                inventory.remove(inventory.get(slotSelected - 1));
                Gdx.app.log("Inventory", temp.stackedItem.name + " removed from Inventory");
            }
        } catch (Exception e) {
        }


        refreshInventory();
    }

    public void dropBack() {
        if(slotNumberForFollow != 0 && followMaterial != null  && followMaterial.stackedItem.isFollowingMouse) {
            if(inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().getColor().a == 0) {
                inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().setAlpha(1);
                if(followMaterial.count > 1) {
                    inventory.get(slotNumberForFollow - 1).count += followMaterial.count - 1;
                }
                slotNumberForFollow = 0;
                Gdx.app.log("SLOT", "number reset");

            } else {
                inventory.get(slotNumberForFollow - 1).count += followMaterial.count;
                slotNumberForFollow = 0;
                Gdx.app.log("SLOT", "number reset");
            }

            followMaterial = null;
        }
    }


    public void render() {

        invBatch.begin();

        checkSlotSelected();
        renderInventory();

        if(followMaterial != null && followMaterial.stackedItem.isFollowingMouse) {
            if(followMaterial.count > 1) {
                itemCounter.draw(invBatch, followMaterial.count + "", followMaterial.stackedItem.getSprite().getX() + 26, followMaterial.stackedItem.getSprite().getY() + 8);
            }
            followMaterial.stackedItem.render(invBatch);
            followMaterial.stackedItem.setCenter(Gdx.input.getX(), 800 - Gdx.input.getY());
        }

        invBatch.end();

        for(ItemStack material : materialsToRemove) {
            inventory.remove(material);
        }

        materialsToRemove.clear();

    }

    private void renderInventory() {
        if(inventory != null && inventory.size() != 0) {
            for(ItemStack item : inventory) {
                item.stackedItem.render(invBatch);
                if(item.count > 1) {
                    itemCounter.draw(invBatch, item.count + "", item.stackedItem.getSprite().getX() + 26, item.stackedItem.getSprite().getY() + 8);
                }

                if(slotSelected == inventory.indexOf(item) + 1) {
                    layout.setText(nameDrawer, item.stackedItem.name);
                    nameDrawer.draw(invBatch, item.stackedItem.name, (775 - layout.width / 2), 345);
                }

                if(item.stackedItem.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    hoveredMaterial = item;

                } else {
                    hoveredMaterial = null;
                }
            }
        }
    }

    public void refreshInventory() {
        Gdx.app.log("Inventory", "Inventory Refreshed");
        slotToFill = 1;
        for(ItemStack item : inventory) {
            item.stackedItem.setCenter(slots.get(slotToFill - 1).getX() + 25, slots.get(slotToFill - 1).getY() + 25);
            item.stackedItem.slotNumber = slotToFill;

            slotToFill += 1;
        }
    }


    private void checkSlotSelected() {

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if(slotSelected - 1 != 0) slotSelected -= 1;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if(slots.size() != slotSelected) slotSelected += 1;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if(slotSelected + 6 <= slots.size()) slotSelected += 6;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if(slotSelected - 6 > 0) slotSelected -= 6;
        }

        for(Sprite slot : slots) {
            if (slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                slotHovered = slots.indexOf(slot) + 1;
                highlight.setPosition(slot.getX(), slot.getY());
                highlight.draw(invBatch);
                if(Gdx.input.justTouched()) {
                    slotSelected = slots.indexOf(slot) + 1;
                }
            } else {
                slot.draw(invBatch);
            }
        }
        if(slotSelected != 0) {
            select.setPosition(slots.get(slotSelected - 1).getX(), slots.get(slotSelected - 1).getY());
            select.draw(invBatch);
        }
    }

    public void leftDown() {
        if(followMaterial == null) {
            for(ItemStack item : inventory) {
                if(item.stackedItem.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if (item.count > 1) {
                        item.count -= 1;
                        followMaterial = new ItemStack(new Material(item.stackedItem.name, item.stackedItem.description, item.stackedItem.ID, item.stackedItem.rarity), 1);
                        followMaterial.stackedItem = item.stackedItem.setVariables(followMaterial.stackedItem);
                        followMaterial.stackedItem.isFollowingMouse = true;
                        slotNumberForFollow = item.stackedItem.slotNumber;
                    } else {
                        followMaterial = new ItemStack(new Material(item.stackedItem.name, item.stackedItem.description, item.stackedItem.ID, item.stackedItem.rarity), 1);
                        followMaterial.stackedItem = item.stackedItem.setVariables(followMaterial.stackedItem);
                        followMaterial.stackedItem.isFollowingMouse = true;
                        item.stackedItem.getSprite().setAlpha(0);
                        slotNumberForFollow = item.stackedItem.slotNumber;
                    }
                }
            }
        } else {
            for(ItemStack item : inventory) {
                if(item.stackedItem.getSprite().getBoundingRectangle().overlaps(followMaterial.stackedItem.getSprite().getBoundingRectangle())) {
                    if(item.stackedItem.name.equals(followMaterial.stackedItem.name)) {
                        if(item.stackedItem.getSprite().getColor().a != 0) {
                            if (item.count > 1) {
                                item.count -= 1;
                                followMaterial.count += 1;
                                slotNumberForFollow = item.stackedItem.slotNumber;
                                return;
                            } else {
                                followMaterial.count += 1;
                                item.stackedItem.getSprite().setAlpha(0);
                                slotNumberForFollow = item.stackedItem.slotNumber;
                                return;
                            }
                        }

                    }

                }
            }
            if(MainScreen.headsUp.craftingActive) {
                for(Sprite slot : MainScreen.crafting.craftingSlots) {
                    if (slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                        if(followMaterial.stackedItem.getSprite().getBoundingRectangle().overlaps(slot.getBoundingRectangle())) {
                            Gdx.app.log("Crafting", "Material attempted to drop into slot " + (MainScreen.crafting.craftingSlots.indexOf(slot) + 1));
                            if(!MainScreen.crafting.addToSlot(MainScreen.crafting.craftingSlots.indexOf(slot) + 1)) {
                                if(followMaterial.count == 1) {
                                    if(inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().getColor().a == 0) {
                                        inventory.remove(inventory.get(slotNumberForFollow - 1));
                                        slotNumberForFollow = 0;
                                        followMaterial = null;
                                    } else {
                                        slotNumberForFollow = 0;
                                        followMaterial = null;
                                    }
                                } else {
                                    followMaterial.count -= 1;
                                }

                            }
                            refreshInventory();
                            return;
                        }
                    }
                }
            }
            if(MainScreen.headsUp.forgeActive) {
                for(Sprite slot : MainScreen.forge.forgeSlots) {
                    if (slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                        if(followMaterial.stackedItem.getSprite().getBoundingRectangle().overlaps(slot.getBoundingRectangle())) {
                            Gdx.app.log("Forge", (MainScreen.forge.forgeSlots.indexOf(slot) + 1) + "");
                            if(!MainScreen.forge.addToForgeSlot(MainScreen.forge.forgeSlots.indexOf(slot) + 1)) {
                                if(inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().getColor().a == 0) {
                                    inventory.remove(inventory.get(slotNumberForFollow - 1));
                                    slotNumberForFollow = 0;
                                }
                            }
                            refreshInventory();
                            return;
                        }
                    }
                }
                for(Sprite slot : MainScreen.forge.fuelSlots) {
                    if (slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                        if(followMaterial.stackedItem.getSprite().getBoundingRectangle().overlaps(slot.getBoundingRectangle())) {
                            Gdx.app.log("ForgeFuel", "Material attempted to drop into slot " + (MainScreen.forge.fuelSlots.indexOf(slot) + 1));
                            if(!MainScreen.forge.addToFuelSlot(MainScreen.forge.fuelSlots.indexOf(slot) + 1)) {
                                if(inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().getColor().a == 0) {
                                    inventory.remove(inventory.get(slotNumberForFollow - 1));
                                    slotNumberForFollow = 0;
                                }
                            }
                            refreshInventory();
                            return;
                        }
                    }
                }
            }

            dropBack();
        }
    }
}
