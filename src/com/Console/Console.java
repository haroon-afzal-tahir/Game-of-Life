package com.Console;

import Colors.Color;
import com.BL.Game;
import com.FactoryImplementation.Factory;
import com.Interfaces.DB_I;
import com.Interfaces.GetFromBL.DB.DB_TXT;
import com.Interfaces.GetFromBL.UI.UI_To_BL_Data_Transfer;

import java.util.Scanner;


public class Console implements UI_To_BL_Data_Transfer {
	int rows = 20, columns = 75;
	Factory factory;
	Game game;
	
	final static Scanner input = new Scanner(System.in);
	
	public Console(int rows, int columns, Factory factory) {
		this.rows = rows;
		this.columns = columns;
		this.factory = factory;
		
		game = new Game(this.rows, this.columns, this.factory);
	}
	
	public static void main(String[] args) {
		Factory factory = new Factory();
		Console obj = new Console(20, 75, factory);
		factory.setConsole(obj);
		obj.setZoomFactor(100);
		obj.setSpeedFactor(0.15f);
		DB_I test = new DB_TXT();
		obj.game.attachDB(test);
		
		char choice = 's';
		while (checkInput(choice)) {
			obj.print();
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
					save(obj);
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
					view(obj);
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
					obj.print();
					break;
			}
		}
		
	}
	
	public static void load(Console obj) {
		System.out.println(view(obj));
		System.out.print("Enter State Name To Load: ");
		String temp;
		temp = input.next();
		obj.game.load(temp);
	}
	
	public static void delete(Console obj) {
		System.out.println(Color.CYAN + view(obj));
		System.out.print(Color.GREEN + "Enter Which State You Want To Delete: " + Color.RESET);
		String temp = input.next();
		obj.game.delete(temp);
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
			for (int j = 0; j < obj.rows; j++) {
				obj.setDead(i, j);
			}
		}
	}
	
	public static void step(Console obj) {
		obj.step();
	}
	
	public static void save(Console obj) {
		obj.game.save();
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
	
	public void print() {
		System.out.println();
		
		System.out.print("\t");
		for (int j = 0; j < columns; j++) {
			System.out.print(Color.CYAN);
			System.out.print(j);
			for (int k = 0; k < (getZoomFactor() / 100); k++) {
				System.out.print("\t");
			}
		}
		System.out.println();
		
		for (int i = 0; i < rows; i++) {
			System.out.print(Color.CYAN);
			System.out.print(i + "\t");
			
			for (int j = 0; j < columns; j++) {
				if (getCellStatus(i, j)) {
					System.out.print(Color.RED);
				} else {
					System.out.print(Color.WHITE);
				}
				System.out.print((char) 0x25A0);
				for (int k = 0; k < (getZoomFactor() / 100); k++) {
					System.out.print("\t");
				}
			}
			for (int j = 0; j < (getZoomFactor() / 100); j++) {
				System.out.println();
			}
		}
		
		System.out.print(Color.WHITE);
		System.out.println("\n---------------------------------------------------------");
		
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
}
