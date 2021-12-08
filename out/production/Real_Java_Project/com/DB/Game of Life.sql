DROP DATABASE GameOfLife;
CREATE DATABASE GameOfLife;
USE GameOfLife;

CREATE TABLE State (
	StateName VARCHAR(30) UNIQUE
);

CREATE TABLE Cell (
	StateName VARCHAR(30) NOT NULL,
    ID INT AUTO_INCREMENT,
    rownum INT NOT NULL,
    columnnum INT NOT NULL,
    PRIMARY KEY(ID, rownum, columnnum),
    CONSTRAINT FK_CellState FOREIGN KEY (StateName)
    REFERENCES State(StateName)
);

CREATE TABLE Board (
	StateName VARCHAR(30) NOT NULL,
    rowsnum INT NOT NULL,
    columnsnum INT NOT NULL,
    PRIMARY KEY(rowsnum, columnsnum),
    CONSTRAINT FK_BoardState FOREIGN KEY (StateName)
    REFERENCES State(StateName)
);

CREATE TABLE CONTROLS (
	StateName VARCHAR(30) NOT NULL,
    Generations INT DEFAULT 0,
    Speed FLOAT,
    PRIMARY KEY(StateName),
    CONSTRAINT FK_ControlState FOREIGN KEY (StateName)
    REFERENCES State(StateName)
);

DELIMITER //

CREATE PROCEDURE SaveBoard(
	State_Name VARCHAR(30),
    gridColumns INT,
    gridRows INT,
    speed FLOAT,
    generations INT
)
BEGIN
		INSERT INTO State(StateName) VALUES (State_Name);
        INSERT INTO Board(StateName, rowsnum, columnsnum) VALUES (State_Name, gridRows, gridColumns);
		INSERT INTO Controls VALUES (State_Name, generations, speed);
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE LoadBoard(
	State_Name VARCHAR(30),
    OUT GCols INT,
    OUT GRows INT,
    OUT speed FLOAT,
    OUT generations INT
)
BEGIN
	IF (EXISTS (SELECT * FROM State WHERE StateName=State_Name)) THEN
		SELECT S.StateName, B.rowsnum, B.columnsnum, C.speed, C.generations FROM State S
		JOIN Board B ON S.StateName=B.StateName
		JOIN Controls C ON B.StateName=C.StateName
		WHERE B.StateName=State_Name;
	END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE SaveCell (
	State_Name VARCHAR(30),
    rowNum INT,
    colNum INT
)
BEGIN
	INSERT INTO Cell(StateName, rownum, columnnum) VALUES (State_Name, rowNum, colNum);
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE LoadCells (
	State_Name VARCHAR(30),
    OUT rowNum INT,
    OUT columnNum INT
)
BEGIN
	IF (EXISTS (SELECT * FROM State WHERE StateName=State_Name)) THEN
        SELECT rownum, columnnum FROM Cell
		WHERE StateName=State_Name;
	END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE ViewCell(
	State_Name VARCHAR(30),
    OUT rowNum INT,
    OUT columnNum INT
)
BEGIN
	IF (EXISTS (SELECT * FROM State WHERE StateName=State_Name)) THEN
		SELECT rownum, columnnum FROM Cell
        WHERE StateName=State_Name;
    END IF;
END //

DELIMITER ;


DELIMITER //
CREATE PROCEDURE ViewBoard (
	State_Name VARCHAR(30),
    OUT gridCols INT,
	OUT gridRows INT,
	OUT speed FLOAT,
	OUT generations INT
)
BEGIN
	IF (EXISTS (SELECT * FROM State WHERE StateName=State_Name)) THEN
		SELECT rowsnum, columnsnum FROM Board B
        WHERE B.StateName=State_Name;
        
        SELECT C.speed, C.generations FROM Controls C
        WHERE C.StateName=State_Name;
    END IF;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE DeleteState (
	State_Name VARCHAR(30),
    OUT Flag INT
)
BEGIN
	IF (EXISTS (SELECT * FROM State WHERE StateName=State_Name)) THEN
		DELETE FROM State
        WHERE StateName=State_Name;
        SET Flag = 1;
	ELSE
		SET Flag = -1;
    END IF;
END //
DELIMITER ;


