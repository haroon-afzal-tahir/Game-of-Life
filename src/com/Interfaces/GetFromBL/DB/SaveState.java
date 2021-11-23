package com.Interfaces.GetFromBL.DB;

import com.BL.Cell;

public interface SaveState {
	void save(int gameID, int gameNo, Cell[][] Grid, int score, int counter, float speed);
}
