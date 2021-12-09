package com.BL;

import com.FactoryImplementation.Factory;
import com.Interfaces.SetToBL.DB_I;
import com.Interfaces.SetToBL.UI_I;
import com.UI.Console;
import com.UI.Main;

public class Game implements Runnable {
    private Board board;
    private Controls controls;
    private DB_I DB_Listener;
    private int generations;
    private UI_I UI_Listener;
    
    Factory factory = new Factory();
    
    private Thread start;
    
    public Game(int rows, int cols, Factory factory) {
        board = new Board(rows, cols);
        controls = new Controls(100, 0, true);
        this.factory = factory;
    }
    
    public Game(int rows, int cols) {
        board = new Board(rows, cols);
        controls = new Controls(100, 0, true);
    }
    
    public Game() {
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
    
    public void attachUI(UI_I list) {
        this.UI_Listener = list;
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
    
    public void save(String filename) {
        DB_Listener.save(this, filename);
    }
    
    public String view() {
        return DB_Listener.view();
    }
    
    public void load(String statename) {
        Game ret_obj = DB_Listener.load(statename);
        this.board = ret_obj.board;
        this.controls = ret_obj.controls;
        this.generations = ret_obj.generations;
    }
    
    public void delete(String statename) {
        DB_Listener.delete(statename);
    }
    
    public void printboard() {
        board.printBoard();
    }
    
    public void step() {
        board.step();
    }
    
    public boolean isAlive(int row, int column) {
        return board.isAlive(row, column);
    }
    
    @Override
    public void run() {
        Main UI = factory.getUI();
        Console console = factory.getConsole();
        while (this.controls.getplay() == true) {
            if (UI != null) {
                UI.step();
                UI.UpdateBoard();
            } else {
                console.step();
                console.print(console);
            }
            setgenerations(getgenerations() + 1);
            try {
                Thread.sleep((long) (((1 / (getspeedfactor())) * 100000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void StartGame() {
        start = new Thread(this);
        start.start();
    }
}
