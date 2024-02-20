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
import com.mygdx.game.io.EntradasNivelUno;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaNivelUno implements Screen {
	

	private Juego game;
	
	SpriteBatch b;
	
	private TiledMap mapa;
//	private TiledMapRenderer mapaRenderer;
	private OrthogonalTiledMapRenderer renderer;
	
	private TextureAtlas atlas;
	
	private OrthographicCamera cam;
	
	private Viewport portJuego;
	
	EntradasNivelUno entradas = new EntradasNivelUno(this);

	public float tiempo = 0; 

	private Hud hud;
	
	
	private World mundo;
	private Box2DDebugRenderer b2dr;
	private B2CreadorMundo creador;
	

	private Ninja player;
	

	protected Fixture fixture;

	
	public PantallaNivelUno(Juego game) {
		atlas = new TextureAtlas("personajes/Personajes.pack");
		
		this.game = game;
		b = Render.batch;
		
		Gdx.input.setInputProcessor(entradas);
		
		
		cam = new OrthographicCamera();
		
		portJuego = new FitViewport(Juego.V_WITDH / Juego.PPM, Juego.V_HEIGHT/ Juego.PPM, cam);
		
		hud = new Hud(b);
		
		mapa = new TmxMapLoader().load(Recursos.FONDONIVELDOS);
		renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);
		
		
		cam.position.set(portJuego.getWorldWidth() / 2 , portJuego.getWorldHeight() / 2, 0);
		
		
		mundo = new World(new Vector2(0, -10), true);
		
		b2dr = new Box2DDebugRenderer();
		

		creador = new B2CreadorMundo(this);
		
		player = new Ninja(this);
		
		mundo.setContactListener(new MundoContactListener());

	}
	
	@Override
	public void show() {
		
	}

	public void handleInput(float dt) {
		
		if((entradas.isArriba())&&(player.b2body.getLinearVelocity().y <= 2)) {
			player.b2body.applyLinearImpulse(new Vector2(0 , 2f ), player.b2body.getWorldCenter(), true);
		}

		if((entradas.isDerecha())&&(player.b2body.getLinearVelocity().x <= 4)){
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		if((entradas.isIzquierda())&&(player.b2body.getLinearVelocity().x >= -7)){
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
		
		if(entradas.isAtaque()) {
			   PolygonShape swordShape = new PolygonShape();
			    swordShape.setAsBox(15/ Juego.PPM, 0);

			    FixtureDef swordFixtureDef = new FixtureDef();
			    swordFixtureDef.shape = swordShape;
			    swordFixtureDef.isSensor = false;

			 player.b2body.createFixture(swordFixtureDef).setUserData("espada");
		}
	}
	
	
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void update(float dt) {
		
		handleInput(dt);
		
		mundo.step(1/60f, 6 ,4);
		
		player.update(dt);
		for (Enemigo enemigo : creador.getArquero()) {
			enemigo.update(dt);
		}
//		arquero.update(dt);
		hud.update(dt);
		
		
		cam.position.x = player.b2body.getPosition().x;
		
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
//		renderer.render();
		
		b2dr.render(mundo, cam.combined);
		
		
		b.setProjectionMatrix(cam.combined);
		b.begin();
		player.draw(b);
		for (Enemigo enemigo : creador.getArquero()) {
			enemigo.draw(b);
			if(enemigo.getX() < player.getX() + 224 / Juego.PPM) {
				enemigo.b2body.setActive(true);
			}
		}
//		arquero.draw(b);
		b.end();
		

	}

	@Override
	public void resize(int width, int height) {
//		cam.setToOrtho(false, width, height);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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

