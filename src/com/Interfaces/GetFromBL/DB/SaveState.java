package com.Interfaces.GetFromBL.DB;

import com.BL.Game;

// Firstly, user will be asked about a name for the state to be saved
// Then, a file will be created with that name
// Then, the Game obj will be saved in steps in the file
// Then, the name will be added in the list and the list will be returned

public interface SaveState {
	public char[][] save(char[][] list, int size_list, Game obj);
}
