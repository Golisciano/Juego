package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.utiles.Render;

public class Juego extends Game {
	
	public static final int V_WITDH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;


	public static final short PISO_BIT = 1;
	public static final short NINJA_BIT = 2;
	public static final short NINJA_ESPADA_BIT = 4;
	public static final short ENEMIGO_BIT = 6;
	public static final short ENEMIGO_CUERPO_BIT = 8;
	public static final short OBJETO_BIT = 16;
	
	@Override
	public void create () {

		Render.app = this;
		Render.batch = new SpriteBatch();
		this.setScreen(new PantallaNivelUno(this));


	}

	


	
	private void update() {

	}


	@Override
	public void dispose () {
		super.dispose();
		Render.batch.dispose();
	
		
	}

	@Override
	public void render () {
		super.render();

		
	}
	
}
