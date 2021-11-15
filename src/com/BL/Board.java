package com.BL;

import java.awt.*;

public class Board {
	// -------------------------------------------------------------
	// ------------------ VARIABLES DECLARATION --------------------
	// -------------------------------------------------------------
	
	private int rows;
	private int columns;
	
	private boolean[][] board;
	
	// -------------------------------------------------------------
	// ------------------------ CONSTRUCTOR ------------------------
	// -------------------------------------------------------------
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		this.board = new boolean[rows][columns];
	}
	
	
	// -------------------------------------------------------------
	// ------------------------ FUNCTIONS --------------------------
	// -------------------------------------------------------------
	
	public static void main(String[] args) {
		Board board = new Board(8, 5);
		
		board.setAlive(0, 0);
		board.setAlive(0, 1);
		board.setAlive(2, 2);
		board.setAlive(3, 2);
		board.setAlive(4, 2);
		
		System.out.println("Count: " + board.CountAliveNeighbors(3, 2));
		
		for (int i = 0; i < 5; i++) {
			board.printBoard();
			board.step();
		}
		
		board.printBoard();
		
	}
	
	public void printBoard() {
		System.out.println("----------");
		for (int i = 0; i < this.rows; i++) {
			String line = "|";
			for (int j = 0; j < this.columns; j++) {
				line += (this.board[i][j]) ? "*" : ".";
    			
    			/*
    			if (!this.board[j][i]) {
    				line += ".";
				}
    			else {
    				line += "*";
				}
				*/
			}
			line += "|";
			System.out.println(line);
		}
		System.out.println("----------\n");
	}
	
	public void setAlive(int row, int column) {
		this.board[row][column] = true;
	}
	
	public void setDead(int row, int column) {
		this.board[row][column] = false;
	}
	
	public boolean isAlive(int row, int column) {
		return this.board[row][column];
	}
	
	public void ToggleState(int row, int column) {
		this.board[row][column] = !this.board[row][column];
	}
	
	// This Function Determines The Alive Neighbors With Respect To The Selected Cell
	public int CountAliveNeighbors(int row, int column) {
		int count = 0;
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i != row || j != column) {
					count += (getState(i, j)) ? 1 : 0;
				}
			}
		}
		return count;
	}
	
	// This Function Returns The State Of The Selected Cell
	public boolean getState(int row, int column) {
		if (row < 0 || row >= this.rows) return false;
		if (column < 0 || column >= this.columns) return false;
		return this.board[row][column];
	}
	
	// -------------------------------------------------------------
	// ------------------------- SETTERS ---------------------------
	// -------------------------------------------------------------
	
	public void step() {
		boolean[][] newBoard = new boolean[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				int aliveNeighbors = CountAliveNeighbors(i, j);
				if (getState(i, j)) {
					if (aliveNeighbors == 2 || aliveNeighbors == 3) {
						newBoard[i][j] = true;
					}
				} else if (aliveNeighbors == 3) {
					newBoard[i][j] = true;
				}
			}
		}
		this.board = newBoard;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	// -------------------------------------------------------------
	// ------------------------- GETTERS ---------------------------
	// -------------------------------------------------------------
	
	public int getColumns() {
		return columns;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public boolean[][] getBoard() {
		return board;
	}
	
	public void setBoard(boolean[][] board) {
		this.board = board;
	}
}
