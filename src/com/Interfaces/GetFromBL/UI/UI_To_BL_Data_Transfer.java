package com.Interfaces.GetFromBL.UI;

public interface UI_To_BL_Data_Transfer {
	void setAlive(int row, int column);
	
	void setDead(int row, int column);
	
	boolean getCellStatus(int row, int column);
	
	void step();
	
	boolean getPlay();
	
	void setPlay(boolean play);
	
	float getZoomFactor();
	
	void setZoomFactor(float zf);
	
	float getSpeedFactor();
	
	void setSpeedFactor(float sf);
	
	void StartGame();
}
