package com.Interfaces;

import com.BL.Game;

public interface DB_I {
	
	void delete(String StateName);
	
	void save(Game obj);
	
	String view();
	
	Game load(String stateName);
	
}
