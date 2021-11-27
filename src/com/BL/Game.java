package com.BL;

import com.Interfaces.DB_I;

public class Game {
        private Controls control;
        private Board board;
        private DB_I DB_Listener;
        private int generations;


       public Game() {
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
                this.control.setSpeedFactor(sf);
        }
        
        public float getspeedfactor() {
            return this.control.getSpeedFactor();
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
}
