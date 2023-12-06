package com.aniilo.game;

import com.aniilo.game.screens.MainGameScreen;
import com.aniilo.game.screens.MainMenuScreen;
import com.aniilo.game.world.GameMap;
import com.aniilo.game.world.TiledGameMap;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class SamuraiGame extends Game {
	

	
	public static final int WIDTH = 1200; //ANCHO DE LA PANTALLA
	public static final int HEIGHT = 700; // ALTO DE LA PANTALLA 
	public SpriteBatch batch;
	
	private OrthographicCamera cam;
	private StretchViewport puntoVista;
	GameMap gameMap;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
		puntoVista = new StretchViewport(WIDTH, HEIGHT, cam); 
		puntoVista.apply();
		cam.position.set(WIDTH /2, HEIGHT /2, 0);
		cam.update();
				
		this.setScreen(new MainMenuScreen(this));
}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined);
		super.render();
	
	}
	
	@Override
	public void resize(int width, int height) {
		puntoVista.update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		
	}
}
