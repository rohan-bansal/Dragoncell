package com.rohan.dragoncell.GameUtils.Entity.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameUtils.Material;

public class BreakableObject {

    public Sprite sprite;
    public int ID;
    public boolean unobtainable = false;
    public int hits = 0;
    public Material material;

    public BreakableObject(String spritePath, int ID, Material... material) {
        this.ID = ID;
        sprite = new Sprite(new Texture(spritePath));

        if(material.length > 0) {
            this.material = material[0];
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
