package com.BL;

import com.FactoryImplementation.BL_Factory;
import com.FactoryImplementation.UI_Factory;
import com.Interfaces.SetToBL.DB_I;
import com.JSON_API.Simple_API;
import com.UI.Console;
import com.UI.Main;
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
    
    public void save(String filename) {
        DB_Listener.save((BL_Factory) this, filename);
    }
    
    public String view() {
        return DB_Listener.view();
    }
    
    public void load(String statename) {
        Game ret_obj = DB_Listener.load(statename);
        this.board = ret_obj.board;
        this.controls = ret_obj.controls;
        this.generations = ret_obj.generations;
    }
    
    public void delete(String statename) {
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
