package com.aniilo.game.screens;

import com.aniilo.game.SamuraiGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	private static final int EXIT_BUTTON_WIDTH  = 250;   //ANCHO DEL BOTON DE SALIDA
	private static final int EXIT_BUTTON_HEIGHT = 120;  //ALTURA DEL BOTON DE SALIDA
	private static final int PLAY_BUTTON_WIDTH  = 230;  //ANCHODEL BOTON DE JUGAR
	private static final int PLAY_BUTTON_HEIGHT = 100;  //ALTURA DEL BOTON DE JUGAR
	private static final int EXIT_BUTTON_Y = 100; //PARA QUE NO SE SOBREPONGAN LOS BOTONES 
	private static final int PLAY_BUTTON_Y = 250; 
	private Texture texture;
	SamuraiGame game; 
	
	Texture playButtonActive; 
	Texture playButtonInactive; 
	Texture exitButtonActive; 
	Texture exitButtonInactive; 
	public MainMenuScreen (SamuraiGame game) {
		this.game = game; 
		playButtonActive = new Texture ("play_button_active.png"); 
		playButtonInactive = new Texture ("play_button_inactive.png"); 
		exitButtonActive = new Texture ("exit_button_active.png"); 
		exitButtonInactive = new Texture ("exit_button_inactive_2.png"); 
		
	} 
	
	@Override
	public void show() {
		texture = new Texture(Gdx.files.internal("fondoia.png"));
	}

	@Override
	public void render(float delta) {
		
		//                ROJO  VERDE  AZUL
		ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		game.batch.begin();
		game.batch.draw(texture ,0 ,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );
		
		int x = SamuraiGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 ; //ACTIVA Y DESACTIVA EL BOTON DE SALIDA
		if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && SamuraiGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && Gdx.input.getY() > EXIT_BUTTON_Y  ) 
		{
			game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y ,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		} else { 
			game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y ,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}

		x = SamuraiGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 ; //ACTIVA Y DESACTIVA EL BOTON DE JUGAR
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && SamuraiGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.input.getY() > PLAY_BUTTON_Y  ) 
		{
			game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y ,PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new MainGameScreen(game));
			}
		} else { 
			game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y ,PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}

		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
	
	}

	@Override
	public void dispose() {
		
	}

}
