package com.mygdx.game.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Config;

public class EntradasPjDos implements InputProcessor{

	private boolean abajo = false, arriba = false, derecha = false, izquierda = false; 
	public boolean enter = false; 
	public boolean ataque = false;
	
	PantallaNivelUno app;
	
	private Ninja player;

	
	public EntradasPjDos(PantallaNivelUno app) {
		this.app = app;
	}

	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}

	public boolean isEnter() {
		return enter;
	}
	
	public boolean isDerecha() {
		return derecha;
	}
	
	public boolean isIzquierda() {
		return izquierda;
	}
	
	
	public boolean isAtaque() {
		return ataque;
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		app.tiempo = 0.08f;
		
		
		if(keycode == Keys.UP) {
			arriba = true;
		} else if(keycode == Keys.DOWN) {
			 abajo = true; 
		}
		
		if(keycode == Keys.ENTER) {
			enter = true;
		}	
		
		if(keycode == Keys.RIGHT) {
			derecha = true; 
		} else if(keycode == Keys.LEFT) {
			izquierda = true; 
		}
		
		
		if(keycode == Keys.SPACE) {
			ataque = true;
		}
		
		return false;
	}

	
	@Override
	public boolean keyUp(int keycode) {
		app.tiempo = 0.08f;
		
		if(keycode == Keys.UP) {
			arriba = false;
		} else if(keycode == Keys.DOWN) {
			 abajo = false; 
		}
		
		if(keycode == Keys.ENTER) {
			enter = false;
		}	
		
		if(keycode == Keys.RIGHT) {
			derecha = false; 
		} else if(keycode == Keys.LEFT) {
			izquierda = false; 
		}

		if(keycode == Keys.SPACE) {
			ataque = false;
		}
		
		return false;
	}

	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}



	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	

}
