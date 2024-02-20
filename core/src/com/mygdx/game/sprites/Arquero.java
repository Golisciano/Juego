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
import com.mygdx.game.pantallas.PantallaNivelUno;

public class Arquero  extends Enemigo{

	private float stateTime;
	private Animation<TextureRegion> caminarAnimcaion;
	private Array <TextureRegion> frames;
	private boolean setMuerto;
	private boolean muerto;
	
	private int salud;
	
	public Arquero(PantallaNivelUno screen, float x, float y) {
		super(screen, x, y);
		salud = 1;
		frames = new Array <TextureRegion>();
		for(int i = 2 ; i < 5 ; i++) {
			frames.add(new TextureRegion(screen.getAtlas().findRegion("arquero"), i * 16, 2, 20, 40));
		
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
					setRegion(new TextureRegion(screen.getAtlas().findRegion("arquero"), 30, 2, 20, 40 ));
					stateTime = 0;
			
		} else if(!muerto) {
			b2body.setLinearVelocity(velocidad);
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2.5f);
			setRegion(caminarAnimcaion.getKeyFrame(stateTime , true));
		}

	}


//	   public void receiveDamage(int damage) {
//	        salud -= damage;
//	        if (salud <= 0) {
//	        	System.out.println("muerto");
//	        }
//	   }
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
		if(!muerto || stateTime < 1 ) {
			super.draw(batch);
		}
	}        

	@Override
	public void hitEnCuerpo(Ninja ninja) {
		setMuerto = true;
	}

}
