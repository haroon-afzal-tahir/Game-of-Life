Create database GameOfLife
go
use GameOfLife
go
--drop table state;
CREATE TABLE [state](
	StateName	VARCHAR(50)	PRIMARY KEY
)
GO

--drop table cell;
CREATE TABLE cell(
	s_name	VARCHAR(50)	FOREIGN KEY REFERENCES[state](StateName),
	rownum INT	NOT null,
	columnNum INT	NOT null,
	id INT IDENTITY(1,1), --changed
	PRIMARY KEY(rownum, columnNum)
)
GO

ALTER TABLE [dbo].[cell]  WITH CHECK ADD  CONSTRAINT [s_name] FOREIGN KEY(s_name)
REFERENCES [dbo].[state](StateName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO


--drop table board;
CREATE TABLE board(
	[sname]	VARCHAR(50)	FOREIGN KEY REFERENCES[state](StateName) NOT NULL,  --changed
	rowsnum INT		NOT NULL,
	columnsnum INT	NOT NULL,
    PRIMARY KEY(rowsnum, columnsnum)
)
go

ALTER TABLE [dbo].[board]  WITH CHECK ADD  CONSTRAINT [sname] FOREIGN KEY(sname)
REFERENCES [dbo].[state](StateName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

--drop table Controls;
CREATE TABLE Controls(
	S_Nam	VARCHAR(50)	FOREIGN KEY REFERENCES[state](StateName) NOT NULL,
	[generations] INT,
	speed FLOAT,
	PRIMARY KEY(S_Nam)
)
go

ALTER TABLE [dbo].[Controls]  WITH CHECK ADD  CONSTRAINT [S_Nam] FOREIGN KEY(S_Nam)
REFERENCES [dbo].[state](StateName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO
-------------------------------------------------------------------------------------------
--drop procedure SaveBoard;

CREATE PROCEDURE SaveBoard
@State_Name VARCHAR(50),
@gridColumns INT,
@gridRows INT,
@speed FLOAT,
@generations INT
--@flag int output
AS
BEGIN
	--saving the game id initially
	BEGIN
		INSERT INTO [dbo].[state](StateName) VALUES ( @State_Name )
	END
	BEGIN
		INSERT INTO [dbo].board
		(
		    sname,
		    rowsnum,
		    columnsnum
		)
		VALUES
		(   @State_Name , -- sid - int
		    @gridRows,    -- rowsnum - int
		    @gridColumns     -- columnsnum - int
		)
	END
	BEGIN
	INSERT INTO [dbo].Controls VALUES
	(   @State_Name ,   -- stateid - int
		@generations,
		@speed
	)

    END
END
GO
------------------------------------------------------------------------------------
---Drop Procedure Load Board;

CREATE PROCEDURE loadBoard
@State_Name VARCHAR(50) ,
@GCols INT OUTPUT,
@GRows INT OUTPUT,
@speed FLOAT OUTPUT,
@generations INT OUTPUT

AS
BEGIN
     ----Check if state_id exists or not, which is going to be loaded
	 IF EXISTS(
	    SELECT  @State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  @State_Name 
	)
	-------Loading rows and columns of board
	BEGIN
	     SELECT @GRows, @GCols
		 FROM dbo.board B
		 WHERE B.sname =  @State_Name  AND @GCols = B.columnsnum AND @GRows = B.rowsnum
	END 
	-----Loading speed,generations of game 
	BEGIN
	     SELECT @speed,@generations
		 FROM dbo.Controls E
		 WHERE E.S_Nam = @State_Name  AND @speed=E.speed AND @generations=E.generations
        END
END
--------------------------------------------------------------------------------------------------
----Drop Procedure Save Cell;

CREATE PROCEDURE saveCell
@State_Name VARCHAR(50),
@rowNum INT,
@colNum INT

AS
BEGIN
      ----Saving GameId, row number and column number of Cell
      BEGIN
	       INSERT INTO [dbo].cell
		(
		    s_name,
		    rownum,
		    columnNum
		)
		VALUES
		(   @State_Name , -- state_id - int
		    @rowNum,    -- rownum - int
		    @colNum    -- columnNum - int
		)

     END
END
---------------------------------------------------------------------------------------------



--drop procedure LoadCells;

CREATE PROCEDURE LoadCells
@State_Name VARCHAR(50),
@rownum INT OUTPUT,
@columnnum INT OUTPUT
--@flag int output
AS
BEGIN
	--checking if the state exists or not which is going to be loaded
	IF EXISTS ( 
		SELECT  @State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  @State_Name 
	)	
	BEGIN
		SELECT @rownum, @columnnum
		FROM dbo.cell C
		WHERE C.s_name =  @State_Name  AND @columnnum = C.columnNum AND @rownum = C.rownum
	END 
END

--------------------------------------------------------------------------

---- Drop Procedure to view Cell;

CREATE PROCEDURE viewCell
 @State_Name VARCHAR(50) ,
@rowNums INT OUTPUT,
@colsNums INT OUTPUT

AS
BEGIN
      --checking if the state exists or not which is going to be viewed
	IF EXISTS ( 
		SELECT  @State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  @State_Name 
	)	
	BEGIN
		SELECT @rowNums, @colsNums
		FROM dbo.cell C
		WHERE C.s_name =  @State_Name  AND @colsNums = C.columnNum AND @rowNums = C.rownum
	END 
END

--------------------------------------------------------------------------------------------

-----Drop Procedure View Board;
CREATE PROCEDURE viewBoard
@State_Name VARCHAR(50),
@gridCols INT OUTPUT,
@gridRows INT OUTPUT,
@speed FLOAT OUTPUT,
@generations INT OUTPUT

AS
BEGIN
     ----Check if state_id exists or not, which is going to be viewed
	 IF EXISTS(
	    SELECT  @State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  @State_Name 
	)
	-------view rows and columns of board
	BEGIN
	     SELECT @gridRows, @gridCols
		 FROM dbo.board B
		 WHERE B.sname =  @State_Name  AND @gridCols = B.columnsnum AND @gridRows = B.rowsnum
	END 
	-----view speed,generations of game 
	BEGIN
	     SELECT @speed, @generations
		 FROM dbo.Controls E
		 WHERE E.S_Nam = @State_Name  AND @speed=E.speed AND @generations = E.generations
    END
END
--------------------------------------------------------------------------------------------

--drop procedure delete_state;
CREATE PROCEDURE delete_state
@State_Name VARCHAR(50),
@flag int output
AS
BEGIN
	
	--checking if the game id exists or not
	IF EXISTS ( 
		SELECT  @State_Name 
		FROM dbo.[state] S			
		WHERE S.StateName =  @State_Name 
	)	
	BEGIN 
	DELETE FROM dbo.state 
	WHERE StateName =  @State_Name 
	SET @flag = 1
	END 
	ELSE
    BEGIN
	SET @flag = -1
    END
END
