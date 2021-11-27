CREATE DATABASE GameOfLife;

use GameOfLife;

##drop table state;
CREATE TABLE state(
	StateName	VARCHAR(50)	PRIMARY KEY
);


##drop table cell;
CREATE TABLE cell(
	s_name	VARCHAR(50),	
	rownum INT	NOT null,
	columnNum INT	NOT null,
	id INT IDENTITY(1,1), --changed
	PRIMARY KEY(rownum, columnNum),
    FOREIGN KEY [s_name] REFERENCES [state](StateName)
);


#ALTER TABLE cell  WITH CHECK ADD  CONSTRAINT [s_name] FOREIGN KEY(s_name)
#REFERENCES [dbo].[state](StateName)
ALTER TABLE cell ADD CONSTRAINT [s_name] CHECK FOREIGN KEY(s_name) REFERENCES [state](StateName);
ON UPDATE CASCADE
ON DELETE CASCADE
GO


###drop table board;
CREATE TABLE board(
	[sname]	VARCHAR(50),	  #changed
	rowsnum INT		NOT NULL,
	columnsnum INT	NOT NULL,
    PRIMARY KEY(rowsnum, columnsnum),
    FOREIGN KEY [sname] REFERENCES[state](StateName) NOT NULL
);


ALTER TABLE board  WITH CHECK ADD  CONSTRAINT [sname] FOREIGN KEY(sname)
REFERENCES [dbo].[state](StateName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

###drop table Controls;
CREATE TABLE Controls(
	S_Nam	VARCHAR(50),	
	[generations] INT,
	speed FLOAT,
	PRIMARY KEY(S_Nam),
    FOREIGN KEY [S_Nam] REFERENCES[state](StateName) NOT NULL
);


ALTER TABLE Controls  WITH CHECK ADD  CONSTRAINT [S_Nam] FOREIGN KEY(S_Nam)
REFERENCES [dbo].[state](StateName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO
-------------------------------------------------------------------------------------------
#drop procedure SaveBoard;

DELIMETER //
CREATE PROCEDURE SaveBoard(State_Name VARCHAR(50),gridColumns INT,gridRows INT,speed FLOAT,generations INT)
#@flag int output
BEGIN
	#saving the game id initially
	BEGIN
		INSERT INTO [state](StateName) VALUES (State_Name )
	END
	BEGIN
		INSERT INTO board
		(
		    sname,
		    rowsnum,
		    columnsnum
		)
		VALUES
		(   State_Name , -- sid - int
		    gridRows,    -- rowsnum - int
		    gridColumns     -- columnsnum - int
		)
	END
	BEGIN
	INSERT INTO Controls
    VALUES
	(   State_Name ,   -- stateid - int
		generations,
		speed
	)

    END
END //
DELIMETER;
#GO

#call SaveBoard(
------------------------------------------------------------------------------------
##Drop Procedure Load Board;

DELIMETER //
CREATE PROCEDURE loadBoard(State_Name VARCHAR(50) ,OUT GCols INT,OUT GRows INT,OUT speed FLOAT,OUT generations INT )
BEGIN
     ###Check if state_id exists or not, which is going to be loaded
	 IF EXISTS THEN
	    SELECT  State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  State_Name
	
	##Loading rows and columns of board
	BEGIN
	     SELECT GRows, GCols
		 FROM dbo.board B
		 WHERE B.sname =  State_Name  AND GCols = B.columnsnum AND GRows = B.rowsnum
	END //
	###Loading speed,generations of game 
	BEGIN
	     SELECT speed,generations
		 FROM dbo.Controls E
		 WHERE E.S_Nam = State_Name  AND speed=E.speed AND generations=E.generations
        END //
END//
DELIMETER;
--------------------------------------------------------------------------------------------------
##Drop Procedure Save Cell;

DELIMETER //
CREATE PROCEDURE saveCell(State_Name VARCHAR(50),rowNum INT,colNum INT)
BEGIN
      ##Saving GameId, row number and column number of Cell
      BEGIN
	       INSERT INTO [dbo].cell
		(
		    s_name,
		    rownum,
		    columnNum
		)
		VALUES
		(   State_Name , -- state_id - int
		    rowNum,    -- rownum - int
		    colNum    -- columnNum - int
		) 

     END //
END//

DELIMETER;
---------------------------------------------------------------------------------------------



##drop procedure LoadCells;

DELIMETER //
CREATE PROCEDURE LoadCells(State_Name VARCHAR(50),OUT rownum INT,OUT columnnum INT)
BEGIN
	##checking if the state exists or not which is going to be loaded
	IF EXISTS 
		(
        SELECT  State_Name 
		FROM state S			
		WHERE S.StateName =  State_Name 
        )
	
	BEGIN
		SELECT rownum, columnnum
		FROM dbo.cell C
		WHERE C.s_name =  State_Name  AND columnnum = C.columnNum AND rownum = C.rownum
	END //
END //

DELIMETER;


--------------------------------------------------------------------------

#######Drop Procedure to view Cell;

DELIMETER //
CREATE PROCEDURE viewCell(State_Name VARCHAR(50),OUT rowNums INT,OUT colsNums INT)
BEGIN
      ##checking if the state exists or not which is going to be viewed
	IF EXISTS ( 
		SELECT  State_Name 
		FROM state S			
		WHERE S.StateName =  State_Name 
	)	
	BEGIN
		SELECT rowNums, colsNums
		FROM dbo.cell C
		WHERE C.s_name =  State_Name  AND colsNums = C.columnNum AND rowNums = C.rownum
	END //
END //

DELIMETER;

--------------------------------------------------------------------------------------------

####Drop Procedure View Board;
DELIMETER //
CREATE PROCEDURE viewBoard(State_Name VARCHAR(50),OUT gridCols INT,OUT gridRows INT,OUT speed FLOAT,OUT generations INT)
BEGIN
     ##Check if state_id exists or not, which is going to be viewed
	 IF EXISTS(
	    SELECT  State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  State_Name 
	)
	###view rows and columns of board
	BEGIN
	     SELECT gridRows, gridCols
		 FROM dbo.board B
		 WHERE B.sname =  State_Name  AND gridCols = B.columnsnum AND gridRows = B.rowsnum
	END //
	###view speed,generations of game 
	BEGIN
	     SELECT speed, generations
		 FROM dbo.Controls E
		 WHERE E.S_Nam = State_Name  AND speed=E.speed AND generations = E.generations
    END //
END //
DELIMETER;
--------------------------------------------------------------------------------------------

####drop procedure delete_state;

DELIMETER //
CREATE PROCEDURE delete_state(State_Name VARCHAR(50),OUT flag int)
BEGIN
	
	##checking if the game id exists or not
	IF EXISTS ( 
		SELECT  State_Name 
		FROM state S			
		WHERE S.StateName =  State_Name 
	)	
	  BEGIN 
	    DELETE FROM dbo.state 
	    WHERE StateName =  State_Name 
		SET flag = 1
		END // 
	ELSE
    BEGIN
	SET flag = -1
    END //
END //
DELIMETER;
