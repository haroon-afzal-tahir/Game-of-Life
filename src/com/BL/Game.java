package com.BL;

import com.FactoryImplementation.UI_Factory;
import com.JSON_API.Simple_API;
import com.UI.Console;
import com.UI.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Game implements Runnable {
	private Board board;
	private Controls controls;
	private DB_I DB_Listener;
	private int generations;
	
	UI_Factory UIFactory = new UI_Factory();
	
	public Game(JSONObject rows, JSONObject cols, JSONObject UIFactory) {
		board = new Board(rows, cols);
		controls = new Controls(Simple_API.StringToJSON("100", "Zoom"), Simple_API.StringToJSON("0", "Speed"), Simple_API.BooleanToJSON(true, "State"));
		this.UIFactory = (UI_Factory) UIFactory.get("UI");
	}
	
	public Game(JSONObject rows, JSONObject cols) {
		board = new Board(rows, cols);
		controls = new Controls(Simple_API.StringToJSON("100", "Zoom"), Simple_API.StringToJSON("0", "Speed"), Simple_API.BooleanToJSON(true, "State"));
	}
	
	public Game() {
	}
	
	public JSONObject getBoard() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Board", board);
		return jsonObject;
	}
	
	public JSONObject getControl() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Controls", controls);
		return jsonObject;
	}
	
	public void attachDB(JSONObject list) {
		this.DB_Listener = (DB_I) list.get("DB");
	}
	
	public JSONObject getspeedfactor() {
		return this.controls.getSpeedFactor();
	}
	
	public JSONObject getgenerations() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Generations", generations);
		return jsonObject;
	}
	
	public void setgenerations(JSONObject gen) {
		generations = Integer.parseInt(Simple_API.JSONToString(gen));
	}
	
	public void save(JSONObject filename) {
		JSONArray Cells = new JSONArray();
		
		board = (Board) this.getBoard().get("Board");
		controls = (Controls) this.getControl().get("Controls");
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 75; j++) {
				JSONObject jsonObject = board.getState(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"));
				if ((Boolean) jsonObject.get("State")) {
					Cells.add(i);
					Cells.add(j);
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("AliveCells", Cells);
		jsonObject.put("Speed", controls.getSpeedFactor());
		jsonObject.put("Generations", controls.getGenerations());
		DB_Listener.save(jsonObject, filename);
	}
	
	public JSONObject view() {
		return DB_Listener.view();
	}
	
	public void load(JSONObject statename) {
		JSONObject jsonObject = DB_Listener.load(statename);
		JSONArray jsonArray = (JSONArray) jsonObject.get("AliveCells");
		
		this.board = new Board(Simple_API.StringToJSON("20", "Row"), Simple_API.StringToJSON("75", "Column"));
		
		for (int i = 0; i < jsonArray.size(); i = i + 2) {
			this.board.setCell(Simple_API.StringToJSON(String.valueOf(jsonArray.get(i)), "x"), Simple_API.StringToJSON(String.valueOf(jsonArray.get(i + 1)), "y"));
		}
		this.controls.setSpeedFactor(Simple_API.StringToJSON("500", "Speed"));
		this.controls.setGenerations(Simple_API.StringToJSON("0", "Generations"));
	}
	
	public void delete(JSONObject statename) {
		DB_Listener.delete(statename);
	}
	
	@Override
	public void run() {
		Main UI = UIFactory.getUI();
		Console console = UIFactory.getConsole();
		while ((Boolean) this.controls.getplay().get("Play") == true) {
			if (UI != null) {
				UI.step();
				UI.UpdateBoard();
			} else {
				console.step();
				console.print(console);
			}
			try {
				Thread.sleep((long) (((1 / (Float) getspeedfactor().get("Speed")) * 100000)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    public void StartGame() {
		Thread start = new Thread(this);
        start.start();
    }
}
