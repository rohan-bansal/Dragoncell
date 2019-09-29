package com.rohan.dragoncell.GameUtils.Entity.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.rohan.dragoncell.FileUtils.Tuple;

import java.util.Random;

public class PassiveMob {

    protected Tuple<Integer, Integer> worldCoords;
    protected Random random = new Random();
    protected Animation<TextureRegion> walkAnim;
    protected Vector2 position;


    public PassiveMob(Tuple<Integer, Integer> coordinates) {
        worldCoords = coordinates;

    }

    public void render(SpriteBatch batch) {
    }
}
