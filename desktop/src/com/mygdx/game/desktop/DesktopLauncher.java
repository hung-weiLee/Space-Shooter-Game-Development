package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.AIEngine.RunAIEngine;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
public static void main (String[] arg) {

	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.title= MyGdxGame.TITLE;
	config.width= MyGdxGame.WIDTH;
	config.height= MyGdxGame.HEIGHT;
	config.y = 0;
	config.forceExit = true;
	new LwjglApplication(new MyGdxGame(), config);
    }
}
