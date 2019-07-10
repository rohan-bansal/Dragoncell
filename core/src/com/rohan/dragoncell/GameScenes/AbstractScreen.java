package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public abstract class AbstractScreen extends Stage implements Screen {

    private ShapeRenderer renderer = new ShapeRenderer();

    protected AbstractScreen() {
        super(new StretchViewport(1000, 800));
    }

    public abstract void buildStage();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(144/255f, 141/255f, 140/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Color(100/255f, 30/255f, 22/255f, 0),
                new Color(100/255f, 30/255f, 22/255f, 0), Color.DARK_GRAY, Color.DARK_GRAY);
        renderer.end();

        super.act(delta);
        super.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void resize(int width, int height) {}
}
