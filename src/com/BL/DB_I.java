package com.BL;

import org.json.simple.JSONObject;

public interface DB_I {
	
	void delete(JSONObject StateName);
	
	void save(JSONObject obj, JSONObject filename);
	
	JSONObject view();
	
	JSONObject load(JSONObject stateName);
	
}
