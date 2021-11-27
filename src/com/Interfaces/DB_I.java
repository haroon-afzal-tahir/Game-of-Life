package com.Interfaces;

import com.BL.Game;

public interface DB_I {
	
	void delete(String StateName);
	
	void save(Game obj);
	
	// public Cell[][] view();
	Game load(String stateName);
	
}
