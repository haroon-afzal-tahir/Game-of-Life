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
	
	public void setZoomFactor(JSONObject zm) {
		this.zoomFactor = Float.parseFloat(Simple_API.JSONToString(zm));
	}
	
	public void setPlay(JSONObject play) {
		this.Play = Simple_API.JSONToBoolean(play);
	}
	
	public float getSpeedFactor() {
		return this.speedFactor * 500;
	}
	
	public void setSpeedFactor(JSONObject sf) {
		this.speedFactor = Float.parseFloat(Simple_API.JSONToString(sf));
	}
	
	public float getZoomfactor() {
		return this.zoomFactor;
	}
	
	public boolean getplay() {
		return this.Play;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(JSONObject score) {
		this.score = Integer.parseInt(Simple_API.JSONToString(score));
	}
	
	public int getGenerations() {
		return generations;
	}
	
	public void setGenerations(JSONObject generations) {
		this.generations = Integer.parseInt(Simple_API.JSONToString(generations));
	}
}
