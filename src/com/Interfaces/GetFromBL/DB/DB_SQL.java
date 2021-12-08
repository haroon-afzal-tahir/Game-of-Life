package com.Interfaces.GetFromBL.DB;


import com.BL.Game;
import com.Interfaces.DB_I;

import java.sql.*;

public class DB_SQL implements DB_I {
	String jdbcUrl = "jdbc:mysql://localhost:3306/gameoflife";
	String username = "root";
	String password = "zxasqw123edc";
	
	@Override
	public void delete(String StateName) {
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			String SQL = "DELETE FROM state WHERE StateName=?";
			
			PreparedStatement statement = connection.prepareStatement(SQL);
			
			statement.setString(1, StateName);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(Game obj, String filename) {
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			String SQL1 = "INSERT INTO state(StateName) VALUES (?)";
			String SQL2 = "INSERT INTO board(StateName, rowsnum, columnsnum) VALUES (?, ?, ?)";
			String SQL3 = "INSERT INTO controls VALUES (?, ?, ?)";
			String SQL4 = "INSERT INTO cell(StateName, rownum, columnnum) VALUES (?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(SQL1);
			statement.setString(1, filename + ".txt");
			statement.executeUpdate();
			
			statement = connection.prepareStatement(SQL2);
			statement.setString(1, filename + ".txt");
			statement.setInt(2, obj.getBoard().getRows());
			statement.setInt(3, obj.getCols());
			statement.executeUpdate();
			
			statement = connection.prepareStatement(SQL3);
			statement.setString(1, filename + ".txt");
			statement.setInt(2, obj.getgenerations());
			statement.setInt(3, (int) obj.getControl().getSpeedFactor());
			statement.executeUpdate();
			
			for (int i = 0; i < obj.getBoard().getRows(); i++) {
				for (int j = 0; j < obj.getBoard().getColumns(); j++) {
					if (obj.getBoard().getCell(i, j).isAlive()) {
						statement = connection.prepareStatement(SQL4);
						statement.setString(1, filename + ".txt");
						statement.setInt(2, obj.getBoard().getCell(i, j).getX());
						statement.setInt(3, obj.getBoard().getCell(i, j).getY());
						statement.executeUpdate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String view() {
		String result = "";
		
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM state");
			
			while (resultSet.next()) {
				result = result + resultSet.getString("StateName") + "\n";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public Game load(String stateName) {
		Game obj = new Game(20, 75);
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT rownum, columnnum FROM cell ");
			while (resultSet.next())
				obj.getBoard().setCell(Integer.parseInt(resultSet.getString("rownum")), Integer.parseInt(resultSet.getString("columnnum")));
			
			resultSet = statement.executeQuery("SELECT speed, generations FROM controls WHERE StateName=?");
			while (resultSet.next())
				obj.getControl().setSpeedFactor(Integer.parseInt(resultSet.getString("speed")));
			obj.getControl().setScore(Integer.parseInt(resultSet.getString("generations")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}