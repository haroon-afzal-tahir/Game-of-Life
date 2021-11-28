package com.Interfaces.GetFromBL.DB;

import com.BL.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadStateclass implements LoadState {
	
	@Override
	public Game load(String filename) {
		// TODO Auto-generated method stub
		Game ret_gameobj = new Game();
		filename = filename + ".txt";
		String line;
		int x = 0, y = 0;
		try {
				FileReader fr = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fr);
				// read from FileReader 
				//read total rows of the grid
				line = bufferedReader.readLine();
				int rows = Integer.parseInt(line);
				//System.out.println(rows);
				
				ret_gameobj.getBoard().setRows(rows);
							
				//total columns of the grid 
				line = bufferedReader.readLine();
			int columns = Integer.parseInt(line);
			//System.out.println(columns);
			
			ret_gameobj.getBoard().setColumns(columns);
			
			//generations
			line = bufferedReader.readLine();
			int generations = Integer.parseInt(line);
			//System.out.println(generations);
			
			//ret_gameobj.getControl().setGenerations(generations);
			
			//speed
			line = bufferedReader.readLine();
			float speed = Float.parseFloat(line);
			//System.out.println(speed);
			
			ret_gameobj.getControl().setSpeedFactor(speed);
			
			//read the indexes of the alive cells
			while (x != -1 && y != -1) {
				//row number of a cell
				line = bufferedReader.readLine();
				x = Integer.parseInt(line);
				//System.out.println(x);
				
				//column number of a cell
				line = bufferedReader.readLine();
					y = Integer.parseInt(line);
					//System.out.println(y);
					
					ret_gameobj.getBoard().setCell(x, y);
				}
			
			// close the file
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret_gameobj;
	}
}
