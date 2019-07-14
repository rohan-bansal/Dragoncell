package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;

import java.util.ArrayList;

public class Forge {

    private Inventory inventory;
    private MaterialsList materials;

    public ArrayList<Sprite> forgeSlots = new ArrayList<Sprite>();
    public ArrayList<Sprite> fuelSlots = new ArrayList<Sprite>();
    private ArrayList<Material> forgeItems = new ArrayList<Material>();
    private ArrayList<Material> forgeItemsToRemove = new ArrayList<Material>();

    private Sprite highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot2.png")));
    private Sprite highlight_fuel = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/fuelSlot2.png")));

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
    }

    public void clearGrid() {

    }

    public void render(SpriteBatch batch) {
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
    }
}
