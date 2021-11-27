package com.Interfaces.GetFromBL.DB;


import com.BL.Game;

public interface DB_I {

    public void delete(String StateName);
    public void save(char[][] list, int size_list);
    public char[][] view();
    public char[][] load(String stateName);

}
