package edu.lewisu.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.lewisu.game.TravelByMap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Medellin City";
		//config.width = 1000;
		//config.height = 500;
		new LwjglApplication(new TravelByMap(), config);
	}
}
