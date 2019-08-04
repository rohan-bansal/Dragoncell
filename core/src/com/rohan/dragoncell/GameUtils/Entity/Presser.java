package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.rohan.dragoncell.FileUtils.GifDecoder;
import com.rohan.dragoncell.GameUtils.ItemStack;
import com.rohan.dragoncell.GameUtils.Material;
import com.rohan.dragoncell.GameUtils.MaterialsList;
import com.rohan.dragoncell.GameUtils.ObtainMethods;

import java.util.ArrayList;

public class Presser {

    private Player player;
    private MaterialsList lis_;
    private Inventory inventory;

    private ItemStack bladeFuel = null;
    private ItemStack toPressItem = null;
    private ItemStack finishedPress = null;

    private float smeltTime = 20f;
    private boolean pressSucc = false;
    private float stateTime = 0f;

    private BitmapFont itemCounter = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont timeDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private GlyphLayout layout = new GlyphLayout();
    private Animation<TextureRegion> smeltAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("Interface/HUD/processing.gif").read());

    private ArrayList<String> fuels = new ArrayList<String>() {{
        add("Small Blade");
    }};

    public Sprite presserFuel, presserItem, presserFinish, presserFuelHigh, presserItemHigh, presserFinishHigh, presserIcon;

    public Presser(Player player, MaterialsList materials) {
        this.player = player;
        this.lis_ = materials;
        this.inventory = player.getInventory();

        itemCounter.getData().setScale(0.5f);
        timeDrawer.getData().setScale(0.5f);

        presserFuel = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/fuelSlot.png")));
        presserFuelHigh = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/fuelSlot2.png")));
        presserItem = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot.png")));
        presserItemHigh = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingSlot2.png")));
        presserFinish = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_fail.png")));
        presserFinishHigh = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/output_success.png")));
        presserIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/juicing.png")));

        presserFuel.setCenter(150, 260);
        presserFuelHigh.setCenter(150, 260);
        presserItem.setCenter(220, 260);
        presserItemHigh.setCenter(220, 260);
        presserFinish.setCenter(350, 260);
        presserFinishHigh.setCenter(350, 260);
        presserIcon.setCenter(285, 300);

    }

    public boolean dropIntoFuelSlot() {
        ItemStack temp = new ItemStack(new Material(inventory.followMaterial.stackedItem), inventory.followMaterial.count);
        temp.stackedItem.setCenter(presserFuel.getX() + 20, presserFuel.getY() + 20);

        if(!fuels.contains(temp.stackedItem.name)) {
            return true;
        }


        if(bladeFuel != null) {
            return true;
        } else {
            bladeFuel = temp;
            Gdx.app.log("Presser", "Fuel Drop Successful");
            inventory.followMaterial = null;
            smeltTime = ObtainMethods.presserFuel.get(bladeFuel.stackedItem.name);

            return false;
        }

    }

    public boolean dropIntoItemSlot() {
        ItemStack temp = new ItemStack(new Material(inventory.followMaterial.stackedItem), inventory.followMaterial.count);
        temp.stackedItem.setCenter(presserItem.getX() + 25, presserItem.getY() + 25);

        if(!ObtainMethods.juiceable.contains(temp.stackedItem.name.toLowerCase())) {
            return true;
        }
/*       if(temp.stackedItem.getSprite().getY() < 250) {
            return true;
        }*/

        if(toPressItem != null) {
            return true;
        } else {
            toPressItem = temp;
            Gdx.app.log("Presser", "Press Drop Successful");
            inventory.followMaterial = null;
            return false;
        }
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        if(presserFuel.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            presserFuelHigh.draw(batch);
        } else {
            presserFuel.draw(batch);
        }
        if(presserItem.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            presserItemHigh.draw(batch);
        } else {
            presserItem.draw(batch);
        }
        presserIcon.draw(batch);

        if(pressSucc) {
            if(finishedPress != null) {
                presserFinishHigh.draw(batch);
                finishedPress.stackedItem.render(batch);
                if(finishedPress.stackedItem.getSprite().getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                    if(Gdx.input.justTouched()) {
                        inventory.addItem(finishedPress.stackedItem, finishedPress.count);
                        finishedPress = null;
                        pressSucc = false;
                    }
                }
            }

        } else {
            presserFinish.draw(batch);
        }

        if(toPressItem != null) {
            toPressItem.stackedItem.render(batch);
            if(toPressItem.count > 1) {
                itemCounter.draw(batch, toPressItem.count + "", toPressItem.stackedItem.getSprite().getX() + 26, toPressItem.stackedItem.getSprite().getY() + 8);
            }
        }
        if(bladeFuel != null) {
            bladeFuel.stackedItem.render(batch);
            if(bladeFuel.count > 1) {
                itemCounter.draw(batch, bladeFuel.count + "", bladeFuel.stackedItem.getSprite().getX() + 26, bladeFuel.stackedItem.getSprite().getY() + 8);
            }
        }

        if(toPressItem != null && bladeFuel != null) {
            batch.draw(smeltAnimation.getKeyFrame(stateTime), presserIcon.getX(), presserIcon.getY() - 50);
            layout.setText(timeDrawer, ObtainMethods.round((double) smeltTime, 1) + "");
            timeDrawer.draw(batch, ObtainMethods.round((double) smeltTime, 1) + "",
                    presserIcon.getX() + 10, presserIcon.getY() - 70);
            if(finishedPress != null) {
                if(finishedPress.count > 1) {
                    itemCounter.draw(batch, finishedPress.count + "", finishedPress.stackedItem.getSprite().getX() + 26, finishedPress.stackedItem.getSprite().getY() + 8);
                }
            }
            threadTime();

        }
    }

    public void threadTime() {
        smeltTime -= 0.015f;

        if(smeltTime <= 0) {
            if(toPressItem.count > 1) {
                smeltTime = ObtainMethods.presserFuel.get(bladeFuel.stackedItem.name);
            } else {
                smeltTime = 20f;
            }
            pressItem();
        }
    }

    private void pressItem() {
        if(finishedPress != null) {
            finishedPress.count += 1;
        } else {
            finishedPress = new ItemStack(lis_.getMaterialByID(ObtainMethods.juicedInto.get(toPressItem.stackedItem.name)), 1);
        }
        finishedPress.stackedItem.setCenter(presserFinish.getX() + 25, presserFinish.getY() + 25);
        pressSucc = true;

        if(bladeFuel.count > 1) {
            bladeFuel.count -= 1;
        } else {
            bladeFuel = null;
        }

        if(toPressItem.count > 1) {
            toPressItem.count -= 1;
        } else {
            toPressItem = null;
        }
    }

    public void clearGrid() {
        if(toPressItem != null) {
            inventory.addItem(toPressItem.stackedItem, toPressItem.count);
        }
        if(bladeFuel != null) {
            inventory.addItem(bladeFuel.stackedItem, bladeFuel.count);
        }

        toPressItem = null;
        bladeFuel = null;
    }
}
