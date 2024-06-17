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
import com.mygdx.game.sprites.Arquero;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja2;
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


    EntradasPjUno entradasUno = new EntradasPjUno(this);


    public float tiempo = 0;

    private Hud hud;


    private World mundo;
    private Box2DDebugRenderer b2dr;
    public static B2CreadorMundo creador;


    public static Ninja2 player1;

    public static Ninja2 player2;


    protected Fixture fixture;


    public static Vector2 player1Position;
    public static Vector2 player2Position;


    public PantallaNivelUno(Juego game) {
        atlas = new TextureAtlas("personajes/Persj.pack");
        this.game = game;
        b = Render.batch;

        Gdx.input.setInputProcessor(entradasUno);


        cam = new OrthographicCamera();

        portJuego = new FitViewport(Juego.V_WITDH / Juego.PPM, Juego.V_HEIGHT / Juego.PPM, cam);

        hud = new Hud(b);

        mapa = new TmxMapLoader().load(Recursos.FONDONIVELDOS);
        renderer = new OrthogonalTiledMapRenderer(mapa, 1 / Juego.PPM);


        cam.position.set(portJuego.getWorldWidth() / 2, portJuego.getWorldHeight() / 2, 0);


        mundo = new World(new Vector2(0, -10), true);


        creador = new B2CreadorMundo(this);
        player1 = new Ninja2(0, this);
        player2 = new Ninja2(0, this);


        player1Position = player1.b2body.getPosition();
        player2Position = player2.b2body.getPosition();
        mundo.setContactListener(new MundoContactListener());

    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (entradasUno.isArriba() || entradasUno.isAbajo() || entradasUno.isIzquierda() || entradasUno.isDerecha()) {
            Juego.hc.enviaerMensaje("mover#" + entradasUno.isArriba() + "#" + entradasUno.isAbajo() + "#" + entradasUno.isIzquierda() + "#" + entradasUno.isDerecha());
        }

    }


    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void update(float dt) {
        if ((HiloCliente.idCliente == 0 && !player1.ninjaEstaMuerto) ||
            (HiloCliente.idCliente == 1 && !player2.ninjaEstaMuerto)) {
            handleInput(dt);
        }

        actualizarDelServidor();

        mundo.step(1 / 60f, 6, 4);

        actualizarDelServidor();
		
        player1.update(dt);
        player2.update(dt);
        for (Arquero enemigo : creador.getArquero()) {
            enemigo.update(dt);
        }

        hud.update(dt);


        if (HiloCliente.idCliente == 0) {
            cam.position.x = player1.ninjaEstaMuerto ? player2.b2body.getPosition().x : player1.b2body.getPosition().x;
        } else {
            cam.position.x = player2.ninjaEstaMuerto ? player1.b2body.getPosition().x : player2.b2body.getPosition().x;
        }



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


        b.setProjectionMatrix(cam.combined);
        b.begin();
        if(!player1.ninjaEstaMuerto) player1.draw(b);
        if(!player2.ninjaEstaMuerto) player2.draw(b);
        for (Arquero enemigo : creador.getArquero()) {
            enemigo.draw(b);

        }

        b.end();


    }


    public void actualizarDelServidor() {
        if (!player1.b2body.getPosition().equals(player1Position)) {
            if (player1.b2body.getPosition().x < player1Position.x) {
            }
            player1.b2body.setTransform(player1Position, player1.b2body.getAngle());
        }
        if (!player2.b2body.getPosition().equals(player2Position)) {
            player2.b2body.setTransform(player2Position, player2.b2body.getAngle());
        }


        for (Arquero enemigo : creador.getArquero()) {
            if (enemigo.b2body.getPosition().x != enemigo.proximoX || enemigo.b2body.getPosition().y != enemigo.proximoY) {

                enemigo.b2body.setTransform(enemigo.proximoX, enemigo.proximoY, enemigo.b2body.getAngle());
            }

        }

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

    public Hud getHud() {
        return hud;
    }
}

