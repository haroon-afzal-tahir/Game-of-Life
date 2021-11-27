package com.BL;

import com.Interfaces.DB_I;

public class Game {
    private Board board;
    private Controls controls;
    private DB_I DB_Listener;
    private int generations;
    
    public Game(int rows, int cols) {
        board = new Board(rows, cols);
        controls = new Controls(100,  0, true);
    }
    
    public Game() {
    }
    
    public void updateBoard() throws InterruptedException {
        this.board.step();
        Thread.sleep((long) this.controls.getSpeedFactor());
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setBoard(Board obj) {
        board = obj;
    }
    
    public Controls getControl() {
        return controls;
    }
    
    public void setControl(Controls obj) {
        controls = obj;
    }
    
    public void attachDB(DB_I list) {
        this.DB_Listener = list;
    }
    
    public int getScore() {
        return controls.getScore();
    }
    
    public void setspeedfactor(float sf) {
        this.controls.setSpeedFactor(sf);
    }
    
    public float getspeedfactor() {
        return this.controls.getSpeedFactor();
    }
    
    public int getgenerations() {
        return generations;
    }
    
    public void setgenerations(int gen) {
        generations = gen;
    }
    
    public void setCell(int i, int j) {
        this.board.setCell(i, j);
    }
    
    public int getRows() {
        return this.board.getRows();
    }
    
    public int getCols() {
        return this.board.getColumns();
    }
    
    public Cell getCell(int i, int j) {
        return this.board.getCell(i, j);
    }
    
    public void attachBoard(Board board) {
        this.board = board;
    }
    
    public void save() {
        DB_Listener.save(this);
    }

    public boolean isAlive(int row, int column) {
        return board.isAlive(row,column);

    }

}
