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
	
	public void setX(JSONObject x) {
		this.x = Integer.parseInt(Simple_API.JSONToString(x));
	}
	
	public void setY(JSONObject y) {
		this.y = Integer.parseInt(Simple_API.JSONToString(y));
	}
	
	public JSONObject isAlive() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("State", isAlive);
		return jsonObject;
	}
	
	public void setAlive(JSONObject alive) {
		isAlive = Simple_API.JSONToBoolean(alive);
	}
}
