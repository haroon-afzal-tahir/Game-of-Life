package com.Interfaces.GetFromBL.DB;


//the Statename will be taken from user in ViewState
//And then the file will be deleted, and it's name will be deleted from the list of saved states
//the updated list will be returned to main
public interface DeleteState {
	public char[][] delete(String statename, char[][] list,  int size_list);
}
