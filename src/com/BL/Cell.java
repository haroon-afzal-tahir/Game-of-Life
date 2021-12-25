package com.BL;

import com.JSON_API.Simple_API;
import org.json.simple.JSONObject;

public class Cell {
	
	// -------------------------------------------------------------
	// ------------------ VARIABLES DECLARATION --------------------
	// -------------------------------------------------------------
	private int x, y; // Co-ordinates
	private boolean isAlive = false;
	
	// -------------------------------------------------------------
	// ------------------------ CONSTRUCTOR ------------------------
	// -------------------------------------------------------------
	public Cell(JSONObject x, JSONObject y) {
		this.x = Integer.parseInt(Simple_API.JSONToString(x));
		this.y = Integer.parseInt(Simple_API.JSONToString(y));
		isAlive = false;
	}
	
	public void setValues(JSONObject x, JSONObject y, JSONObject flag) {
		setX(x);
		setY(y);
		setAlive(flag);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(JSONObject x) {
		this.x = Integer.parseInt(Simple_API.JSONToString(x));
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(JSONObject y) {
		this.y = Integer.parseInt(Simple_API.JSONToString(y));
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(JSONObject alive) {
		isAlive = Simple_API.JSONToBoolean(alive);
	}
	
	public void setDead(JSONObject dead) {
		isAlive = Simple_API.JSONToBoolean(dead);
	}
}
