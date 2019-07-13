package com.rohan.dragoncell.GameScenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rohan.dragoncell.FileUtils.SoundEffects;


public class TitleScreen extends AbstractScreen {

    private Game game;

    private Image title, play, play_glow, load, load_glow, about, about_glow;
    private SoundEffects music = new SoundEffects();

    public TitleScreen(Game game) {
        this.game = game;
        Gdx.app.log("Title", "Set title screen");

        title = new Image(new Texture(Gdx.files.internal("Interface/Title/dragoncell.png")));
        play = new Image(new Texture(Gdx.files.internal("Interface/Title/play.png")));
        play_glow = new Image(new Texture(Gdx.files.internal("Interface/Title/play_glow.png")));
        load = new Image(new Texture(Gdx.files.internal("Interface/Title/load-game.png")));
        load_glow = new Image(new Texture(Gdx.files.internal("Interface/Title/load-game_glow.png")));
        about = new Image(new Texture(Gdx.files.internal("Interface/Title/about.png")));
        about_glow = new Image(new Texture(Gdx.files.internal("Interface/Title/about_glow.png")));

        music.loadTrack("Music/afterlife.mp3", true);

        title.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 5/6, Align.center);
        play.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3, Align.center);
        play_glow.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 3, Align.center);
        load.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 5, Align.center);
        load_glow.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 5, Align.center);
        about.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8, Align.center);
        about_glow.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8 , Align.center);

        music.play();
    }


    @Override
    public void buildStage() {
        addActor(title);
        title.addAction(Actions.sequence(Actions.moveTo(title.getX(), title.getY() - 6, 1f), Actions.moveTo(title.getX(), title.getY(), 1f)));

        addActor(play);
        addActor(load);
        addActor(about);
        addActor(play_glow);
        addActor(load_glow);
        addActor(about_glow);

        play_glow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainScreen(game, false));
                event.handle();
                return true;
            }
        });
        load.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainScreen(game, true));
                event.handle();
                return true;
            }
        });
        about.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new AboutScreen(game));
                event.handle();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (title.getActions().size == 0) {
            title.addAction(Actions.sequence(Actions.moveTo(title.getX(), title.getY() - 6, 1f), Actions.moveTo(title.getX(), title.getY(), 1f)));
        }
        checkHover();
    }

    private void checkHover() {
        Vector2 mouseScreenPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector2 playlocalPos = play.screenToLocalCoordinates(mouseScreenPosition);

        //Vector2 aboutlocalPos = about.screenToLocalCoordinates(mouseScreenPosition);
        //Vector2 loadlocalPos = load.screenToLocalCoordinates(mouseScreenPosition);

        if(play.hit(playlocalPos.x, playlocalPos.y, false) != null) {
            play_glow.setVisible(true);
        //} else if(about.hit(aboutlocalPos.x, aboutlocalPos.y, false) != null) {
        //    about_glow.setVisible(true);
        //} else if(load.hit(loadlocalPos.x, loadlocalPos.y, false) != null) {
        //    load_glow.setVisible(true);
        } else {
            play_glow.setVisible(false);
            load_glow.setVisible(false);
            about_glow.setVisible(false);

        }
    }
}
