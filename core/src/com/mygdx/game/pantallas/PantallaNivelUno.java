package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Juego;
import com.mygdx.game.elementos.B2CreadorMundo;
import com.mygdx.game.elementos.Hud;
import com.mygdx.game.elementos.MundoContactListener;
import com.mygdx.game.io.EntradasPjDos;
import com.mygdx.game.io.EntradasPjUno;
import com.mygdx.game.red.HiloCliente;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaNivelUno implements Screen {
	

	private Juego game;
	
	SpriteBatch b;
	
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderer;
	
	private TextureAtlas atlas;
	private OrthographicCamera cam;
	private Viewport portJuego;
	
	
	EntradasPjUno entradasUno = new EntradasPjUno();


	public float tiempo = 0; 

	private Hud hud;
	
	
	private World mundo;
	private Box2DDebugRenderer b2dr;
	private B2CreadorMundo creador;
	

	private Ninja player1;
	private int nroPlayer = 0;
	private Ninja player2;
	

	protected Fixture fixture;

	private HiloCliente hc;
	
	public PantallaNivelUno(Juego game) {
		atlas = new TextureAtlas("personajes/Persj.pack");
		hc = new HiloCliente();
		hc.start();
		this.game = game;
		b = Render.batch;
		
		Gdx.input.setInputProcessor(entradasUno);

		
		
		cam = new OrthographicCamera();
		
		portJuego = new FitViewport(Juego.V_WITDH / Juego.PPM, Juego.V_HEIGHT/ Juego.PPM, cam);
		
		hud = new Hud(b);
		
		mapa = new TmxMapLoader().load(Recursos.FONDONIVELDOS);
		renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);
		
		
		cam.position.set(portJuego.getWorldWidth() / 2 , portJuego.getWorldHeight() / 2, 0);
		
		
		mundo = new World(new Vector2(0, -10), true);
		
		b2dr = new Box2DDebugRenderer();
		

		creador = new B2CreadorMundo(this);
		
		player1 = new Ninja();
		player2 = new Ninja();
		
		mundo.setContactListener(new MundoContactListener());

	}
	
	@Override
	public void show() {
		
	}

	public void handleInput(float dt) {
		
		if(entradasUno.isArriba()) {
			hc.enviaerMensaje("ApreteArriba");
		} else {
			
		}
		
		if(entradasUno.isAbajo()) {
			hc.enviaerMensaje("ApreteAbajo");
		}
		
		if(entradasUno.isDerecha()) {
			hc.enviaerMensaje("ApreteDerecha");
		}
		
		if(entradasUno.isIzquierda()) {
			hc.enviaerMensaje("ApreteIzquierda");
		}
//		if(nroPlayer==0) {
//			if((entradasUno.isArriba())&&(player1.b2body.getLinearVelocity().y <= 2)) {
//				player1.b2body.applyLinearImpulse(new Vector2(0 , 2f ), player1.b2body.getWorldCenter(), true);
//			}
//
//			if((entradasUno.isDerecha())&&(player1.b2body.getLinearVelocity().x <= 4)){
//				player1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player1.b2body.getWorldCenter(), true);
//			}
//			if((entradasUno.isIzquierda())&&(player1.b2body.getLinearVelocity().x >= -7)){
//				player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player1.b2body.getWorldCenter(), true);
//			}
//			
//			if(entradasUno.isAtaque()) {
//				   PolygonShape swordShape = new PolygonShape();
//				    swordShape.setAsBox(15/ Juego.PPM, 0);
//
//				    FixtureDef swordFixtureDef = new FixtureDef();
//				    swordFixtureDef.shape = swordShape;
//				    swordFixtureDef.isSensor = false;
//
//				    player1.b2body.createFixture(swordFixtureDef).setUserData("espada");
//			}
//		} else if(nroPlayer == 1)  {
//			
//			if((entradasUno.isArriba())&&(player2.b2body.getLinearVelocity().y <= 2)) {
//				player2.b2body.applyLinearImpulse(new Vector2(0 , 2f ), player2.b2body.getWorldCenter(), true);
//			}
//
//			if((entradasUno.isDerecha())&&(player1.b2body.getLinearVelocity().x <= 4)){
//				player2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player2.b2body.getWorldCenter(), true);
//			}
//			if((entradasUno.isIzquierda())&&(player1.b2body.getLinearVelocity().x >= -7)){
//				player2.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player2.b2body.getWorldCenter(), true);
//			}
//			
//			if(entradasUno.isAtaque()) {
//				   PolygonShape swordShape = new PolygonShape();
//				    swordShape.setAsBox(15/ Juego.PPM, 0);
//
//				    FixtureDef swordFixtureDef = new FixtureDef();
//				    swordFixtureDef.shape = swordShape;
//				    swordFixtureDef.isSensor = false;
//
//				    player2.b2body.createFixture(swordFixtureDef).setUserData("espada");
//			}
//		}
		
	}
	
	
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void update(float dt) {
		
		handleInput(dt);
		
		mundo.step(1/60f, 6 ,4);
//		
//		player1.update(dt);
//		player2.update(dt);
		
		for (Enemigo enemigo : creador.getArquero()) {
			enemigo.update(dt);
		}

		
		hud.update(dt);
		
		
		cam.position.x = player1.b2body.getPosition().x;
		cam.position.x = player2.b2body.getPosition().x;
		
		cam.update();
		
		renderer.setView(cam);

	
	}
	
	@Override
	public void render(float delta) {
		

		update(delta);
		
		Render.limpiarPantalla(0, 0, 0);
		
		renderer.render();
		
		b.setProjectionMatrix(hud.escena.getCamera().combined);
		hud.escena.draw();
		cam.update();
		b.setProjectionMatrix(cam.combined);
		
	
		renderer.setView(cam);

		
		b2dr.render(mundo, cam.combined);
		
		
		b.setProjectionMatrix(cam.combined);
		b.begin();
		player1.draw(b);
		player2.draw(b);
		for (Enemigo enemigo : creador.getArquero()) {
			enemigo.draw(b);
			if(enemigo.getX() < player1.getX() + 224 / Juego.PPM) {
				enemigo.b2body.setActive(true);
			}
		}
//		arquero.draw(b);
		b.end();
		

	}

	@Override
	public void resize(int width, int height) {
		portJuego.update(width, height);
	}


	public TiledMap getMapa() {
		return mapa;
	}
	
	public World getMundo() {
		return mundo;
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
		mapa.dispose();
		renderer.dispose();
		mundo.dispose();
		b2dr.dispose();
		hud.dispose();
	}

    public Hud getHud(){ 
    		return hud; 
    	}
}

