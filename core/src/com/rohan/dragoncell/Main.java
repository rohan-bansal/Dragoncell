package com.rohan.dragoncell;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.rohan.dragoncell.GameScenes.AbstractScreen;
import com.rohan.dragoncell.GameScenes.TitleScreen;

public class Main extends Game {

	private long startTime;
	private SpriteBatch batch;
	private Sprite logo;

	private boolean titleScreenSet = false;

	@Override
	public void create () {
		Gdx.app.log("Main", "Loading Libraries: Program Launched");
		batch = new SpriteBatch();

		logo = new Sprite(new Texture(Gdx.files.internal("Interface/Title/logo.png")));
		logo.setCenter(500, 400);

		Pixmap pm = new Pixmap(Gdx.files.internal("Interface/World/pointer.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		pm.dispose();

		startTime = TimeUtils.millis();
	}

	@Override
	public void render () {
		super.render();
		if(TimeUtils.millis() - startTime <= 1000) {
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			batch.begin();
			logo.draw(batch);
			batch.end();
		} else {
			if(!titleScreenSet) {
				AbstractScreen title = new TitleScreen(this);
				title.buildStage();
				this.setScreen(title);
				//this.setScreen(new TitleScreen(this));
				titleScreenSet = true;
			}
		}
	}
	
	@Override
	public void dispose () {
	}
}
