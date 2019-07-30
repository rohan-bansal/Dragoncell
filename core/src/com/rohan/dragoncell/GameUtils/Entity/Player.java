package com.rohan.dragoncell.GameUtils.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private Inventory inventory;

    public int hearts = 20;
    public int health = 20;
    public int coins = 0;

    private float stateTime;
    private boolean flip;

    private Texture walkSheet;
    public TextureRegion currentFrame;
    private String horidirection = "left";
    private Animation<TextureRegion> walkAnim;
    public Vector2 position;
    private int animState;

    public Player() {

        inventory = new Inventory(this);

        position = new Vector2(285, 260);

        walkSheet = new Texture(Gdx.files.internal("Character/character_right.png"));
        TextureRegion[][] walkTMP = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / 3,
                walkSheet.getHeight() / 1);

        TextureRegion[] walkFrames = new TextureRegion[3 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                walkFrames[index++] = walkTMP[i][j];
            }
        }
        walkAnim = new Animation<TextureRegion>(0.3f, walkFrames);
        walkAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


    }

    public Inventory getInventory() {
        return inventory;
    }


    public void renderInventory() {

        inventory.render();
    }

    public void renderPlayer(SpriteBatch batch) {

        checkMove();

        currentFrame = walkAnim.getKeyFrame(stateTime, true);
        flip = horidirection.equals("left");

        batch.draw(currentFrame, flip ? position.x + currentFrame.getRegionWidth() : position.x, position.y, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    private void checkMove() {

        if(Gdx.input.isKeyPressed(Input.Keys.W) && position.y < 440) {
            stateTime += Gdx.graphics.getDeltaTime();
            position.y += 1.5;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.A) && position.x > 40) {
            stateTime += Gdx.graphics.getDeltaTime();
            horidirection = "left";
            position.x -= 1.5;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.S) && position.y > 30) {
            stateTime += Gdx.graphics.getDeltaTime();
            position.y -= 1.5;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.D) && position.x < 500) {
            stateTime += Gdx.graphics.getDeltaTime();
            horidirection = "right";
            position.x += 1.5;
        }
    }
}
