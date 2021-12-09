package com.BL;

public class Controls {
	private float zoomFactor;
	private float speedFactor;
	private int generations;
	
	private boolean Play = true;
	private int score;
	
	public Controls(float zm, float sf, boolean p) {
		this.zoomFactor = zm;
		
		this.Play = p;
		this.speedFactor = sf;
		generations = 0;
	}
	
	public void setZoomFactor(float zm) {
		this.zoomFactor = zm;
	}
	
	public void setPlay(boolean play) {
		this.Play = play;
	}
	
	public float getSpeedFactor() {
		return this.speedFactor * 500;
	}
	
	public void setSpeedFactor(float sf) {
		this.speedFactor = sf;
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
	
	public int getGenerations() {
		return generations;
	}
	
	public void setGenerations(int generations) {
		this.generations = generations;
	}
}
