package com.aniilo.game.world;

import java.util.HashMap;

public enum TileType {
	
	PIEDRA(4, true, "PIEDRA"),
	LADRILLO(5, true,"LADRILLO" ),
	MADERA(14,true,"MADERA");
	
	
	public static final int TILE_SIZE = 16;
	private int id;
	private boolean colicion; 
	private String nombre; 
	private float danio;
	
	private TileType (int id, boolean colicion, String nombre ) {
		this(id ,colicion, nombre, 0);
	}

	private TileType (int id, boolean colicion, String nombre, float danio ) {
		this.id = id;
		this.colicion = colicion;
		this.nombre = nombre;
		this.danio = danio;
	
	}

	public int getId() {
		return id;
	}

	public boolean isColicion() {
		return colicion;
	}

	public String getNombre() {
		return nombre;
	}

	public float getDanio() {
		return danio;
	}
	//es un array excepto que no se necesita un index, solamente tiene que ser un objeto 
	private static HashMap<Integer, TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tileType :TileType.values() ) {
			tileMap.put(tileType.getId(), tileType );
		}
	}

	public static TileType getTileTypeId (int id) {
		return tileMap.get(id);
	}
}
