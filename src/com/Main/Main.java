package com.Main;

import Colors.Color;


public class Main {
	
	public static void printCommand() {
		System.out.println(Color.CYAN + "\nHow Do You Want To Play Game of Life");
		System.out.print(Color.RED + "1. " + Color.YELLOW + "DB To Application\t");
		System.out.print(Color.RED + "2. " + Color.YELLOW + "DB To Console\n");
		System.out.print(Color.RED + "3. " + Color.YELLOW + "File To Application\t");
		System.out.print(Color.RED + "4. " + Color.YELLOW + "File To Console\n");
		System.out.println(Color.MAGENTA + "Enter Any Other Key To Exit\n");
		
		
		/*
		for (int i = 0; i < 25; i++) {
			if ((i + 1) % 5 == 0) {
				System.out.println();
			}
			else {
				System.out.print(Color.RED);
				System.out.print((char) 0x25A0 + "\t");
			}
		}
		*/
		
		System.out.print(Color.GREEN + "Your Choice: ");
	}
	
	public static void main(String[] args) {
		/*
		Scanner input = new Scanner(System.in);

		Game game_obj=new Game(8,5);
		DB_TXT Db_object=new DB_TXT();

		game_obj.attachDB(Db_object);

		game_obj.setCell(0,0);
		game_obj.setCell(0,1);
		game_obj.setCell(2,2);
		game_obj.setCell(3,2);
		game_obj.setCell(4,2);

		game_obj.printboard();
		game_obj.save();
		game_obj.step();

		game_obj.printboard();
		game_obj.save();
		game_obj.step();

		game_obj.printboard();
		game_obj.save();
		game_obj.step();

		game_obj.view();
		Scanner inpu=new Scanner(System.in);
		String statename=inpu.next();
		game_obj.load(statename);
		game_obj.printboard();
		*/
	}
}
