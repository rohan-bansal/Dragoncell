package com.rohan.dragoncell.GameUtils.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.GameUtils.Entity.Player;

public class HUD {

    private Player player;
    private SpriteBatch batch = new SpriteBatch();

    private Texture heart, emptyHeart, inventory, chest, crafting_, recipeBook, forge_;
    private Sprite craftingIcon, clearIcon, materialsBookIcon, forgeIcon, clearIconHighlight, finishIcon, finishIconHighlight;
    private boolean clearIconActive = true;
    private boolean finishIconActive = true;
    public boolean craftingActive = true;
    public boolean forgeActive = false;
    public boolean matBookActive = true;


    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont inventory_ = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont crafting = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont recipe_ = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);

    public HUD(Player player) {
        this.player = player;

        heart = new Texture(Gdx.files.internal("Interface/HUD/heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Interface/HUD/background.png"));
        inventory = new Texture(Gdx.files.internal("Interface/HUD/inventory.png"));
        chest = new Texture(Gdx.files.internal("Interface/HUD/chestInventory.png"));
        crafting_ = new Texture(Gdx.files.internal("Interface/HUD/workbench_view.png"));
        forge_ = new Texture(Gdx.files.internal("Interface/HUD/forge_view.png"));
        recipeBook = new Texture(Gdx.files.internal("Interface/HUD/recipeBook.png"));
        craftingIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingIcon.png")));
        clearIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear.png")));
        forgeIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/forgeIcon.png")));
        materialsBookIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/materialsBookIcon.png")));
        clearIconHighlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear_highlight.png")));
        finishIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/finish.png")));
        finishIconHighlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/finish_highlight.png")));

        craftingIcon.setPosition(549, 400);
        forgeIcon.setPosition(549, 370);
        materialsBookIcon.setPosition(549,430);

        inventory_.setColor(Color.TAN);
        inventory_.getData().setScale(2);

        crafting.setColor(Color.TAN);
        crafting.getData().setScale(2);

        recipe_.setColor(Color.TAN);
        recipe_.getData().setScale(1f);
    }

    public void render(float delta) {

        batch.begin();

        batch.draw(inventory, 570, 10);
        craftingIcon.draw(batch);
        forgeIcon.draw(batch);
        materialsBookIcon.draw(batch);

        checkClicks();

        if(matBookActive) {
            batch.draw(recipeBook, 80, 520);
            MainScreen.materialsBook.render(batch);

            layout.setText(recipe_, "Material Book");
            recipe_.draw(batch, "Material Book", 80 + (recipeBook.getWidth() / 2) - layout.width / 2, 770);
        } else {
            for(int x = 0; x < player.hearts; x++) {
                if(player.health == player.hearts) {
                    batch.draw(heart, 20 * x + 5, 770);
                } else {
                    if(x > player.health - 1) {
                        batch.draw(emptyHeart, 20 * x + 5, 770);
                    } else {
                        batch.draw(heart, 20 * x + 5, 770);
                    }
                }
            }
        }

        if(craftingActive) {
            batch.draw(crafting_, 20, 10);
            MainScreen.crafting.render(batch);

            clearIcon.setCenter(480, 260);
            clearIconHighlight.setCenter(480, 260);

            finishIcon.setCenter(480, 200);
            finishIconHighlight.setCenter(480, 200);

            layout.setText(crafting, "Workbench");
            crafting.draw(batch, "Workbench", 20 + (crafting_.getWidth() / 2) - layout.width / 2, 480);
        } else if(forgeActive) {
            batch.draw(forge_, 20, 10);
            MainScreen.forge.render(batch);

            clearIcon.setCenter(480, 280);
            clearIconHighlight.setCenter(480, 280);

            layout.setText(crafting, "Forge");
            crafting.draw(batch, "Forge", 20 + (forge_.getWidth() / 2) - layout.width / 2, 480);
        }

        if(clearIconActive) {
            if (clearIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                clearIconHighlight.draw(batch);
                if (Gdx.input.justTouched()) {
                    MainScreen.crafting.clearGrid();
                }
            } else {
                clearIcon.draw(batch);
            }
        }
        if(finishIconActive) {
            if(finishIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                finishIconHighlight.draw(batch);
                if(Gdx.input.justTouched()) {
                    MainScreen.crafting.craft();
                }
            } else {
                finishIcon.draw(batch);
            }
        }

        layout.setText(inventory_, "Inventory");
        inventory_.draw(batch, "Inventory", 570 + (inventory.getWidth() / 2) - layout.width / 2, 770);

        batch.end();

    }

    private void checkClicks() {
        if(craftingIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            if(Gdx.input.justTouched()) {
                if(!craftingActive) {
                    craftingActive = true;
                    forgeActive = false;
                    clearIconActive = true;
                    finishIconActive = true;
                } else {
                    craftingActive = false;
                    clearIconActive = false;
                    finishIconActive = false;
                }
            }
        } else if(materialsBookIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            if(Gdx.input.justTouched()) {
                if(!matBookActive) {
                    matBookActive = true;
                } else {
                    matBookActive = false;
                }
            }
        } else if(forgeIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
            if(Gdx.input.justTouched()) {
                if(!forgeActive) {
                    forgeActive = true;
                    craftingActive = false;
                    clearIconActive = false;
                    finishIconActive = false;
                } else {
                    forgeActive = false;
                    clearIconActive = false;
                }
            }
        }
    }
}
