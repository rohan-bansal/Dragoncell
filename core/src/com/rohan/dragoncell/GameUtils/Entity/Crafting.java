package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.FileUtils.Tuple;
import com.rohan.dragoncell.GameUtils.Display.HUD;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;
import com.rohan.dragoncell.GameUtils.ObtainMethods;
import com.rohan.dragoncell.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Crafting {

    private Inventory inventory;
    private MaterialsList materials;

    public ArrayList<Sprite> craftingSlots = new ArrayList<Sprite>();
    private ArrayList<Material> craftingItems = new ArrayList<Material>();
    private ArrayList<Material> craftingItemsToRemove = new ArrayList<Material>();
    private HashMap<Integer, Tuple<Integer, Integer>> slotPositions = new HashMap<Integer, Tuple<Integer, Integer>>();
    private Sprite highlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot2.png")));
    private Sprite outputFail = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_fail.png")));
    private Sprite outputSuccess = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_success.png")));
    private boolean outputSucc = false;
    private Material craft = null;

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont rarityDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont IDDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont descriptionDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);

    private Sprite next, back, next_high, back_high;
    GlyphLayout layout = new GlyphLayout();

    private int IDpage = 2;
    private Material shownMat;

    public Crafting(Inventory inventory, MaterialsList materials) {
        this.inventory = inventory;
        this.materials = materials;

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
        outputSuccess.setCenter(280, 100);

        next = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_right.png")));
        next_high = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_right_highlight.png")));

        back = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_left.png")));
        back_high = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/arrow_left_highlight.png")));

        next.setCenter(450, 730);
        next_high.setCenter(450, 730);

        back.setCenter(125, 730);
        back_high.setCenter(125, 730);

        initDrawers();
    }

    private void initDrawers() {
        nameDrawer.setColor(Color.GOLDENROD);

        descriptionDrawer.getData().setScale(0.5f);
        descriptionDrawer.setColor(Color.TAN);

        IDDrawer.setColor(Color.BLACK);
        IDDrawer.getData().setScale(0.5f);

        rarityDrawer.getData().setScale(0.5f);

        refreshMaterialShown();
    }

    private void refreshMaterialShown() {
        for(Material material : materials.materialList) {
            if(material.discovered) {

            }
            if(material.ID == IDpage) {
                shownMat = new Material(material.name, material.description, material.ID, material.rarity);
                shownMat.getSprite().setScale(2f);
                shownMat.setCenter(170, 650);
            }
        }
    }

    public void clearGrid() {
        for(Material material : craftingItems) {
            inventory.addItem(material);
            craftingItemsToRemove.add(material);
        }
    }

    public void craft() {
        ArrayList<Material> craftables = new ArrayList<Material>();
        for(Material material : materials.materialList) {
            if(material.canBeCrafted) {
                craftables.add(material);
            }
        }

        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<String> temp2 = new ArrayList<String>();

        for(Material n : craftingItems) {
            temp.add(n.name);
        }

        for(Material m : craftables) {
            temp2.clear();
            for(Material x : m.recipe) {
                temp2.add(x.name);
            }
            if (temp2.size() == temp.size()) {
                if (temp.containsAll(temp2) && temp2.containsAll(temp)) {
                    Gdx.app.log("Crafting", "Crafting Option: " + m.name);
                    craft = new Material(m.name, m.description, m.ID, m.rarity);
                    break;
                }
            }
        }

        if(craft != null) {
            outputSucc = true;
            craft.setCenter(outputSuccess.getX() + 25, outputSuccess.getY() + 25);
            craftingItems.clear();
        }
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

        if(!outputSucc) {
            outputFail.draw(batch);
        } else {
            outputSuccess.draw(batch);
        }

        if(craft != null) {
            craft.render(batch);
            if(craft.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                if(Gdx.input.justTouched()) {
                    inventory.addItem(craft);
                    craft = null;
                    outputSucc = false;
                }
            }
        }

        drawMaterialsBook(batch);

        craftingItemsToRemove.clear();
    }

    private void drawMaterialsBook(SpriteBatch batch) {
        shownMat.render(batch);

        layout.setText(nameDrawer, shownMat.name);
        nameDrawer.draw(batch, shownMat.name, (330 - layout.width / 2), 700);

        layout.setText(descriptionDrawer, shownMat.description);
        descriptionDrawer.draw(batch, shownMat.description, (290 - layout.width / 2), 570);

        layout.setText(IDDrawer, "ID: " + shownMat.ID);
        IDDrawer.draw(batch, "ID: " + shownMat.ID + "", (180 - layout.width / 2), 610);

        layout.setText(rarityDrawer, ObtainMethods.rarities.get(shownMat.rarity));
        switch(shownMat.rarity) {
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
        rarityDrawer.draw(batch, ObtainMethods.rarities.get(shownMat.rarity), (330 - layout.width / 2), 650);

        if(IDpage != materials.materialList.size() - 1) {
            if (next.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                next_high.draw(batch);
                if(Gdx.input.justTouched()) {
                    IDpage += 1;
                    refreshMaterialShown();
                }
            } else {
                next.draw(batch);
            }
        }
        if(IDpage != 1) {
            if (back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                back_high.draw(batch);
                if(Gdx.input.justTouched()) {
                    IDpage -= 1;
                    refreshMaterialShown();
                }
            } else {
                back.draw(batch);
            }
        }
    }
}
