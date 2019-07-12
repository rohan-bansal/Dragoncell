package com.rohan.dragoncell.GameUtils.Display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class ViewCam {

    public OrthographicCamera camera;


    public ViewCam() {

        camera = new OrthographicCamera(1000, 800);


    }

    public void render() {

        camera.update();

    }

}
