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
	
	public JSONObject isAlive(JSONObject row, JSONObject column) {
		int i = Integer.parseInt(Simple_API.JSONToString(row));
		int j = Integer.parseInt(Simple_API.JSONToString(column));
		return this.board[i][j].isAlive();
	}
	
	// This Function Determines The Alive Neighbors With Respect To The Selected Cell
	public JSONObject CountAliveNeighbors(JSONObject row, JSONObject column) {
		int count = 0;
		int temp_row = (Integer) row.get("I");
		int temp_column = (Integer) column.get("J");
		for (int i = temp_row - 1; i <= temp_row + 1; i++) {
			for (int j = temp_column - 1; j <= temp_column + 1; j++) {
				if (i != temp_row || j != temp_column) {
					JSONObject k = new JSONObject(), l = new JSONObject();
					k.put("I", i);
					l.put("J", j);
					JSONObject jsonObject = getState(k, l);
					
					if (jsonObject != null) {
						count += ((Boolean) jsonObject.get("State")) ? 1 : 0;
					}
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Count", count);
		return jsonObject;
	}
	
	// This Function Returns The State Of The Selected Cell
	public JSONObject getState(JSONObject row, JSONObject column) {
		int i = (Integer) row.get("I");
		int j = (Integer) column.get("J");
		if (i < 0 || i >= this.rows) return null;
		if (j < 0 || j >= this.columns) return null;
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
				JSONObject row = new JSONObject(), column = new JSONObject();
				row.put("I", i);
				column.put("J", j);
				int aliveNeighbors = (Integer) CountAliveNeighbors(row, column).get("Count");
				JSONObject jsonObject = getState(row, column);
				if (jsonObject != null) {
					if ((Boolean) jsonObject.get("State") == true) {
						if (aliveNeighbors == 2 || aliveNeighbors == 3) {
							newBoard[i][j].setAlive(Simple_API.BooleanToJSON(true, "State"));
						}
					} else if (aliveNeighbors == 3) {
						newBoard[i][j].setAlive(Simple_API.BooleanToJSON(true, "State"));
					}
				}
			}
		}
		this.board = newBoard;
	}
	
	public JSONObject getCell(JSONObject row, JSONObject column) {
		int i = Integer.parseInt(Simple_API.JSONToString(row));
		int j = Integer.parseInt(Simple_API.JSONToString(column));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Cell", this.board[i][j]);
		return jsonObject;
	}
	
	public void setCell(JSONObject i, JSONObject j) {
		int row = Integer.parseInt(Simple_API.JSONToString(i));
		int column = Integer.parseInt(Simple_API.JSONToString(j));
		this.board[row][column].setX(i);
		this.board[row][column].setY(j);
		this.board[row][column].setAlive(Simple_API.BooleanToJSON(true, "State"));
	}
	
}
