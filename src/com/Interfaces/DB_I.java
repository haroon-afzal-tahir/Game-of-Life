package com.Interfaces;


import com.BL.Cell;
import com.BL.Game;

public interface DB_I {

    public void delete(String StateName);
    public void save(Game obj);
   // public Cell[][] view();
    public Game load(String stateName);

}
