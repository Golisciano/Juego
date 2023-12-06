package com.aniilo.game.screens;

import com.aniilo.game.SamuraiGame;
import com.aniilo.game.world.GameMap;
import com.aniilo.game.world.TiledGameMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

	private static final int EXIT_BUTTON_WIDTH  = 250;   //ANCHO DEL BOTON DE SALIDA
	private static final int EXIT_BUTTON_HEIGHT = 120;  //ALTURA DEL BOTON DE SALIDA
	private static final int PLAY_BUTTON_WIDTH  = 230;  //ANCHODEL BOTON DE JUGAR
	private static final int PLAY_BUTTON_HEIGHT = 100;  //ALTURA DEL BOTON DE JUGAR
	private static final int EXIT_BUTTON_Y = 100; //PARA QUE NO SE SOBREPONGAN LOS BOTONES 
	private static final int PLAY_BUTTON_Y = 250; 
	private Texture texture;
	private TextureRegion [] regionsMovimientos; 
	private Texture imagen; 
	private Texture texture2;
	private TextureRegion frameActual;
	private Animation animacion ;  
	private float tiempo;
	float x ;
	float y;
	
	SamuraiGame game; 
	GameMap gameMap;
	
	Texture playButtonActive; 
	Texture playButtonInactive; 
	Texture exitButtonActive; 
	Texture exitButtonInactive; 
	
	public MainMenuScreen (SamuraiGame game) {
		this.game = game;
		this.x = x; 
		this.y = y; 
		playButtonActive = new Texture ("play_button_active.png"); 
		playButtonInactive = new Texture ("play_button_inactive.png"); 
		exitButtonActive = new Texture ("exit_button_active.png"); 
		exitButtonInactive = new Texture ("exit_button_inactive_2.png"); 
		
		imagen = new Texture(Gdx.files.internal("samurai_assets_stan2_re.png")); //CARGA LA IMAGEN
		TextureRegion [][] tmp = TextureRegion.split(imagen, //UBICA LA REGION DE LA IMAGEN
				imagen.getWidth()/6, imagen.getHeight()); //DIVIDE EN PIEZAS LA IMAGEN SEGUN LA CANTIDAD DE IMAGENES Y LA ALTURA
		
		regionsMovimientos = new TextureRegion[6] ; 
		for (int i = 0 ; i<6; i++ ) regionsMovimientos[i] = tmp[0][i]; //SIRVE PARA PASAR DE LA MATRIZ AL ARRAY
		animacion  = new Animation(2/0f,regionsMovimientos); //CREA LA ANIMCAION Y LA DURACION DE LOS FRAMES
		tiempo = 0f; //INICIALIZA EL TIEMPO 
		
		
	//	gameMap = new TiledGameMap();

	} 
	

	@Override
	public void show() {
		texture = new Texture(Gdx.files.internal("fondoia.png"));

	}

	@Override
	public void render(float delta) {
		
		//                ROJO  VERDE  AZUL
		ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		game.batch.begin();
		game.batch.draw(texture ,0 ,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );
		tiempo += Gdx.graphics.getDeltaTime(); //ES EL TIEMPO QUE PASO DESDE EL ULTIMO RENDER
		frameActual = (TextureRegion) animacion.getKeyFrame(tiempo, true); //HACE UN LOOP
		game.batch.draw(frameActual ,x ,y); //DIBUJA EL FRAME
		
		int x = SamuraiGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2 ; //ACTIVA Y DESACTIVA EL BOTON DE JUGAR
		if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && SamuraiGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.input.getY() > PLAY_BUTTON_Y  ) 
		{
			game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y ,PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				this.dispose();
				gameMap.setScreen(new MainGameScreen(game));
			}
		} else { 
			game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y ,PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}
		
		
		int  g = SamuraiGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 ; //ACTIVA Y DESACTIVA EL BOTON DE SALIDA
		if (Gdx.input.getX() < g + EXIT_BUTTON_WIDTH && Gdx.input.getX() > g && SamuraiGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && Gdx.input.getY() > EXIT_BUTTON_Y  ) 
		{
			game.batch.draw(exitButtonActive, g, EXIT_BUTTON_Y ,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
		} else { 
			game.batch.draw(exitButtonInactive, g, EXIT_BUTTON_Y ,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}


		game.batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
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
		
	}

}
