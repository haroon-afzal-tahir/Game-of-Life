package com.FactoryImplementation;

import com.UI.Console;
import com.UI.Main;

public class UI_Factory {
	// UI Object
	Main UI_Object = null;
	// Console Object
	Console Console_Object = null;
	
	public UI_Factory() {
	}
	
	public Main getUI() {
		return UI_Object;
	}
	
	public void setUI(Main obj) {
		this.UI_Object = obj;
	}
	
	public Console getConsole() {
		return Console_Object;
	}
	
	public void setConsole(Console obj) {
		this.Console_Object = obj;
	}
}
