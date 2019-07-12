package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.FileUtils.Tuple;
import com.rohan.dragoncell.GameUtils.Display.HUD;
import com.rohan.dragoncell.GameUtils.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class Crafting {

    private Player player;
    private Inventory inventory;
    private HUD hud;

    public ArrayList<Sprite> craftingSlots = new ArrayList<Sprite>();
    private ArrayList<Material> craftingItems = new ArrayList<Material>();
    private ArrayList<Material> craftingItemsToRemove = new ArrayList<Material>();
    private HashMap<Integer, Tuple<Integer, Integer>> slotPositions = new HashMap<Integer, Tuple<Integer, Integer>>();
    private Sprite highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot2.png")));
    private Sprite outputFail = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_fail.png")));
    private Sprite outputSuccess = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_success.png")));


    public Crafting(Player player, Inventory inventory, HUD hud) {
        this.player = player;
        this.inventory = inventory;
        this.hud = hud;

        int xPos = 190;
        int yPos = 350;
        int incr = 1;
        while(incr < 17) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot.png")));
            temp.setCenter(xPos, yPos);
            craftingSlots.add(temp);
            xPos += 60;
            if(xPos > 400) {
                xPos = 190;
                yPos -= 60;
            }
            incr++;
        }

        for(Sprite slot : craftingSlots) {
            slotPositions.put(craftingSlots.indexOf(slot) + 1, new Tuple<Integer, Integer>(Math.round(slot.getX() + 25), Math.round(slot.getY() + 25)));
        }

        outputFail.setCenter(280, 100);
    }

    public void clearGrid() {
        for(Material material : craftingItems) {
            inventory.addItem(material);
            craftingItemsToRemove.add(material);
        }
    }

    public void craft() {

    }

    public boolean addToSlot(int slot) {
        Material temp = new Material(inventory.followMaterial.name, inventory.followMaterial.description, inventory.followMaterial.ID, inventory.followMaterial.rarity);
        temp.setCenter(slotPositions.get(slot).x, slotPositions.get(slot).y);

        for(Material material : craftingItems) {
            if(material.getSprite().getX() == temp.getSprite().getX() && material.getSprite().getY() == temp.getSprite().getY()) {
                Gdx.app.log("Crafting", "Drop Failed");
                return true;
            }
        }
        Gdx.app.log("Crafting", "Drop Successful");
        craftingItems.add(temp);
        inventory.followMaterial = null;
        return false;
    }

    public void render(SpriteBatch batch) {

        for(Sprite slot : craftingSlots) {
            if(slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                highlight.setPosition(slot.getX(), slot.getY());
                highlight.draw(batch);
            } else {
                slot.draw(batch);
            }
        }

        for(Material material : craftingItems) {
            material.render(batch);
        }

        for(Material m_ : craftingItemsToRemove) {
            craftingItems.remove(m_);
        }

        outputFail.draw(batch);

        craftingItemsToRemove.clear();
    }
}
