package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.dragoncell.GameUtils.Display.HUD;
import com.rohan.dragoncell.GameUtils.Entity.Player;
import com.rohan.dragoncell.GameUtils.MaterialsList;

public class MainScreen implements Screen {

    private Game game;
    private MaterialsList materials;
    private Player player;
    private SpriteBatch mainBatch = new SpriteBatch();
    private HUD headsUp;


    private boolean loadData = false;


    public MainScreen(Game game, boolean loadData) {
        this.game = game;
        this.loadData = loadData;

        initMaterials();
        initPlayer();
    }

    private void initMaterials() {
        materials = new MaterialsList();
        Gdx.app.log("World", "Materials and Recipes Loaded");

    }

    private void initPlayer() {
        player = new Player();
        headsUp = new HUD(player);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 27/255f, 26/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        mainBatch.begin();
        mainBatch.end();

        headsUp.render(delta);

    }



    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
