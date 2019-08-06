package com.rohan.dragoncell.GameUtils.Entity.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BreakableObject {

    public Sprite sprite;
    public int ID;
    public boolean unobtainable = false;
    public int hits = 0;

    public BreakableObject(String spritePath, int ID) {
        this.ID = ID;
        sprite = new Sprite(new Texture(spritePath));
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
