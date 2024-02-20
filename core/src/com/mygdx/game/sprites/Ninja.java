package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Juego;
import com.mygdx.game.pantallas.PantallaNivelUno;

public class Ninja extends Sprite {
	
	public World mun;
	public Body b2body;

	 private Fixture swordFixture;
	
	public enum State {CALLENDO, CAMINANDO, SALTANDO, PARADO, ATACANDO};
	public State estadoActual;
	public State estadoAnterrior;
	private TextureRegion ninjaParado;
	private Animation<TextureRegion> ninjaCaminar;
	private Animation<TextureRegion>  ninjaSalto;
	private Animation<TextureRegion>  ninjaAtaque;
	
	
	private float timerEstado;
	
	private boolean correrDerecha; 
	
	public Ninja( PantallaNivelUno screen) {
		super(screen.getAtlas().findRegion("ninja"));
		this.mun = screen.getMundo();
		estadoActual = State.PARADO;
		estadoAnterrior = State.PARADO;
		timerEstado = 0;
		correrDerecha = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 5 ; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,2 , 20, 40));
		}
		ninjaCaminar = new Animation<TextureRegion>(0.1f,frames);
		frames.clear();
		
		for(int i = 11 ; i < 16; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,2 , 20, 40));
		}
		ninjaSalto = new Animation<TextureRegion>(0.1f,frames);
		
		
		for(int i = 16 ; i < 19; i++) {
			frames.add(new TextureRegion(getTexture(), i * 16 ,2 , 20, 40));
		}
		ninjaAtaque = new Animation<TextureRegion>(0.1f,frames);
		
		defineNinja();
		ninjaParado  = new TextureRegion(getTexture(), 2, 2, 20, 40);
		
		
		setBounds(2, 2, 20 / Juego.PPM, 40 / Juego.PPM);
		setRegion(ninjaParado);
		

	}

	
	public void update (float dt) { 
		
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2.5f);
		
		setRegion(getFrame(dt)); 
	}
	
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
		
		if((b2body.getLinearVelocity().x < 0 || !correrDerecha) && !region.isFlipX()) {
			region.flip(true, false);
			correrDerecha = false;
		} else if ((b2body.getLinearVelocity().x > 0 || correrDerecha) && region.isFlipX()) {
			region.flip(true, false);
			correrDerecha = true;
		}
		
		timerEstado = estadoActual == estadoAnterrior ? timerEstado + dt : 0;
		estadoAnterrior = estadoActual;
		return region;
	} 
	
	public State getState() {
		if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && estadoAnterrior == State.SALTANDO)) {
			return State.SALTANDO;
		} else if(b2body.getLinearVelocity().y < 0) {
			return State.CALLENDO;
		} else if(b2body.getLinearVelocity().x != 0) {
			return State.CAMINANDO;
		} else {
			return State.PARADO;
		}
		
	}
	
	public void defineNinja() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(195 / Juego.PPM , 40 / Juego.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = mun.createBody(bdef);
		
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 /Juego.PPM );
		fdef.filter.categoryBits = Juego.NINJA_ESPADA_BIT;
		fdef.filter.categoryBits = Juego.PISO_BIT | Juego.ENEMIGO_BIT | Juego.ENEMIGO_CUERPO_BIT;
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		


//   b2body.createFixture(swordFixtureDef);
//
//	    swordShape.dispose();
//	
	
	}
	
}
