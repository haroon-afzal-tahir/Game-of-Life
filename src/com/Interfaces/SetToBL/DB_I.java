package com.Interfaces.SetToBL;

import com.BL.Game;
import com.FactoryImplementation.BL_Factory;

public interface DB_I {
	
	void delete(String StateName);
	
	void save(BL_Factory obj, String filename);
	
	String view();
	
	Game load(String stateName);
	
}
