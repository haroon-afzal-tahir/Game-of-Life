package com.DB;

import com.BL.DB_I;
import com.JSON_API.Simple_API;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class DB_TXT implements DB_I {
	public void delete(JSONObject StateName) {
		// TODO Auto-generated method stub
		String filePath = new File("").getAbsolutePath();
		File dir = new File(filePath + "/files");
		String filename = Simple_API.JSONToString(StateName);
		File myFile = new File(dir, filename);
		
		if (myFile.delete()) {
			System.out.println(myFile.getName() + " has been deleted.\n");
		} else {
			System.out.println("\nSome problem occurred while deleting the file\n");
		}
		
	}
	
	public void save(JSONObject obj, JSONObject filename) {
		
		String filePath = new File("").getAbsolutePath();
		File dir = new File(filePath + "/files");
		
		try {
			File myObj = new File(dir, Simple_API.JSONToString(filename) + ".txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		try {
			FileWriter wFile = new FileWriter(new File(dir, Simple_API.JSONToString(filename) + ".txt"));
			
			int row = 20;
			int col = 75;
			wFile.write(row + "\n");
			wFile.write(col + "\n");
			wFile.write(obj.get("Generations") + "\n");
			JSONArray jsonArray = (JSONArray) obj.get("AliveCells");
			for (int i = 0; i < jsonArray.size(); i = i + 2) {
				wFile.write(jsonArray.get(i + 0) + "\n");
				wFile.write(jsonArray.get(i + 1) + "\n");
			}
			/*
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (obj.isAlive(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")) == true) {
						wFile.write(obj.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")).getX() + "\n");
						wFile.write(obj.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")).getY() + "\n");
						
					}
				}
			}
			*/
			wFile.write("-1\n-1");
			
			wFile.close();
			System.out.println("Successfully wrote to the file.");
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public JSONObject view() {
		String StateNames = "";
		String filePath = new File("").getAbsolutePath();
		File dir = new File(filePath + "/files");
		
		File[] matches = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		}); //checking the number of files in the folder
		for (int i = 0; i < matches.length; i++) {
			
			StateNames = StateNames.concat(matches[i].getName() + "\n");
		}
		
		return Simple_API.StringToJSON(StateNames, "States");
	}
	
	public JSONObject load(JSONObject stateName) {
		
		JSONObject jsonObject = new JSONObject();
		
		String filePath = new File("").getAbsolutePath();
		File dir = new File(filePath + "/files");
		String filename = Simple_API.JSONToString(stateName);
		String line;
		int x = 0, y = 0;
		try {
			FileReader fr = new FileReader(new File(dir, filename));
			BufferedReader bufferedReader = new BufferedReader(fr);
			
			line = bufferedReader.readLine();
			int rows = Integer.parseInt(line);
			
			line = bufferedReader.readLine();
			int columns = Integer.parseInt(line);
			
			
			line = bufferedReader.readLine();
			int generations = Integer.parseInt(line);
			
			jsonObject.put("Generations", generations);
			jsonObject.put("Speed", 1);
			JSONArray jsonArray = new JSONArray();
			while (x != -1 && y != -1) {
				line = bufferedReader.readLine();
				x = Integer.parseInt(line);
				
				line = bufferedReader.readLine();
				y = Integer.parseInt(line);
				if (x != -1 && y != -1) {
					jsonArray.add(x);
					jsonArray.add(y);
				}
			}
			jsonObject.put("AliveCells", jsonArray);
			
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
}
