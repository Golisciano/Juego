package com.aniilo.game;

import com.aniilo.game.screens.MainGameScreen;
import com.aniilo.game.screens.MainMenuScreen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class SamuraiGame extends Game {
	
	public static final int WIDTH = 1200; //ANCHO DE LA PANTALLA
	public static final int HEIGHT = 700; // ALTO DE LA PANTALLA 
	public SpriteBatch batch;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	
	@Override
	public void dispose () {
		
	}
}
