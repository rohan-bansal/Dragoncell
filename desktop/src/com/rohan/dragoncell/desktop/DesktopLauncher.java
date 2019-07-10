package com.rohan.dragoncell.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rohan.dragoncell.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.title = "The DragonCell";
		config.addIcon("Interface/Title/icon.png", Files.FileType.Internal);
		config.width = 1000;
		config.height = 800;

		new LwjglApplication(new Main(), config);
	}
}
