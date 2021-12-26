package com.BL;

import com.JSON_API.Simple_API;
import org.json.simple.JSONObject;

public class Controls {
	private float zoomFactor;
	private float speedFactor;
	private int generations;
	
	private boolean Play = true;
	private int score;
	
	public Controls(JSONObject zm, JSONObject sf, JSONObject p) {
		this.zoomFactor = Float.parseFloat(Simple_API.JSONToString(zm));
		this.speedFactor = Float.parseFloat(Simple_API.JSONToString(sf));
		
		this.Play = Simple_API.JSONToBoolean(p);
		generations = 0;
	}
	
	public JSONObject getSpeedFactor() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Speed", this.speedFactor * 500);
		return jsonObject;
	}
	
	public void setSpeedFactor(JSONObject sf) {
		this.speedFactor = Float.parseFloat(Simple_API.JSONToString(sf));
	}
	
	public JSONObject getplay() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Play", this.Play);
		return jsonObject;
	}
	
	public void setPlay(JSONObject play) {
		Play = (Boolean) play.get("Play");
	}
	
	public JSONObject getZoomFactor() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Zoom", zoomFactor);
		return jsonObject;
	}
	
	public void setZoomFactor(JSONObject zoom) {
		zoomFactor = Float.parseFloat((String) zoom.get("Zoom"));
	}
	
	public JSONObject getGenerations() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Generations", generations);
		return jsonObject;
	}
	
	public void setGenerations(JSONObject generations) {
		this.generations = Integer.parseInt(Simple_API.JSONToString(generations));
	}
}
