package com.Interfaces.GetFromBL.DB;
import com.BL.Game;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Scanner;


import java.io.File;
import java.io.IOException;

public class SaveStateClass implements SaveState {
    public char[][] save(char[][] list, int size_list, Game obj)
    {
        //input statename from user
        Scanner input=new Scanner(System.in());
        System.out.println("Enter the state name:");
        String state=input.next();

        //create a fle with name of state
        try {
            File myObj = new File("File"+state+".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //write in the file state.txt
        try {
            FileWriter wFile = new FileWriter("File"+state +".txt");
            for(int rows=1;rows<=10;rows++)
            {
                //writing score in the file
                if(obj.Score<=200)
                {
                    wFile.write("Score of the game "+ state+" is:"+ obj.Score);
                    //System.out.println("Score of the game "+ state+" is:"+ obj.Score);

                }
                //writing speed of the game state in file
                if(obj.setspeedfactor(state)<=1.5)
                {
                    wFile.write("Speed of the game "+ state+" is:"+ obj.setspeedfactor(state));
                    //System.out.println("Speed of the game "+state+" is:"+obj.setspeedfactor(state));

                }
                //writing generation counter of the game state in file
                if(obj.setgenerations(state)<=10)
                {
                    wFile.write("Generation of cell of the game "+ state+" is:"+ obj.setgenerations(state));
                    //System.out.println("Generation of the game "+state+" is:"+obj.setgenerations(state));

                }
                //writing cell of the board in file
                for(int cols=1;cols<=20;cols++)
                {
                    //wFile.write(Game.obj[rows][cols]);
                    wFile.write( obj.Board[rows][cols].setX(rows));
                    wFile.write(obj.Board[rows][cols].setY(cols));
                    wFile.write(obj.Board[rows][cols].isAlive(true));
                    //condition to check if cell is alive then save it in the file
                    /*if(obj.Board[rows][cols].isAlice(true)
                    {
                        wFile.write( obj.Board[rows][cols].setX(rows));
                        wFile.write(obj.Board[rows][cols].setY(cols));


                    }*/
                }


            }
            wFile.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Adding name in the list
        int i=0,j=0;
        for(i=0;i<size_list-1;i++)
        {
            for(j=0;j<list[i].length;j++)
            {
                list[i][j]=wFile[i][j];
            }

        }
        return list;

    }

}
