package com.FactoryImplementation;

import com.BL.Board;
import com.BL.Controls;
import com.FactoryInterface.BL_Interface;

public class BL_Implementation implements BL_Interface {
	private Board board;
	private Controls controls;
	
	public BL_Implementation(int rows, int cols) {
		board = new Board(rows, cols);
		controls = new Controls(100, 1, 0, true);
	}
	
	public void updateBoard() throws InterruptedException {
		this.board.step();
		Thread.sleep((long) this.controls.getSpeedFactor());
	}
	
	
	@Override
	public Board getBoard() {
		return board;
	}
	
	@Override
	public void setBoard(Board obj) {
		board = obj;
	}
	
	@Override
	public Controls getControl() {
		return controls;
	}
	
	@Override
	public void setControl(Controls obj) {
		controls = obj;
	}
}
