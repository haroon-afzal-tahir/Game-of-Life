package com.InterfaceImplementation.GetFromBL.UI;

import com.Interfaces.GetFromBL.UI.ControlInterface;

public class ControlInterfaceImplementation implements ControlInterface {
	public int getSpeed(int speed) {
		return speed;
	}
	
	public int getZoom(int zoom) {
		return zoom;
	}
	
	public int getGenerations(int generations) {
		return generations;
	}
}
