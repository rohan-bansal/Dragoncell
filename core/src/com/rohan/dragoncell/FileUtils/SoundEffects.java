package com.rohan.dragoncell.FileUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundEffects {

    private Music music;

    public SoundEffects() {
    }

    public void loadTrack(String track, boolean looping) {
        music = Gdx.audio.newMusic(Gdx.files.internal(track));
        if(looping) {
            music.setLooping(true);
        } else {
            music.setLooping(false);
        }
    }

    public void play() {
        music.play();
    }

    public void pause() {
        music.pause();
    }

    public void stop() {
        music.stop();
    }
}
