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
	stateid	INT	FOREIGN KEY REFERENCES[state](stateID),
	rownum INT	NOT null,
	columnNum INT	NOT null,
	id INT,
	PRIMARY KEY(rownum, columnNum)
)
GO

ALTER TABLE [dbo].[cell]  WITH CHECK ADD  CONSTRAINT [stateid] FOREIGN KEY(stateID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO


--drop table board
CREATE TABLE board(
	stateid	INT	FOREIGN KEY REFERENCES[state](stateID),
	rowsnum INT		NOT NULL,
	columnsnum INT	NOT NULL,
    PRIMARY KEY(rowsnum, columnsnum)
)
go

ALTER TABLE [dbo].[board]  WITH CHECK ADD  CONSTRAINT [stateid] FOREIGN KEY(stateID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO

--drop table extras
CREATE TABLE Extras(
	stateid	INT	FOREIGN KEY REFERENCES[state](stateID),
	score	INT,
	PRIMARY KEY(stateid)
)
go

ALTER TABLE [dbo].[extras]  WITH CHECK ADD  CONSTRAINT [stateid] FOREIGN KEY(stateID)
REFERENCES [dbo].[state](stateid)
ON UPDATE CASCADE
ON DELETE CASCADE
GO
