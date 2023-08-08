package com.aniilo.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.aniilo.game.SamuraiGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); //AJUSTE DE FPS
		config.setWindowedMode(1200, 700); //AJUSTE DE ANCHO Y ALTURA DE LA PANTALLA
		config.setResizable(false);  //DESABILITA LA OPCION  AL JUGADOR DE EXPANDIR O ACHICAR LA PATALLA 
		config.setTitle("game");
		new Lwjgl3Application(new SamuraiGame(), config);
	}
}
