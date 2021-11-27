package com.BL;

import com.Interfaces.DB_I;

public class Game {
        private Controls control;
        private Board board;
        private DB_I DB_Listener;
        private int generations;


       public Game() {
       }

    public Game(int rows,int columns) {
           board=new Board(rows,columns);
           control=new Controls(1,1,true);

    }
    
    public void attachDB(DB_I list) {
        this.DB_Listener = list;
    }
        
        public Controls getcontrols() {
                return control;
        }
        
        public Board getBoard() {
                return board;
        }
        
        public int getScore() {
                return control.getScore();
        }
        
        public void setspeedfactor(float sf) {
                this.control.setspeedfactor(sf);
        }
        
        public float getspeedfactor() {
                return this.control.getspeedfactor();
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

    public String view()
    {
        return DB_Listener.view();

    }
    public void load(String StateName)
    {
        Game ret=DB_Listener.load(StateName);
        this.board.setRows(ret.getRows());
        this.board.setColumns(ret.getCols());
        this.board=ret.board;
        this.control=ret.control;
        this.generations=ret.generations;
        this.board.printBoard();
    }


    public void setAlive(int row, int column) {
        board.setAlive(row, column);
    }

    public void setDead(int row, int column) {
        board.setDead(row, column);
    }

    public boolean isAlive(int row, int column) {
        return board.isAlive(row, column);
    }

    public void ToggleState(int row, int column) {
        board.ToggleState(row, column);
    }
    // This Function Returns The State Of The Selected Cell
    public boolean getState(int row, int column) {
        return board.getState(row, column);
    }

    public void step() {
        board.step();
    }
    public void printBoard()
    {

        board.printBoard();
    }


}
