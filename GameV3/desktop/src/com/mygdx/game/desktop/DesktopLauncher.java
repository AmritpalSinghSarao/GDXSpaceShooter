package com.mygdx.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		GameMain gameMain = new GameMain();
		config.width= gameMain.width;
		config.height= gameMain.height;
		config.title= gameMain.title;
		new LwjglApplication(gameMain, config);

	}
}
