package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MainScreen implements Screen {

    private Game game;
    private boolean loadData = false;

    public MainScreen(Game game, boolean loadData) {
        this.game = game;
        this.loadData = loadData;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 27/255f, 26/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


    }



    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
