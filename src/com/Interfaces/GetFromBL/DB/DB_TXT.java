package com.Interfaces.GetFromBL.DB;

import com.BL.Game;
import com.Interfaces.DB_I;

import java.io.*;

public class DB_TXT implements DB_I {

    public void delete(String StateName)
    {
        // TODO Auto-generated method stub
        String filePath = new File("").getAbsolutePath();
        File dir = new File(filePath+"/files");
        String filename = StateName + ".txt";
        File myFile = new File(dir,filename);

        if (myFile.delete()){
            System.out.println(myFile.getName() + " has been deleted.\n" );
        }
        else {
            System.out.println("\nSome problem occurred while deleting the file\n");
        }

    }

    public void save( Game obj)
    {

        String filePath = new File("").getAbsolutePath();
        File dir = new File(filePath+"/files");

        File[] matches = dir.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return  name.endsWith(".txt");
            }
        }); //checking the number of files in the folder


        int length;
        if (matches!=null) {
            length= matches.length;
        } else {
            length=0;
        }
        int state= length+1; //Naming Next File to be Saved
        //create a fle with name of state
        try {
            File myObj = new File(dir,"File"+state+".txt");
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
            FileWriter wFile = new FileWriter(new File(dir,"File"+state +".txt"));
            File filer;


            //writing cell of the board in file
            //Board obj1 = obj.getBoard();
            int row = obj.getBoard().getRows();
            int col = obj.getBoard().getColumns();
            wFile.write(row + "\n");
            wFile.write(col + "\n");
            wFile.write(obj.getgenerations() + "\n");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (obj.isAlive(i,j) == true) {
                        wFile.write(obj.getBoard().getCell(i, j).getX() + "\n");
                        wFile.write(obj.getBoard().getCell(i, j).getY() + "\n");

                    }
                }
            }
            wFile.write("-1\n-1");
            //for(int cols=1;cols<=col;cols++)
            //{
            //wFile.write(Game.obj[rows][cols]);
            //wFile.write(obj.getBoard().setCell(rows, cols));
            //wFile.write(obj.Board[rows][cols].setY(cols));
            //wFile.write(obj.Board[rows][cols].isAlive(true));
            //condition to check if cell is alive then save it in the file
                    /*if(obj.Board[rows][cols].isAlice(true)
                    {
                        wFile.write( obj.Board[rows][cols].setX(rows));
                        wFile.write(obj.Board[rows][cols].setY(cols));


                    }*/
            //}

            wFile.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Adding name in the list
        /*
        int i=0,j=0;
        for(i=0;i<size_list-1;i++)
        {
            for(j=0;j<list[i].length;j++)
            {
                list[i][j]=wFile[i][j];
            }

        }

        */



    }

    public String view()
    {
    
        String StateNames = "";
        String filePath = new File("").getAbsolutePath();
        File dir = new File(filePath + "/files");
    
        File[] matches = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        }); //checking the number of files in the folder
        for (int i = 0; i < matches.length; i++) {

            StateNames=StateNames.concat(matches[i].getName() + "\n");
        }

        return StateNames;
    }

    public Game load(String stateName)
    {

        String filePath = new File("").getAbsolutePath();
        File dir = new File(filePath+"/files");
        Game ret_gameobj=null;
        String filename = stateName + ".txt";
        String line;
        int x = 0, y = 0;
        try {
            FileReader fr = new FileReader(new File(dir,filename));
            BufferedReader bufferedReader = new BufferedReader(fr);
            // read from FileReader
            //read total rows of the grid
            line = bufferedReader.readLine();
            int rows = Integer.parseInt(line);
            //System.out.println(rows);
    
    
            //total columns of the grid
            line = bufferedReader.readLine();
            int columns = Integer.parseInt(line);
            //System.out.println(columns);
    
    
            ret_gameobj = new Game(rows, columns);
            //generations
            line = bufferedReader.readLine();
            int generations = Integer.parseInt(line);
            //System.out.println(generations);
    
            ret_gameobj.setgenerations(generations);
    
            //speed
    
            //System.out.println(speed);

            ret_gameobj.setspeedfactor(1);

            //read the indexes of the alive cells
            while (x != -1 && y !=-1) {
                //row number of a cell
                line = bufferedReader.readLine();
                x = Integer.parseInt(line);
                //System.out.println(x);

                //column number of a cell
                line = bufferedReader.readLine();
                y = Integer.parseInt(line);
                //System.out.println(y);
                if(x!=-1 && y!=-1)
                    ret_gameobj.getBoard().setCell(x, y);
            }

            // close the file
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret_gameobj;

    }
}
