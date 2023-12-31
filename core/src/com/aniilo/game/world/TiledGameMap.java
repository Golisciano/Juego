package com.aniilo.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap extends GameMap {

	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	
	
	
	public TiledGameMap () {
		tiledMap = new TmxMapLoader().load("mapa_villa.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
	}

	@Override
	public void render(OrthographicCamera camara) {
		tiledMapRenderer.setView(camara);
		tiledMapRenderer.render();

	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		tiledMap.dispose();

	}

	@Override
	public TileType getTileTYpeByCordenadas (int layer, int col, int fila) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, fila);
		
		if (cell !=null) {
			TiledMapTile tile = cell.getTile();
			
			if (tile != null) {
				int id = tile.getId();
				return TileType.getTileTypeId(id);
			}
		}
		return null;
	}

	@Override
	public int getWidth() {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}

	
	public int getHeight() {
		
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}

	@Override
	public int getLayers() {
		return tiledMap.getLayers().getCount();
	}

}
