package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Juego;
import com.mygdx.game.elementos.Hud;
import com.mygdx.game.pantallas.PantallaNivelUno;

public class Ninja2 extends Sprite {

	public World mun;
	public Body b2body;

	 private Fixture swordFixture;

	public enum State {CALLENDO, CAMINANDO, SALTANDO, PARADO, ATACANDO, MUERTO};
	public State estadoActual;
	public State estadoAnterrior;
	private TextureRegion ninjaParado;
	private Animation<TextureRegion> ninjaMuerto;
	private Animation<TextureRegion> ninjaCaminar;
	private Animation<TextureRegion>  ninjaSalto;
	private Animation<TextureRegion>  ninjaAtaque;


	private float timerEstado;

	private boolean correrDerecha;
	public boolean ninjaEstaMuerto;

	private float nroPlayer;

	public Ninja2(int nroPlayer, PantallaNivelUno screen) {
		super(screen.getAtlas().findRegion("ninja"));
		this.mun = screen.getMundo();
		this.nroPlayer = nroPlayer;
		estadoActual = State.PARADO;
		estadoAnterrior = State.PARADO;
		timerEstado = 0;
		correrDerecha = true;
		
		//busca en el png del personaje y lo va moviendo
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 5 ; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,134 , 20, 40));
		}
		ninjaCaminar = new Animation<TextureRegion>(0.1f,frames);
		frames.clear();
		
		for(int i = 12 ; i < 17; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,134 , 20, 40));
		}
		ninjaSalto = new Animation<TextureRegion>(0.21f,frames);
		
		for(int i = 19 ; i < 23; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,134 , 20, 40));
		}
		ninjaMuerto = new Animation<TextureRegion>(0.21f,frames);
		
		for(int i = 16 ; i < 19; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,134 , 20, 40));
		}
		ninjaAtaque = new Animation<TextureRegion>(0.1f,frames);
		
		defineNinja();
		ninjaParado  = new TextureRegion(getTexture(), 2, 134, 20, 40);
		
		
		setBounds(2, 2, 20 / Juego.PPM, 40 / Juego.PPM);
		setRegion(ninjaParado);
		

	}

	//actualiza la posicion del jugador
	public void update (float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2.5f);
		setRegion(getFrame(dt));
	}
	
	
	//busca el frame del estado que esta realizando el personaje
	public TextureRegion getFrame(float dt) {
		estadoActual = getState();
		
		TextureRegion region;
		switch (estadoActual) {
		case SALTANDO:
			region = ninjaSalto.getKeyFrame(timerEstado);
			break;
		case CAMINANDO: 
			region = ninjaCaminar.getKeyFrame(timerEstado, true);
			break;
		case ATACANDO: 
			region = ninjaAtaque.getKeyFrame(timerEstado);
			break;
		case MUERTO: 
			region = ninjaMuerto.getKeyFrame(timerEstado);
			break;
		case CALLENDO: 
			region = ninjaParado;
			break;
		case PARADO:
			region = ninjaParado;
			break;
	
		default:
			region = ninjaParado;
			break;
		}
		
		//modifica para que lado esta viendo el pj
		if((b2body.getLinearVelocity().x < 0 || !correrDerecha) && !region.isFlipX()) {
			region.flip(region.isFlipX() ? false : false, false);
			correrDerecha = false;
		} else if ((b2body.getLinearVelocity().x > 0 || correrDerecha) && region.isFlipX()) {
			region.flip(region.isFlipX() ? true : true, false);
			correrDerecha = true;
		}
		
		timerEstado = estadoActual == estadoAnterrior ? timerEstado + dt : 0;
		estadoAnterrior = estadoActual;
		return region;
	} 
	
	//aumenta la velocidad de la accion
	public State getState() {
		if (ninjaEstaMuerto) {
			return State.MUERTO;
		} else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && estadoAnterrior == State.SALTANDO)) {
			return State.SALTANDO;
		} else if(b2body.getLinearVelocity().y < 0) {
			return State.CALLENDO;
		} else if(b2body.getLinearVelocity().x != 0) {
			return State.CAMINANDO;
		} else {
			return State.PARADO;
		}	
	}
	
	
	public void golpe(Enemigo enemigo) {
		
		//muestra el estado del pj cuando se queda sin vida
		if(Hud.vida == 0) { 
			Array<TextureRegion> frames = new Array<TextureRegion>();
			ninjaEstaMuerto = true; 
			for(int i = 19 ; i < 23; i++) {
				frames.add(new TextureRegion(getTexture(), i * 16 ,134 , 20, 40));
			}
			ninjaMuerto = new Animation<TextureRegion>(0.21f,frames);
		}
	}
	
	//define el "cuerpo" del pj
	public void defineNinja() {
		if(nroPlayer==1) {
			BodyDef bdef = new BodyDef();
			bdef.position.set(195 / Juego.PPM , 40 / Juego.PPM);
			bdef.type = BodyDef.BodyType.DynamicBody;
			b2body = mun.createBody(bdef);
		} else {
			BodyDef bdef = new BodyDef();
			bdef.position.set(180 / Juego.PPM , 40 / Juego.PPM);
			bdef.type = BodyDef.BodyType.DynamicBody;
			b2body = mun.createBody(bdef);
		}
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 /Juego.PPM );
		fdef.filter.categoryBits = Juego.NINJA_BIT;
		fdef.filter.maskBits = Juego.PISO_BIT |
				Juego.ENEMIGO_BIT |
				Juego.OBJETO_BIT |
				Juego.ENEMIGO_CUERPO_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);



	}
	
}
