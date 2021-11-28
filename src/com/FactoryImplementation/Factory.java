package com.FactoryImplementation;

import com.UI.Main;

public class Factory {
	// UI Object
	Main obj;
	
	public Factory() {
	}
	
	public Main getUI() {
		return obj;
	}
	
	public void setUI(Main obj) {
		this.obj = obj;
	}
}
