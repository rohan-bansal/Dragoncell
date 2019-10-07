package com.rohan.dragoncell.GameUtils.Entity.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.rohan.dragoncell.FileUtils.Tuple;
import com.rohan.dragoncell.GameScenes.MainScreen;
import com.rohan.dragoncell.Main;

import java.util.Random;

public class PassiveMob {

    protected Tuple<Integer, Integer> worldCoords;
    protected Random random = new Random();
    protected Animation<TextureRegion> walkAnim;
    public Vector2 position;
    public String name;

    public TextureRegion currentFrame;
    private float stateTime = 0f;
    public int animState = 1;
    private boolean flip;
    private boolean directionLeft = true;
    private boolean moveDiagonal = false;
    private boolean stop = false;
    private float stopCounter = 10f;
    private float lengthOfWalk = 3f;


    public PassiveMob(Tuple<Integer, Integer> coordinates, String name) {
        worldCoords = coordinates;
        this.name = name;
    }

    public Rectangle getRect() {
        return new Rectangle(position.x, position.y, walkAnim.getKeyFrame(0).getRegionWidth(), walkAnim.getKeyFrame(0).getRegionHeight());
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        flip = directionLeft;

        if(!new Rectangle(20, 10, MainScreen.headsUp.collection_.getWidth(), MainScreen.headsUp.collection_.getHeight()).contains(getRect())) {
            stop = false;
            animState = 1;
            lengthOfWalk = 10f + random.nextFloat() * (20f - 10f);
            if(directionLeft) {
                directionLeft = false;
                moveDiagonal = false;
            } else {
                directionLeft = true;
                moveDiagonal = false;
            }
            if(position.y <= 30) {
                directionLeft = false;
                moveDiagonal = true;
            } else if(position.y >= 460) {
                directionLeft = true;
                moveDiagonal = true;
            }
        }

        if(lengthOfWalk > 0) {
            lengthOfWalk -= 0.2f;
            if(flip) {
                position.x -= 0.2;
                if(moveDiagonal) {
                    position.y -= 0.2f;
                }
            } else {
                position.x += 0.2;
                if(moveDiagonal) {
                    position.y += 0.2f;
                }
            }
        } else {
            if(stop) {
                animState = 0;
                stopCounter -= 0.1f;
                if(stopCounter <= 0) {
                    stopCounter = 10f;
                    stop = false;
                }
            } else {
                animState = 1;
                lengthOfWalk = 10f + random.nextFloat() * (20f - 10f);
                directionLeft = random.nextBoolean();
                moveDiagonal = random.nextBoolean();
                stop = random.nextBoolean();
            }

        }

        if(animState == 1) {
            currentFrame = walkAnim.getKeyFrame(stateTime, true);
        } else {
            currentFrame = walkAnim.getKeyFrame(0);
        }

        batch.draw(currentFrame, flip ? position.x + currentFrame.getRegionWidth() : position.x, position.y, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight());


    }
}
