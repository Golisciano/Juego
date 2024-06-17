package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Juego;
import com.mygdx.game.elementos.Hud;
import com.mygdx.game.pantallas.PantallaNivelUno;


public class Arquero  extends Enemigo{

	private float stateTime;
	private Animation<TextureRegion> caminarAnimcaion;
	private Animation<TextureRegion> morirAnimacion;
	private Array <TextureRegion> frames;
	private Array <TextureRegion> frame;
	public State estadoActual;
	public State estadoAnterrior;
	private boolean setMuerto;
	public boolean muerto;
	
	private float timerEstado;
	
	private int salud;
	
	public enum State {CAMINANDO, PARADO, ATACANDO};
	private TextureRegion arqueroParado;
	private Animation<TextureRegion> arqueroCaminar;
	private Animation<TextureRegion>  arqueroAtaque;
	
	public boolean caminarDerecha;

	public float proximoX,proximoY;
	
	public Arquero(PantallaNivelUno screen, float x, float y) {
		super(screen, x, y);
		salud = 1;
		estadoActual = State.PARADO;
		estadoAnterrior = State.PARADO;
		proximoX=x;
		proximoY=y;
		
		frames = new Array <TextureRegion>();
		
		for(int i = 0 ; i < 3 ; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("arqu"), i * 16, 2, 32, 55));
		}
		
		caminarAnimcaion = new Animation<TextureRegion>(0.5f, frames);
		stateTime = 0;
		setBounds(getX(), getY(), 32 / Juego.PPM, 60/ Juego.PPM);
		setMuerto = false;
		muerto = false;

		
	}

	public void update(float dt) {
		stateTime += dt;
		if(setMuerto && !muerto) {
			mundo.destroyBody(b2body);
			muerto = true; 	
			for(int i = 4 ; i < 8 ; i++ ) {
				setRegion(new TextureRegion(screen.getAtlas().findRegion("arqu"), i * 16, 2, 46, 40 ));
			}
			morirAnimacion = new Animation<TextureRegion>(0.5f, frames);	
			stateTime = 0;


		} else if(!muerto) {

			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2.5f);
			setRegion(caminarAnimcaion.getKeyFrame(stateTime , true));
		}

	}

	        @Override
	protected void defineEnemigo() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = mundo.createBody(bdef);
		
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 /Juego.PPM );
		fdef.filter.categoryBits = Juego.ENEMIGO_BIT;
		fdef.filter.maskBits = Juego.PISO_BIT |
				Juego.NINJA_BIT |
				Juego.NINJA_ESPADA_BIT |
				Juego.OBJETO_BIT |
				Juego.ENEMIGO_BIT;
		
		
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);
		
		PolygonShape cuerpo = new PolygonShape();
		Vector2[] vertice = new Vector2[4];
		vertice[0] = new Vector2(-5, 8).scl(1 / Juego.PPM);
		vertice[1] = new Vector2(5, 8).scl(1 / Juego.PPM);
		vertice[2] = new Vector2(-3, 3).scl(1 / Juego.PPM);
		vertice[3] = new Vector2(3, 3).scl(1 / Juego.PPM);
		cuerpo.set(vertice);
		
		fdef.shape = cuerpo;
		fdef.restitution = 0.5f;
		fdef.filter.categoryBits = Juego.ENEMIGO_CUERPO_BIT;
		b2body.createFixture(fdef).setUserData(this);
	}
	        
	public void draw(Batch batch) {
		if(getTexture() == null) return;
		if(!muerto || stateTime < 1 ) {
			super.draw(batch);
		}
	}        

	@Override
	public void hitEnCuerpo(Ninja ninja) {
		setMuerto = true;
	}

}
