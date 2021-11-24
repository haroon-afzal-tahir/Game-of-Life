package com.Interfaces.GetFromBL.DB;

import com.BL.Game;

public interface ViewState {
	//This shows a list of saved states. 
	//The user can then select a state from the list and load it.
	//The name of the selected state will be returned.
	String view(char[][] list);
}
