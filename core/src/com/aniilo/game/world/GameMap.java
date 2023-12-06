package com.aniilo.game.world;

import com.aniilo.game.screens.MainGameScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class GameMap {

	public abstract void render (OrthographicCamera camara); 
	public abstract void update (float delta); 
	public abstract void dispose (); 

	/**
	 * Obtiene un tile por la pocicion del pixel en el mundo en una capa especifica
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	

	public TileType getTileTYpeByLocation(int layer, float x, float y ) {
		return this.getTileTYpeByCordenadas(layer, (int)(x/TileType.TILE_SIZE), (int)(y/TileType.TILE_SIZE)); 
	}
	
	
	public abstract TileType getTileTYpeByCordenadas(int layer, int col, int fila ); 
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();
	public void setScreen(MainGameScreen TiledGameMap) {
		// TODO Auto-generated method stub
		
	}
}
