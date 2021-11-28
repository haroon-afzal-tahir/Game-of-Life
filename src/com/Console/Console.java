package com.Console;

import Colors.Color;
import com.BL.Game;
import com.FactoryImplementation.Factory;
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
		obj.setZoomFactor(1);
		obj.setSpeedFactor(500);
		
		while (obj.getPlay() == true) {
			obj.printCommands();
			int choice = 0;
			
			choice = input.nextInt();
			
			switch (choice) {
				case 1:
					obj.setPlay(true);
					break;
				case 2:
					obj.setPlay(false);
					break;
				case 3:
					obj.setSpeedFactor(obj.getSpeedFactor() + 500);
					break;
				case 4:
					obj.setSpeedFactor(obj.getSpeedFactor() - 500);
					break;
				case 5:
					if (obj.getZoomFactor() < 10) {
						obj.setZoomFactor((obj.getZoomFactor() + 100));
					}
					break;
				case 6:
					if (obj.getZoomFactor() > 1) {
						obj.setZoomFactor((obj.getZoomFactor() - 100));
					}
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					break;
				case 11:
					break;
				case 12:
					break;
				case 13:
					obj.print();
					input(obj);
					break;
				default:
					obj.print();
					break;
			}
		}
		
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
	
	public void printCommands() {
		System.out.println(Color.RED + "Instructions To Play Game of Life");
		System.out.println(Color.YELLOW + "1. " + Color.CYAN + "Press 's' To Start The Game");
		System.out.println(Color.YELLOW + "2. " + Color.CYAN + "Press 'S' To Stop The Game");
		System.out.println(Color.YELLOW + "3. " + Color.CYAN + "Press '+' To Speed Up The Game");
		System.out.println(Color.YELLOW + "4. " + Color.CYAN + "Press '-' To Slow Down The Game");
		System.out.println(Color.YELLOW + "5. " + Color.CYAN + "Press '[' To Zoom In The Game");
		System.out.println(Color.YELLOW + "6. " + Color.CYAN + "Press ']' To Zoom Out The Game");
		System.out.println(Color.YELLOW + "7. " + Color.CYAN + "Press '1' To Save The State");
		System.out.println(Color.YELLOW + "8. " + Color.CYAN + "Press '2' To Load The State");
		System.out.println(Color.YELLOW + "9. " + Color.CYAN + "Press '3' To Delete The State");
		System.out.println(Color.YELLOW + "10. " + Color.CYAN + "Press 'v' To View States");
		System.out.println(Color.YELLOW + "11. " + Color.CYAN + "Press ']' To Reset The State");
		System.out.println(Color.YELLOW + "12. " + Color.CYAN + "Press 'n' To Move To The Next State");
		System.out.println(Color.YELLOW + "13. " + Color.CYAN + "Press 'i' To Take Input");
		
		System.out.print(Color.GREEN + "\nYour Choice: ");
	}
	
	public void print() {
		System.out.println();
		
		System.out.print("\t");
		for (int j = 0; j < columns; j++) {
			System.out.print(Color.CYAN);
			System.out.print(j + "\t");
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
			System.out.println();
		}
		
		System.out.print(Color.WHITE);
		System.out.println("\n\n\n---------------------------------------------------------");
		
	}
	
	@Override
	public void setAlive(int row, int column) {
		game.getBoard().setAlive(row, column);
	}
	
	@Override
	public void setDead(int row, int column) {
		game.getBoard().setDead(row, column);
	}
	
	@Override
	public boolean getCellStatus(int row, int column) {
		return game.getBoard().getCell(row, column).isAlive();
	}
	
	@Override
	public void step() {
		game.getBoard().step();
	}
	
	@Override
	public boolean getPlay() {
		return game.getControl().getplay();
	}
	
	@Override
	public void setPlay(boolean play) {
		game.getControl().setPlay(play);
	}
	
	@Override
	public float getZoomFactor() {
		return game.getControl().getZoomfactor();
	}
	
	@Override
	public void setZoomFactor(float zf) {
		game.getControl().setSpeedFactor(zf);
	}
	
	@Override
	public float getSpeedFactor() {
		return game.getControl().getSpeedFactor();
	}
	
	@Override
	public void setSpeedFactor(float sf) {
		game.getControl().setSpeedFactor(sf);
	}
	
	@Override
	public void StartGame() {
		game.StartGame();
	}
}
