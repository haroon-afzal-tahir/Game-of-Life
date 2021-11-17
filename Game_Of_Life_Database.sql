Create database GameOfLifedb
go
use GameOfLifedb
go
--drop table state
CREATE TABLE [state](
	stateID INT PRIMARY KEY
)
GO

--drop table cell
CREATE TABLE cell(
	state_id	INT	FOREIGN KEY REFERENCES[state](stateID),
	rownum INT	NOT null,
	columnNum INT	NOT null,
	id INT IDENTITY(1,1), --changed
	PRIMARY KEY(rownum, columnNum)
)
GO

ALTER TABLE [dbo].[cell]  WITH CHECK ADD  CONSTRAINT [state_id] FOREIGN KEY(state_ID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO


--drop table board
CREATE TABLE board(
	[sid]	INT	FOREIGN KEY REFERENCES[state](stateID) NOT NULL,  --changed
	rowsnum INT		NOT NULL,
	columnsnum INT	NOT NULL,
    PRIMARY KEY(rowsnum, columnsnum)
)
go

ALTER TABLE [dbo].[board]  WITH CHECK ADD  CONSTRAINT [sid] FOREIGN KEY(sID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

--drop table extras
CREATE TABLE Extras(
	stateid	INT	FOREIGN KEY REFERENCES[state](stateID) NOT NULL,
	score	INT,
	[counter] INT,
	speed FLOAT,
	PRIMARY KEY(stateid)
)
go

ALTER TABLE [dbo].[extras]  WITH CHECK ADD  CONSTRAINT [stateid] FOREIGN KEY(stateID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO
-------------------------------------------------------------------------------------------
--drop procedure SaveBoard

CREATE PROCEDURE SaveBoard
@gameID INT,
@gridColumns INT,
@gridRows INT,
@speed FLOAT,
@counter INT,
@score INT
--@flag int output
AS
BEGIN
	--saving the game id initially
	BEGIN
		INSERT INTO [dbo].[state](stateID) VALUES (@gameID)
	END
	BEGIN
		INSERT INTO [dbo].board
		(
		    [sid],
		    rowsnum,
		    columnsnum
		)
		VALUES
		(   @gameID, -- sid - int
		    @gridRows,    -- rowsnum - int
		    @gridColumns     -- columnsnum - int
		)
	END
	BEGIN
	INSERT INTO [dbo].Extras VALUES
	(   @gameID,   -- stateid - int
	    @score, -- score - int
		@counter,
		@speed
	)

    END
END
GO
------------------------------------------------------------------------------------
---Drop Procedure Load Board

CREATE PROCEDURE loadBoard
@gameID INT,
@GCols INT OUTPUT,
@GRows INT OUTPUT,
@speed FLOAT OUTPUT,
@counter INT OUTPUT,
@score INT OUTPUT

AS
BEGIN
     ----Check if state_id exists or not, which is going to be loaded
	 IF EXISTS(
	    SELECT @gameid
		FROM dbo.[state] S			
		WHERE S.stateID = @gameid
	)
	-------Loading rows and columns of board
	BEGIN
	     SELECT @GRows, @GCols
		 FROM dbo.board B
		 WHERE B.sid = @gameid AND @GCols = B.columnsnum AND @GRows = B.rowsnum
	END 
	-----Loading speed,counter and score of game 
	BEGIN
	     SELECT @speed,@counter,@score
		 FROM dbo.Extras E
		 WHERE E.stateid=@gameid AND @speed=E.speed AND @score=E.score AND @counter=E.counter
        END
END
--------------------------------------------------------------------------------------------------
----Drop Procedure Save Cell

CREATE PROCEDURE saveCell
@gameID INT,
@rowNum INT,
@colNum INT

AS
BEGIN
      ----Saving GameId, row number and column number of Cell
      BEGIN
	       INSERT INTO [dbo].cell
		(
		    [state_id],
		    rownum,
		    columnNum
		)
		VALUES
		(   @gameID, -- state_id - int
		    @rowNum,    -- rownum - int
		    @colNum    -- columnNum - int
		)

     END
END
---------------------------------------------------------------------------------------------



--drop procedure LoadCells

CREATE PROCEDURE LoadCells
@gameid INT,
@rownum INT OUTPUT,
@columnnum INT OUTPUT
--@flag int output
AS
BEGIN
	--checking if the state exists or not which is going to be loaded
	IF EXISTS ( 
		SELECT @gameid
		FROM dbo.[state] S			
		WHERE S.stateID = @gameid
	)	
	BEGIN
		SELECT @rownum, @columnnum
		FROM dbo.cell C
		WHERE C.state_id = @gameid AND @columnnum = C.columnNum AND @rownum = C.rownum
	END 
END

--------------------------------------------------------------------------

---- Drop Procedure to view Cell

CREATE PROCEDURE viewCell
@gameId INT,
@rowNums INT OUTPUT,
@colsNums INT OUTPUT

AS
BEGIN
      --checking if the state exists or not which is going to be viewed
	IF EXISTS ( 
		SELECT @gameId
		FROM dbo.[state] S			
		WHERE S.stateID = @gameId
	)	
	BEGIN
		SELECT @rowNums, @colsNums
		FROM dbo.cell C
		WHERE C.state_id = @gameid AND @colsNums = C.columnNum AND @rowNums = C.rownum
	END 
END

--------------------------------------------------------------------------------------------

-----Drop Procedure View Board
CREATE PROCEDURE viewBoard
@gameID INT,
@gridCols INT OUTPUT,
@gridRows INT OUTPUT,
@speed FLOAT OUTPUT,
@counter INT OUTPUT,
@score INT OUTPUT

AS
BEGIN
     ----Check if state_id exists or not, which is going to be viewed
	 IF EXISTS(
	    SELECT @gameID
		FROM dbo.[state] S			
		WHERE S.stateID = @gameID
	)
	-------view rows and columns of board
	BEGIN
	     SELECT @gridRows, @gridCols
		 FROM dbo.board B
		 WHERE B.sid = @gameID AND @gridCols = B.columnsnum AND @gridRows = B.rowsnum
	END 
	-----view speed,counter and score of game 
	BEGIN
	     SELECT @speed,@counter,@score
		 FROM dbo.Extras E
		 WHERE E.stateid=@gameid AND @speed=E.speed AND @score=E.score AND @counter=E.counter
    END
END
--------------------------------------------------------------------------------------------


