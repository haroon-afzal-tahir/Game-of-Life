package com.FactoryInterface;

import com.BL.Board;
import com.BL.Controls;

public interface BL_Interface {
	Board getBoard();
	
	void setBoard(Board obj);
	
	Controls getControl();
	
	void setControl(Controls obj);
}
