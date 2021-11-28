package com.UI;


import javafx.scene.control.Button;

public class Grid_Button extends Button {
	private int x, y;

	public Grid_Button(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
