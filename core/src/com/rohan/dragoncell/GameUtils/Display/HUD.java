package com.rohan.dragoncell.GameUtils.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameUtils.Entity.Player;

public class HUD {

    private Player player;
    private SpriteBatch batch = new SpriteBatch();

    private Texture heart, emptyHeart, inventory, chest;

    private GlyphLayout layout = new GlyphLayout();
    private BitmapFont inventory_ = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);

    public HUD(Player player) {
        this.player = player;

        heart = new Texture(Gdx.files.internal("Interface/HUD/heart.png"));
        emptyHeart = new Texture(Gdx.files.internal("Interface/HUD/background.png"));
        inventory = new Texture(Gdx.files.internal("Interface/HUD/inventory.png"));
        chest = new Texture(Gdx.files.internal("Interface/HUD/chestInventory.png"));
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

        layout.setText(inventory_, "Inventory");
        inventory_.setColor(Color.TAN);
        inventory_.getData().setScale(2);
        inventory_.draw(batch, "Inventory", 570 + (inventory.getWidth() / 2) - layout.width / 2, 770);

        batch.end();

    }
}
