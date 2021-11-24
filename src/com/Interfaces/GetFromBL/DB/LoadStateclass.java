package com.Interfaces.GetFromBL.DB;

import com.BL.Game;

public class LoadStateclass implements LoadState {
	@Override
	public Game load(int gameID) {
		// TODO Auto-generated method stub
		Game ret_gameobj = new Game();
		
		var filename = "file"+ gameID + ".txt";
		try {
			FileReader fw = new FileReader(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return ret_gameobj;
	}
}
