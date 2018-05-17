package com.mygdx.spacewar.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.spacewar.SpaceWar;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                // Заголовок окна игры
                config.title = "Space";
                // Ширина и высота окна
                config.width = 800;
                config.height = 450;
                // Запрещаем растягивать/сжимать окно
                config.resizable = false;
		new LwjglApplication(new SpaceWar(), config);
	}
}
