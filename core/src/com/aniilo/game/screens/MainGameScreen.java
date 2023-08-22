package com.aniilo.game.screens;

import com.aniilo.game.SamuraiGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainGameScreen implements Screen {
	public static final float SPEED = 200; 
	public static final int SAMURAI_WIDTH = 110; 
	public static final int SAMURAI_HEIGHT = 170; 

	private Animation animacion ;  
	private float tiempo; 
	private SpriteBatch batch;
	private TextureRegion [] regionsMovimientos; 
	private Texture imagen; 
	private Texture texture;
	private TextureRegion frameActual; 
	
	int walk; 
	float stateTime;
	float x ;
	float y; 
 
	
	SamuraiGame game; 
	
	public MainGameScreen (SamuraiGame game) {
		this.game = game; 
		this.x = x; 
		this.y = y; 
		
			imagen = new Texture(Gdx.files.internal("samurai_assests_walk_re.png")); //CARGA LA IMAGEN
			TextureRegion [][] tmp = TextureRegion.split(imagen, //UBICA LA REGION DE LA IMAGEN
					imagen.getWidth()/6, imagen.getHeight()); //DIVIDE EN PIEZAS LA IMAGEN SEGUN LA CANTIDAD DE IMAGENES Y LA ALTURA
			
			regionsMovimientos = new TextureRegion[6] ; 
			for (int i = 0 ; i<6; i++ ) regionsMovimientos[i] = tmp[0][i]; //SIRVE PARA PASAR DE LA MATRIZ AL ARRAY
			animacion  = new Animation(2/10f,regionsMovimientos); //CREA LA ANIMCAION Y LA DURACION DE LOS FRAMES
			tiempo = 0f; //INICIALIZA EL TIEMPO 

		
	} 
	

	@Override
	public void show () {
		texture = new Texture(Gdx.files.internal("fondo_villa.jpg"));
	
	}

	@Override
	public void render (float delta) {
		
		if(Gdx.input.isKeyPressed(Keys.W)) {  //CONTROL PARA SUBIR 
			y += SPEED * Gdx.graphics.getDeltaTime() ; 
			
			if (y + SAMURAI_HEIGHT > Gdx.graphics.getHeight() ) {
				y = Gdx.graphics.getHeight() - SAMURAI_HEIGHT; 
			}
			
		} 
		
		if(Gdx.input.isKeyPressed(Keys.S)) { //CONTROL PARA BAJAR 
			y -= SPEED * Gdx.graphics.getDeltaTime();  
			
			if (y < 0 ) { //EL PJ NO PUEDE BAJAR MAS DE LA CORDENADA
				y = 0; 
			}
		} 
		
		if(Gdx.input.isKeyPressed(Keys.A)) { //CONTROL PARA LA IZQUIERDA
			x -= SPEED * Gdx.graphics.getDeltaTime(); 
			
			if (x < 0 ) { //EL PJ NO PUEDE MOVERSE A LA IZQUIERDAS MAS DE LA CORDENADA
				x = 0; 
			}
		} 
		
		if(Gdx.input.isKeyPressed(Keys.D)) { //CONTOL PARA LA DERECHA 
			x += SPEED * Gdx.graphics.getDeltaTime(); 
			
			if (x + SAMURAI_WIDTH > Gdx.graphics.getWidth() ) {
				x = Gdx.graphics.getWidth() - SAMURAI_WIDTH; 
			}
		//	if (x + badlogic.jpg > Gdx.graphics.getWidth()) {
				
		//	}
		}
		
	
		//                ROJO  VERDE  AZUL
		ScreenUtils.clear(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		game.batch.begin();
		tiempo += Gdx.graphics.getDeltaTime(); //ES EL TIEMPO QUE PASO DESDE EL ULTIMO RENDER
		frameActual = (TextureRegion) animacion.getKeyFrame(tiempo, true); //HACE UN LOOP
		game.batch.draw(texture ,0 ,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );
		game.batch.draw(frameActual ,x ,y); //DIBUJA EL FRAME
		game.batch.end();


	}

	
		
	

	@Override
	public void resize (int width, int height) {
	
	}

	@Override
	public void pause () {
		
	}

	@Override
	public void resume () {
	
	}

	@Override
	public void hide () {
	
	}

	@Override
	public void dispose () {
		game.batch.dispose();
		
	}

}
