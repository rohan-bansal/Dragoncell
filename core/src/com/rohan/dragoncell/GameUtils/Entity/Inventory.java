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

    public Material followMaterial = null;
    private int slotNumberForFollow = 0;
    private ItemStack hoveredMaterial = null;

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont rarityDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont descriptionDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont itemCounter = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
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

        descriptionDrawer.getData().setScale(0.5f);
        descriptionDrawer.setColor(Color.TAN);

        rarityDrawer.getData().setScale(0.5f);
    }

    public void addItem(Material item, int... count) {
        Gdx.app.log("Inventory", item.name + " added to inventory");
        item.setCenter(slots.get(slotToFill - 1).getX() + 25, slots.get(slotToFill - 1).getY() + 25);

        for(ItemStack item_ : inventory) {
            if(item_.stackedItem.name.equals(item.name)) {
                item_.addItem();
                refreshInventory();
                return;
            }
        }
        if (slotToFill + 1 <= slots.size()) {
            slotToFill += 1;
        }

        if(count.length > 0) {
            item.slotNumber = slotToFill - 1;
            inventory.add(new ItemStack(item, count[0]));
        } else {
            item.slotNumber = slotToFill - 1;
            inventory.add(new ItemStack(item, 1));
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
        if(slotNumberForFollow != 0 && followMaterial != null  && followMaterial.isFollowingMouse) {
            if(inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().getColor().a == 0) {
                inventory.get(slotNumberForFollow - 1).stackedItem.getSprite().setAlpha(1);
                slotNumberForFollow = 0;
            } else {
                inventory.get(slotNumberForFollow - 1).count += 1;
                slotNumberForFollow = 0;
            }

            followMaterial = null;
        }
    }


    public void render() {

        invBatch.begin();

        checkSlotSelected();
        renderInventory();

        if(followMaterial != null && followMaterial.isFollowingMouse) {
            followMaterial.render(invBatch);
            followMaterial.setCenter(Gdx.input.getX(), 800 - Gdx.input.getY());
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

                    layout.setText(descriptionDrawer, item.stackedItem.description);
                    descriptionDrawer.draw(invBatch, item.stackedItem.description, (775 - layout.width / 2), 280);

                    layout.setText(rarityDrawer, ObtainMethods.rarities.get(item.stackedItem.rarity));
                    switch(item.stackedItem.rarity) {
                        case 1:
                            rarityDrawer.setColor(Color.LIME); //common
                            break;
                        case 2:
                            rarityDrawer.setColor(Color.OLIVE); //uncommon
                            break;
                        case 3:
                            rarityDrawer.setColor(Color.BLUE); //rare
                            break;
                        case 4:
                            rarityDrawer.setColor(Color.MAROON); //epic
                            break;
                        case 5:
                            rarityDrawer.setColor(Color.ORANGE); //legendary
                            break;
                        case 6:
                            rarityDrawer.setColor(Color.SCARLET); //mystic
                            break;
                    }
                    rarityDrawer.draw(invBatch, ObtainMethods.rarities.get(item.stackedItem.rarity), (775 - layout.width / 2), 250);
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
                        followMaterial = new Material(item.stackedItem.name, item.stackedItem.description, item.stackedItem.ID, item.stackedItem.rarity);
                        followMaterial.isFollowingMouse = true;
                        slotNumberForFollow = item.stackedItem.slotNumber;
                    } else {
                        followMaterial = new Material(item.stackedItem.name, item.stackedItem.description, item.stackedItem.ID, item.stackedItem.rarity);
                        followMaterial.isFollowingMouse = true;
                        item.stackedItem.getSprite().setAlpha(0);
                        slotNumberForFollow = item.stackedItem.slotNumber;
                    }
                }
            }
        } else {
            for(Sprite slot : MainScreen.crafting.craftingSlots) {
                if (slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(followMaterial.getSprite().getBoundingRectangle().overlaps(slot.getBoundingRectangle())) {
                        Gdx.app.log("Crafting", "Material attempted to drop into slot " + (MainScreen.crafting.craftingSlots.indexOf(slot) + 1));
                        if(!MainScreen.crafting.addToSlot(MainScreen.crafting.craftingSlots.indexOf(slot) + 1)) {
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
            dropBack();
        }
    }
}
