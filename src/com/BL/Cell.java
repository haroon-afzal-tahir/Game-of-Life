package com.BL;

public class Cell {
    
    // -------------------------------------------------------------
    // ------------------ VARIABLES DECLARATION --------------------
    // -------------------------------------------------------------
    private int x, y; // Co-ordinates
    private boolean isAlive = false;
    
    // -------------------------------------------------------------
    // ------------------------ CONSTRUCTOR ------------------------
    // -------------------------------------------------------------
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        isAlive = false;
    }
    
    public void setValues(int x, int y, boolean flag) {
        setX(x);
        setY(y);
        setAlive(flag);
    }
    
    public int getX() {
        return x;
    }
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean alive) {
		isAlive = alive;
	}
	
	public void setDead(boolean dead) {
		isAlive = dead;
	}
}
