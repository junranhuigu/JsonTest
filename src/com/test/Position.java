package com.test;

import java.text.DecimalFormat;

public class Position{
	private float x;
	private float y;
	private int mapId;
	
	public Position() {
		// TODO Auto-generated constructor stub
	}
	
	public Position(float x, float y, int mapId) {
		this.x = x;
		this.y = y;
		this.mapId = mapId;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		return " x=" + x + ", y=" + y + " , mapId=" + mapId;
	}
	
	@Override
	public boolean equals(Object obj) {
		Position p = (Position) obj;
		DecimalFormat format = new DecimalFormat("#0.000");
		return format.format(this.x) == format.format(p.x) && format.format(this.y) == format.format(p.y) && this.mapId == p.mapId;
	}
	
}
