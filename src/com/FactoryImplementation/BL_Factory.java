package com.FactoryImplementation;

import com.BL.Game;

public class BL_Factory extends Game {
	public BL_Factory(int rows, int columns, UI_Factory UIFactory) {
		super(rows, columns, UIFactory);
	}
}
