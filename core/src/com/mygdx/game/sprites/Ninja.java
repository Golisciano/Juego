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
import com.mygdx.game.elementos.Hud;
import com.mygdx.game.pantallas.PantallaFin;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.utiles.Render;

public class Ninja extends Sprite {
	
	public World mun;
	public Body b2body;


	private float nroPlayer;
	

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
