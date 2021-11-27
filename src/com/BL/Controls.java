package com.BL;

public class Controls {
	private int zoomFactor;
	private float speedFactor;

	private boolean Play = true;
	private int score;
	
	public Controls(int zm, int sf, boolean p) {
		this.zoomFactor = zm;

		this.Play = p;
		this.speedFactor = sf;
	}
	
	public void setspeedfactor(float sf) {
		this.speedFactor = sf;
	}
	

	
	public void setzoomfactor(int zm) {
		this.zoomFactor = zm;
	}
	
	public void setplay(boolean play) {
		this.Play= play;
	}
	public float getspeedfactor() {
		return this.speedFactor;
	}
	

	
	public int getzoomfactor() {
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
