package com.BL;

import com.JSON_API.Simple_API;
import org.json.simple.JSONObject;

public class Board {
	
	// -------------------------------------------------------------
	// ------------------ VARIABLES DECLARATION --------------------
	// -------------------------------------------------------------
	
	private int rows;
	private int columns;
	
	private Cell[][] board;
	
	// -------------------------------------------------------------
	// ------------------------ CONSTRUCTOR ------------------------
	// -------------------------------------------------------------
	
	public Board(JSONObject rows, JSONObject columns) {
		this.rows = Integer.parseInt(Simple_API.JSONToString(rows));
		this.columns = Integer.parseInt(Simple_API.JSONToString(columns));
		
		this.board = new Cell[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.board[i][j] = new Cell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"));
			}
		}
	}
	
	// -------------------------------------------------------------
	// ------------------------ FUNCTIONS --------------------------
	// -------------------------------------------------------------
	
	public boolean isAlive(JSONObject row, JSONObject column) {
		int i = Integer.parseInt(Simple_API.JSONToString(row));
		int j = Integer.parseInt(Simple_API.JSONToString(column));
		return this.board[i][j].isAlive();
	}
	
	// This Function Determines The Alive Neighbors With Respect To The Selected Cell
	public int CountAliveNeighbors(JSONObject row, JSONObject column) {
		int count = 0;
		int temp_row = Integer.parseInt(Simple_API.JSONToString(row));
		int temp_column = Integer.parseInt(Simple_API.JSONToString(column));
		for (int i = temp_row - 1; i <= temp_row + 1; i++) {
			for (int j = temp_column - 1; j <= temp_column + 1; j++) {
				if (i != temp_row || j != temp_column) {
					count += (getState(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"))) ? 1 : 0;
				}
			}
		}
		return count;
	}
	
	// This Function Returns The State Of The Selected Cell
	public boolean getState(JSONObject row, JSONObject column) {
		int i = Integer.parseInt(Simple_API.JSONToString(row));
		int j = Integer.parseInt(Simple_API.JSONToString(column));
		if (i < 0 || i >= this.rows) return false;
		if (j < 0 || j >= this.columns) return false;
		return this.board[i][j].isAlive();
	}
	
	public void step() {
		Cell[][] newBoard = new Cell[this.rows][this.columns];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newBoard[i][j] = new Cell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"));
			}
		}
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				int aliveNeighbors = CountAliveNeighbors(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"));
				if (getState(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J"))) {
					if (aliveNeighbors == 2 || aliveNeighbors == 3) {
						newBoard[i][j].setAlive(Simple_API.BooleanToJSON(true, "State"));
					}
				} else if (aliveNeighbors == 3) {
					newBoard[i][j].setAlive(Simple_API.BooleanToJSON(true, "State"));
				}
			}
		}
		this.board = newBoard;
	}
	
	// -------------------------------------------------------------
	// ------------------------- SETTERS ---------------------------
	// -------------------------------------------------------------
	
	public int getRows() {
		return rows;
	}
	
	// -------------------------------------------------------------
	// ------------------------- GETTERS ---------------------------
	// -------------------------------------------------------------
	
	public int getColumns() {
		return columns;
	}
	
	public Cell getCell(JSONObject i, JSONObject j) {
		int row = Integer.parseInt(Simple_API.JSONToString(i));
		int column = Integer.parseInt(Simple_API.JSONToString(j));
		return this.board[row][column];
	}
	
	public void setCell(JSONObject i, JSONObject j) {
		int row = Integer.parseInt(Simple_API.JSONToString(i));
		int column = Integer.parseInt(Simple_API.JSONToString(j));
		this.board[row][column].setX(i);
		this.board[row][column].setY(j);
		this.board[row][column].setAlive(Simple_API.BooleanToJSON(true, "State"));
	}
}
