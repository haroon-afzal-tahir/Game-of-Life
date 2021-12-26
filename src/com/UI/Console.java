package com.UI;

import Colors.Color;
import com.BL.*;
import com.FactoryImplementation.BL_Factory;
import com.FactoryImplementation.UI_Factory;
import com.JSON_API.Simple_API;
import org.json.simple.JSONObject;

import java.util.Scanner;


public class Console implements UI_To_BL_Data_Transfer, UI_I {
	int rows = 20, columns = 75;
	UI_Factory UIFactory;
	BL_Factory game;
	
	final static Scanner input = new Scanner(System.in);
	
	public Console(JSONObject rows, JSONObject columns, JSONObject UIFactory) {
		this.rows = Integer.parseInt(Simple_API.JSONToString(rows));
		this.columns = Integer.parseInt(Simple_API.JSONToString(columns));
		this.UIFactory = (UI_Factory) UIFactory.get("UI");
		
		game = new BL_Factory(Simple_API.StringToJSON(String.valueOf(this.rows), "Row"), Simple_API.StringToJSON(String.valueOf(this.columns), "Column"), UIFactory);
	}
	
	public static void main(DB_I test) {
		UI_Factory UIFactory = new UI_Factory();
		JSONObject UI = new JSONObject();
		UI.put("UI", UIFactory);
		Console obj = new Console(Simple_API.StringToJSON("20", "Row"), Simple_API.StringToJSON("75", "Column"), UI);
		UIFactory.setConsole(obj);
		obj.setZoomFactor(Simple_API.StringToJSON("100", "Zoom"));
		obj.setSpeedFactor(Simple_API.StringToJSON("0.15", "Speed"));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("DB", test);
		obj.game.attachDB(jsonObject);
		
		char choice = 's';
		while (checkInput(choice)) {
			obj.print(obj);
			obj.printCommands();
			choice = input.next().charAt(0);
			System.out.print(Color.RESET);
			switch (choice) {
				case 's':
					obj.setPlay(Simple_API.BooleanToJSON(true, "Play"));
					obj.StartGame();
					break;
				case 'S':
					obj.setPlay(Simple_API.BooleanToJSON(false, "Play"));
					break;
				case '+':
					if (Float.parseFloat(Simple_API.JSONToString(obj.getSpeedFactor())) < 0.25f) {
						obj.setSpeedFactor(Simple_API.StringToJSON(String.valueOf((float) (Float.parseFloat(Simple_API.JSONToString(obj.getSpeedFactor())) + 0.01)), "Speed"));
					}
					break;
				case '-':
					if (Float.parseFloat(Simple_API.JSONToString(obj.getSpeedFactor())) > 0.15f) {
						obj.setSpeedFactor(Simple_API.StringToJSON(String.valueOf((float) (Float.parseFloat(Simple_API.JSONToString(obj.getSpeedFactor())) - 0.01)), "Speed"));
					}
					break;
				case '[':
					if (Float.parseFloat(Simple_API.JSONToString(obj.getZoomFactor())) < 1000) {
						obj.setZoomFactor(Simple_API.StringToJSON(String.valueOf(Float.parseFloat(Simple_API.JSONToString(obj.getZoomFactor())) + 100), "Zoom"));
					}
					break;
				case ']':
					if (Float.parseFloat(Simple_API.JSONToString(obj.getZoomFactor())) > 100) {
						obj.setZoomFactor(Simple_API.StringToJSON(String.valueOf(Float.parseFloat(Simple_API.JSONToString(obj.getZoomFactor())) - 100), "Zoom"));
					}
					break;
				case '1':
					System.out.print(Color.BLUE + "Enter State Name: ");
					String filename = "";
					while (filename == "") {
						filename = input.next();
					}
					
					save(obj, Simple_API.StringToJSON(filename, "StateName"));
					break;
				case '2':
					load(obj);
					obj.setZoomFactor(Simple_API.StringToJSON("100", "Zoom"));
					obj.setSpeedFactor(Simple_API.StringToJSON("0.15", "Speed"));
					break;
				case '3':
					delete(obj);
					break;
				case 'v':
					System.out.println(view(obj));
					break;
				case 'r':
					reset(obj);
					break;
				case 'n':
					step(obj);
					break;
				case 'i':
					input(obj);
					break;
				default:
					obj.print(obj);
					break;
			}
		}
	}
	
	public static void load(Console obj) {
		System.out.println(view(obj));
		System.out.print("Enter State Name To Load: ");
		String temp;
		temp = input.next();
		obj.game.load(Simple_API.StringToJSON(temp + ".txt", "StateName"));
	}
	
	public static void delete(Console obj) {
		System.out.println(Color.CYAN + view(obj));
		System.out.print(Color.GREEN + "Enter Which State You Want To Delete: " + Color.RESET);
		String temp = input.next();
		obj.game.delete(Simple_API.StringToJSON(temp + ".txt", "StateName"));
	}
	
	public static String view(Console obj) {
		return Simple_API.JSONToString(obj.game.view());
	}
	
	public static void input(Console obj) {
		System.out.print(Color.RED + "Enter X-Coordinate and Y-Coordinate (i.e. num1 num2): ");
		int x = 0, y = 0;
		x = input.nextInt();
		y = input.nextInt();
		
		if (Simple_API.JSONToBoolean(obj.getCellStatus(Simple_API.StringToJSON(String.valueOf(x), "Row"), Simple_API.StringToJSON(String.valueOf(y), "Column"))))
			obj.setDead(Simple_API.StringToJSON(String.valueOf(x), "Row"), Simple_API.StringToJSON(String.valueOf(y), "Column"));
		else
			obj.setAlive(Simple_API.StringToJSON(String.valueOf(x), "Row"), Simple_API.StringToJSON(String.valueOf(y), "Column"));
	}
	
	public static void reset(Console obj) {
		for (int i = 0; i < obj.rows; i++) {
			for (int j = 0; j < obj.columns; j++) {
				obj.setDead(Simple_API.StringToJSON(String.valueOf(i), "Row"), Simple_API.StringToJSON(String.valueOf(j), "Column"));
			}
		}
		obj.SetGenerations(Simple_API.StringToJSON("0", "Generations"));
	}
	
	public static void step(Console obj) {
		obj.step();
		int generations = Integer.parseInt(Simple_API.JSONToString(obj.GetGenerations())) + 1;
		obj.SetGenerations(Simple_API.StringToJSON(String.valueOf(generations), "Generations"));
	}
	
	public static void save(Console obj, JSONObject filename) {
		obj.game.save(filename);
	}
	
	public static boolean checkInput(char choice) {
		return choice == 's' || choice == 'S' || choice == '+' || choice == '-' || choice == '[' || choice == ']' || choice == '1' ||
					   choice == '2' || choice == '3' || choice == 'v' || choice == 'r' || choice == 'n' || choice == 'i';
	}
	
	public void printCommands() {
		System.out.println(Color.RED + "Instructions To Play Game of Life");
		System.out.println(Color.YELLOW + "1. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "s" + Color.MAGENTA + "' " + Color.CYAN + "To Start The Game");
		System.out.println(Color.YELLOW + "2. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "S" + Color.MAGENTA + "' " + Color.CYAN + "To Stop The Game");
		System.out.println(Color.YELLOW + "3. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "+" + Color.MAGENTA + "' " + Color.CYAN + "To Speed Up The Game");
		System.out.println(Color.YELLOW + "4. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "-" + Color.MAGENTA + "' " + Color.CYAN + "To Slow Down The Game");
		System.out.println(Color.YELLOW + "5. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "[" + Color.MAGENTA + "' " + Color.CYAN + "To Zoom In The Game");
		System.out.println(Color.YELLOW + "6. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "]" + Color.MAGENTA + "' " + Color.CYAN + "To Zoom Out The Game");
		System.out.println(Color.YELLOW + "7. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "1" + Color.MAGENTA + "' " + Color.CYAN + "To Save The State");
		System.out.println(Color.YELLOW + "8. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "2" + Color.MAGENTA + "' " + Color.CYAN + "To Load The State");
		System.out.println(Color.YELLOW + "9. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "3" + Color.MAGENTA + "' " + Color.CYAN + "To Delete The State");
		System.out.println(Color.YELLOW + "10. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "v" + Color.MAGENTA + "' " + Color.CYAN + "To View States");
		System.out.println(Color.YELLOW + "11. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "r" + Color.MAGENTA + "' " + Color.CYAN + "To Reset The State");
		System.out.println(Color.YELLOW + "12. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "n" + Color.MAGENTA + "' " + Color.CYAN + "To Move To The Next State");
		System.out.println(Color.YELLOW + "13. " + Color.CYAN + "Press" + Color.MAGENTA + " '" + Color.BLUE + "i" + Color.MAGENTA + "' " + Color.CYAN + "To Take Input");
		
		
		System.out.print(Color.GREEN + "\nYour Choice: ");
	}
	
	public void print(Console obj) {
		System.out.println();
		
		System.out.print("\t");
		float zoom = (Float) obj.getZoomFactor().get("Zoom");
		for (int j = 0; j < obj.columns; j++) {
			System.out.print(Color.CYAN);
			System.out.print(j);
			for (int k = 0; k < (zoom / 100); k++) {
				System.out.print("\t");
			}
		}
		System.out.println();
		
		for (int i = 0; i < obj.rows; i++) {
			System.out.print(Color.CYAN);
			System.out.print(i + "\t");
			
			for (int j = 0; j < obj.columns; j++) {
				if (Simple_API.JSONToBoolean(getCellStatus(Simple_API.StringToJSON(String.valueOf(i), "Row"), Simple_API.StringToJSON(String.valueOf(j), "Column")))) {
					System.out.print(Color.RED);
				} else {
					System.out.print(Color.WHITE);
				}
				System.out.print((char) 0x25A0);
				for (int k = 0; k < (zoom / 100); k++) {
					System.out.print("\t");
				}
			}
			for (int j = 0; j < (zoom / 100); j++) {
				System.out.println();
			}
		}
		
		System.out.print(Color.WHITE);
		System.out.println("\nGenerations: " + obj.GetGenerations() + "\n---------------------------------------------------------");
		
	}
	
	@Override
	public void setAlive(JSONObject row, JSONObject column) {
		((com.BL.Cell) ((Board) game.getBoard().get("Board")).getCell(row, column).get("Cell")).setAlive(Simple_API.BooleanToJSON(true, "State"));
	}
	
	@Override
	public void setDead(JSONObject row, JSONObject column) {
		((com.BL.Cell) ((Board) game.getBoard().get("Board")).getCell(row, column).get("Cell")).setAlive(Simple_API.BooleanToJSON(false, "State"));
	}
	
	@Override
	public JSONObject getCellStatus(JSONObject row, JSONObject column) {
		return Simple_API.BooleanToJSON((Boolean) (((Cell) ((Board) game.getBoard().get("Board")).getCell(row, column).get("Cell")).isAlive().get("State")), "State");
	}
	
	@Override
	public void step() {
		((Board) game.getBoard().get("Board")).step();
	}
	
	@Override
	public JSONObject getPlay() {
		return Simple_API.BooleanToJSON((Boolean) ((Controls) game.getControl().get("Controls")).getplay().get("Play"), "State");
	}
	
	@Override
	public void setPlay(JSONObject play) {
		((Controls) game.getControl().get("Controls")).setPlay(play);
	}
	
	@Override
	public JSONObject getZoomFactor() {
		return ((Controls) game.getControl().get("Controls")).getZoomFactor();
	}
	
	@Override
	public void setZoomFactor(JSONObject zf) {
		((Controls) game.getControl().get("Controls")).setZoomFactor(zf);
	}
	
	@Override
	public JSONObject getSpeedFactor() {
		return ((Controls) game.getControl().get("Controls")).getSpeedFactor();
	}
	
	@Override
	public void setSpeedFactor(JSONObject sf) {
		((Controls) game.getControl().get("Controls")).setSpeedFactor(sf);
	}
	
	@Override
	public void StartGame() {
		game.StartGame();
	}
	
	@Override
	public void SetGenerations(JSONObject generations) {
		((Controls) game.getControl().get("Controls")).setGenerations(generations);
	}
	
	@Override
	public JSONObject GetGenerations() {
		return ((Controls) game.getControl().get("Controls")).getGenerations();
	}
}