package com.FactoryImplementation;

import com.BL.Game;
import org.json.simple.JSONObject;

public class BL_Factory extends Game {
	public BL_Factory(JSONObject rows, JSONObject columns, UI_Factory UIFactory) {
		super(rows, columns, UIFactory);
	}
	
	public BL_Factory(JSONObject rows, JSONObject columns) {
		super(rows, columns);
	}
}
