package com.mygdx.game.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.pantallas.PantallaMenu;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.red.HiloCliente;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Config;

public class EntradasPjUno implements InputProcessor{

	private boolean abajo = false, arriba = false, derecha = false, izquierda = false; 
	public boolean enter = false; 
	public boolean ataque = false;

	
	PantallaNivelUno app;
	
	private Ninja player;

	private Ninja player1;
	private int nroPlayer = 1;
	private Ninja player2;
	
	private HiloCliente hc;
	

	public EntradasPjUno() {
		this.hc = hc;
		
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
		
		if(nroPlayer == 1) {
			if(keycode == Keys.W) {
				arriba = true;
			}
			
			if(keycode == Keys.D) {
				derecha = true; 
			} else if(keycode == Keys.A) {
				izquierda = true; 
			}
		
		}
		
		
		
		
		if(keycode == Keys.ENTER) {
			enter = true;
		}	

		
		
		if(keycode == Keys.SPACE) {
			ataque = true;
		}
		
		return false;
	}

	
	@Override
	public boolean keyUp(int keycode) {
		app.tiempo = 0.08f;
		
		if(keycode == Keys.W) {
			arriba = false;
			hc.enviaerMensaje("DejeApretarArriba");
		} 
		
		if(keycode == Keys.D) {
			derecha = false; 
			hc.enviaerMensaje("DejeApretarDerecha");
		} else if(keycode == Keys.A) {
			izquierda = false; 
			hc.enviaerMensaje("DejeApretarIzquierda");
		}
		
		if(keycode == Keys.ENTER) {
			enter = false;
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
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
	


}
