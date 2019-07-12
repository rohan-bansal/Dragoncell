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

    private Texture heart, emptyHeart, inventory, chest, crafting_;
    private Sprite craftingIcon, clearIcon, clearIconHighlight, finishIcon, finishIconHighlight;
    private boolean clearIconActive = true;
    public boolean craftingActive = true;

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont inventory_ = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont crafting = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);

    public HUD(Player player) {
        this.player = player;

        heart = new Texture(Gdx.files.internal("Interface/HUD/heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Interface/HUD/background.png"));
        inventory = new Texture(Gdx.files.internal("Interface/HUD/inventory.png"));
        chest = new Texture(Gdx.files.internal("Interface/HUD/chestInventory.png"));
        crafting_ = new Texture(Gdx.files.internal("Interface/HUD/gameView.png"));
        craftingIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/craftingIcon.png")));
        clearIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear.png")));
        clearIconHighlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/clear_highlight.png")));
        finishIcon = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/finish.png")));
        finishIconHighlight = new Sprite(new Texture(Gdx.files.internal("Interface/HUD/finish_highlight.png")));

        craftingIcon.setPosition(549, 400);

        clearIcon.setCenter(480, 260);
        clearIconHighlight.setCenter(480, 260);

        finishIcon.setCenter(480, 200);
        finishIconHighlight.setCenter(480, 200);

        inventory_.setColor(Color.TAN);
        inventory_.getData().setScale(2);

        crafting.setColor(Color.TAN);
        crafting.getData().setScale(2);
    }

    public void render(float delta) {

        batch.begin();
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

        batch.draw(inventory, 570, 10);
        craftingIcon.draw(batch);

        checkClicks();

        if(craftingActive) {
            batch.draw(crafting_, 20, 10);
            MainScreen.crafting.render(batch);

            layout.setText(crafting, "Workbench");
            crafting.draw(batch, "Workbench", 20 + (crafting_.getWidth() / 2) - layout.width / 2, 480);
        } else {
        }

        if(clearIconActive) {
            if(clearIcon.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                clearIconHighlight.draw(batch);
                if(Gdx.input.justTouched()) {
                    MainScreen.crafting.clearGrid();
                }
            } else {
                clearIcon.draw(batch);
            }
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
                    clearIconActive = true;
                } else {
                    craftingActive = false;
                    clearIconActive = false;
                }
            }
        }
    }
}
