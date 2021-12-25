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
	
	public Game(JSONObject rows, JSONObject cols, UI_Factory UIFactory) {
		board = new Board(rows, cols);
		controls = new Controls(Simple_API.StringToJSON("100", "Zoom"), Simple_API.StringToJSON("0", "Speed"), Simple_API.BooleanToJSON(true, "State"));
		this.UIFactory = UIFactory;
	}
	
	
	public Game(JSONObject rows, JSONObject cols) {
		board = new Board(rows, cols);
		controls = new Controls(Simple_API.StringToJSON("100", "Zoom"), Simple_API.StringToJSON("0", "Speed"), Simple_API.BooleanToJSON(true, "State"));
	}
	
	public Game() {
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Controls getControl() {
		return controls;
	}
	
	public void attachDB(DB_I list) {
		this.DB_Listener = list;
	}
	
	public void setspeedfactor(JSONObject sf) {
		this.controls.setSpeedFactor(sf);
	}
	
	public float getspeedfactor() {
		return this.controls.getSpeedFactor();
	}
	
	public int getgenerations() {
		return generations;
	}
	
	public void setgenerations(JSONObject gen) {
		generations = Integer.parseInt(Simple_API.JSONToString(gen));
	}
	
	public void save(JSONObject filename) {
		JSONArray Cells = new JSONArray();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 75; j++) {
				if (this.getBoard().getState(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"))) {
					Cells.add(i);
					Cells.add(j);
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("AliveCells", Cells);
		jsonObject.put("Speed", this.getControl().getSpeedFactor());
		jsonObject.put("Generations", this.getControl().getGenerations());
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
	
	public boolean isAlive(JSONObject row, JSONObject column) {
		return board.isAlive(row, column);
	}
	
	@Override
	public void run() {
		Main UI = UIFactory.getUI();
		Console console = UIFactory.getConsole();
		while (this.controls.getplay() == true) {
            if (UI != null) {
                UI.step();
                UI.UpdateBoard();
            } else {
                console.step();
                console.print(console);
            }
			setgenerations(Simple_API.StringToJSON(String.valueOf(getgenerations() + 1), "Generations"));
            try {
                Thread.sleep((long) (((1 / (getspeedfactor())) * 100000)));
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
