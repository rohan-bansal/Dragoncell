package com.rohan.dragoncell.GameUtils.Entity.Mob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.rohan.dragoncell.FileUtils.Tuple;

import java.util.Random;

public class Cow extends PassiveMob {


    private Animation<TextureRegion> walkAnim;
    private Sprite sprite;
    private Texture walkSheet;
    private float stateTime = 0f;
    private Vector2 position = new Vector2();
    private String name;

    public Cow(Tuple<Integer, Integer> coordinates, String name) {
        super(coordinates, name);

        this.name = name;

        worldCoords = coordinates;

        position.x = random.nextInt((460 - 40) + 1) + 40;
        position.y = random.nextInt((400 - 30) + 1) + 30;

        walkSheet = new Texture(Gdx.files.internal("Entities/Cow/cow_right.png"));
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

        currentFrame = walkAnim.getKeyFrame(stateTime, true);

        super.walkAnim = walkAnim;
        super.position = position;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
