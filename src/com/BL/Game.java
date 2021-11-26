package com.BL;

public class Game {
        private Controls control;
        private Board board;
        
        public Game() {
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
                return this.control.getgenerations();
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
}
