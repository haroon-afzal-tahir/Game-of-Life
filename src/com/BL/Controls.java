package com.BL;

public class Controls {
	private float zoomFactor;
	private float speedFactor;
	private int generations;
	private boolean Play = true;
	private int score;
	
	public Controls(float zm, float sf, int g, boolean p) {
		this.zoomFactor = zm;
		this.generations = g;
		this.Play = p;
		this.speedFactor = sf;
	}
	
	public void setZoomFactor(float zm) {
		this.zoomFactor = zm;
	}
	
	public void setPlay(boolean play) {
		this.Play = play;
	}
	
	public float getSpeedFactor() {
		return this.speedFactor * 1000;
	}
	
	public void setSpeedFactor(float sf) {
		this.speedFactor = sf;
	}
	
	public int getGenerations() {
		return this.generations;
	}
	
	public void setGenerations(int g) {
		this.generations = g;
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
	
	public void setScore(int score) {
		this.score = score;
	}
}
