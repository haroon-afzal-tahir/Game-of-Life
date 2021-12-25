package com.UI;

import Colors.Color;
import com.FactoryImplementation.BL_Factory;
import com.FactoryImplementation.UI_Factory;
import com.Interfaces.GetFromBL.UI.UI_To_BL_Data_Transfer;
import com.Interfaces.SetToBL.DB_I;
import com.Interfaces.SetToBL.UI_I;

import java.util.Scanner;


public class Console implements UI_To_BL_Data_Transfer, UI_I {
	int rows = 20, columns = 75;
	UI_Factory UIFactory;
	BL_Factory game;
	
	final static Scanner input = new Scanner(System.in);
	
	public Console(int rows, int columns, UI_Factory UIFactory) {
		this.rows = rows;
		this.columns = columns;
		this.UIFactory = UIFactory;
		
		game = new BL_Factory(this.rows, this.columns, this.UIFactory);
	}
	
	public static void main(DB_I test) {
		UI_Factory UIFactory = new UI_Factory();
		Console obj = new Console(20, 75, UIFactory);
		UIFactory.setConsole(obj);
		obj.setZoomFactor(100);
		obj.setSpeedFactor(0.15f);
		obj.game.attachDB(test);
		
		char choice = 's';
		while (checkInput(choice)) {
			obj.print(obj);
			obj.printCommands();
			choice = input.next().charAt(0);
			System.out.print(Color.RESET);
			switch (choice) {
				case 's':
					obj.setPlay(true);
					obj.StartGame();
					break;
				case 'S':
					obj.setPlay(false);
					break;
				case '+':
					if (obj.getSpeedFactor() < 0.25f) {
						obj.setSpeedFactor((float) (obj.getSpeedFactor() + 0.01));
					}
					break;
				case '-':
					if (obj.getSpeedFactor() > 0.15f) {
						obj.setSpeedFactor((float) (obj.getSpeedFactor() - 0.01));
					}
					break;
				case '[':
					if (obj.getZoomFactor() < 1000) {
						obj.setZoomFactor((obj.getZoomFactor() + 100));
					}
					break;
				case ']':
					if (obj.getZoomFactor() > 100) {
						obj.setZoomFactor((obj.getZoomFactor() - 100));
					}
					break;
				case '1':
					System.out.print(Color.BLUE + "Enter State Name: ");
					String filename = "";
					while (filename == "") {
						filename = input.next();
					}
					
					save(obj, filename);
					break;
				case '2':
					load(obj);
					obj.setZoomFactor(100);
					obj.setSpeedFactor(0.15f);
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
		obj.game.load(temp + ".txt");
	}
	
	public static void delete(Console obj) {
		System.out.println(Color.CYAN + view(obj));
		System.out.print(Color.GREEN + "Enter Which State You Want To Delete: " + Color.RESET);
		String temp = input.next();
		obj.game.delete(temp + ".txt");
	}
	
	public static String view(Console obj) {
		return obj.game.view();
	}
	
	public static void input(Console obj) {
		System.out.print(Color.RED + "Enter X-Coordinate and Y-Coordinate (i.e. num1 num2): ");
		int x = 0, y = 0;
		x = input.nextInt();
		y = input.nextInt();
		
		if (obj.getCellStatus(x, y))
			obj.setDead(x, y);
		else
			obj.setAlive(x, y);
	}
	
	public static void reset(Console obj) {
		for (int i = 0; i < obj.rows; i++) {
			for (int j = 0; j < obj.columns; j++) {
				obj.setDead(i, j);
			}
		}
		obj.SetGenerations(0);
	}
	
	public static void step(Console obj) {
		obj.step();
		obj.SetGenerations(obj.GetGenerations() + 1);
	}
	
	public static void save(Console obj, String filename) {
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
		for (int j = 0; j < obj.columns; j++) {
			System.out.print(Color.CYAN);
			System.out.print(j);
			for (int k = 0; k < (obj.getZoomFactor() / 100); k++) {
				System.out.print("\t");
			}
		}
		System.out.println();
		
		for (int i = 0; i < obj.rows; i++) {
			System.out.print(Color.CYAN);
			System.out.print(i + "\t");
			
			for (int j = 0; j < obj.columns; j++) {
				if (getCellStatus(i, j)) {
					System.out.print(Color.RED);
				} else {
					System.out.print(Color.WHITE);
				}
				System.out.print((char) 0x25A0);
				for (int k = 0; k < (obj.getZoomFactor() / 100); k++) {
					System.out.print("\t");
				}
			}
			for (int j = 0; j < (obj.getZoomFactor() / 100); j++) {
				System.out.println();
			}
		}
		
		System.out.print(Color.WHITE);
		System.out.println("\nGenerations: " + obj.GetGenerations() + "\n---------------------------------------------------------");
		
	}
	
	@Override
	public void setAlive(int row, int column) {
		this.game.getBoard().setAlive(row, column);
	}
	
	@Override
	public void setDead(int row, int column) {
		this.game.getBoard().setDead(row, column);
	}
	
	@Override
	public boolean getCellStatus(int row, int column) {
		return this.game.getBoard().getCell(row, column).isAlive();
	}
	
	@Override
	public void step() {
		this.game.getBoard().step();
	}
	
	@Override
	public boolean getPlay() {
		return this.game.getControl().getplay();
	}
	
	@Override
	public void setPlay(boolean play) {
		this.game.getControl().setPlay(play);
	}
	
	@Override
	public float getZoomFactor() {
		return this.game.getControl().getZoomfactor();
	}
	
	@Override
	public void setZoomFactor(float zf) {
		this.game.getControl().setZoomFactor(zf);
	}
	
	@Override
	public float getSpeedFactor() {
		return this.game.getControl().getSpeedFactor();
	}
	
	@Override
	public void setSpeedFactor(float sf) {
		this.game.getControl().setSpeedFactor(sf);
	}
	
	@Override
	public void StartGame() {
		this.game.StartGame();
	}
	
	@Override
	public void SetGenerations(int generations) {
		this.game.getControl().setGenerations(generations);
	}
	
	@Override
	public int GetGenerations() {
		return this.game.getControl().getGenerations();
	}
}
