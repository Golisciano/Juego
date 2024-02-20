package com.mygdx.game.elementos;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Juego;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja;


public class MundoContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		switch (cDef) {
			case Juego.ENEMIGO_CUERPO_BIT | Juego.NINJA_BIT :
				if(fixA.getFilterData().categoryBits == Juego.ENEMIGO_CUERPO_BIT) {
					((Enemigo)fixA.getUserData()).hitEnCuerpo((Ninja) fixB.getUserData());
				}else {
					((Enemigo)fixB.getUserData()).hitEnCuerpo((Ninja) fixA.getUserData());
				}
				break;

			case Juego.ENEMIGO_BIT | Juego.OBJETO_BIT :
				if(fixA.getFilterData().categoryBits == Juego.ENEMIGO_BIT) {
					((Enemigo)fixA.getUserData()).reverseVelocidad(true, false);
				}else {
					((Enemigo)fixB.getUserData()).reverseVelocidad(true, false);
				}
				break;
			
			case Juego.ENEMIGO_BIT | Juego.ENEMIGO_BIT :
				((Enemigo)fixA.getUserData()).reverseVelocidad(true, false);
				((Enemigo)fixB.getUserData()).reverseVelocidad(true, false);
				break;
		}
	} 

	@Override
	public void endContact(Contact contact) {
//		Gdx.app.log("Final", "");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		
	}

}
