package com.rohan.dragoncell.FileUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rohan.dragoncell.GameScenes.MainScreen;

public class MyTextInputListener implements Input.TextInputListener {

    @Override
    public void input(String text) {
        MainScreen.materialsBook.setpageID(text);
    }

    @Override
    public void canceled() {

    }
}
