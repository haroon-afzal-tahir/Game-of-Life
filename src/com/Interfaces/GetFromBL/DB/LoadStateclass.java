package com.Interfaces.GetFromBL.DB;

import com.BL.Game;

public class LoadStateclass implements LoadState {
	@Override
	public Game load(int gameID) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Game ret_gameobj = new Game();
		int ch = 0;
		var filename = "file"+ gameID + ".txt";
		int generation = 0, counter = 0;
		try {
			FileReader fr = new FileReader(filename);
			// read from FileReader till the end of file
			
			while ((ch = fr.read()) != -1)
			    System.out.println((char)ch);
			
			// close the file
			    fr.close();
			}
			catch (IOException e) {
	            e.printStackTrace();
	        }
		return ret_gameobj;
	}
}
