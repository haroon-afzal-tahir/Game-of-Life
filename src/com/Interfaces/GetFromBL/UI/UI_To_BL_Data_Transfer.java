package com.Interfaces.GetFromBL.UI;

import org.json.simple.JSONObject;

public interface UI_To_BL_Data_Transfer {
	void setAlive(JSONObject row, JSONObject column);
	
	void setDead(JSONObject row, JSONObject column);
	
	JSONObject getCellStatus(JSONObject row, JSONObject column);
	
	void step();
	
	JSONObject getPlay();
	
	void setPlay(JSONObject play);
	
	JSONObject getZoomFactor();
	
	void setZoomFactor(JSONObject zf);
	
	JSONObject getSpeedFactor();
	
	void setSpeedFactor(JSONObject sf);
	
	void StartGame();
	
	void SetGenerations(JSONObject generations);
	
	JSONObject GetGenerations();
}
