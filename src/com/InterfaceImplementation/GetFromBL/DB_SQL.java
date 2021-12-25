package com.InterfaceImplementation.GetFromBL;


import com.BL.Game;
import com.FactoryImplementation.BL_Factory;
import com.Interfaces.SetToBL.DB_I;
import com.JSON_API.Simple_API;

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
	public void save(BL_Factory obj, String filename) {
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			String SQL1 = "INSERT INTO state(StateName) VALUES (?)";
			String SQL2 = "INSERT INTO board(StateName, rowsnum, columnsnum) VALUES (?, ?, ?)";
			String SQL3 = "INSERT INTO controls VALUES (?, ?, ?)";
			String SQL4 = "INSERT INTO cell(StateName, ID, rownum, columnnum) VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(SQL1);
			statement.setString(1, filename + ".txt");
			statement.executeUpdate();
			
			statement = connection.prepareStatement(SQL2);
			statement.setString(1, filename + ".txt");
			statement.setInt(2, obj.getBoard().getRows());
			statement.setInt(3, obj.getBoard().getColumns());
			statement.executeUpdate();
			
			statement = connection.prepareStatement(SQL3);
			statement.setString(1, filename + ".txt");
			statement.setInt(2, obj.getgenerations());
			statement.setInt(3, (int) obj.getControl().getSpeedFactor());
			statement.executeUpdate();
			int id = 1;
			for (int i = 0; i < obj.getBoard().getRows(); i++) {
				for (int j = 0; j < obj.getBoard().getColumns(); j++) {
					if (obj.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")).isAlive()) {
						statement = connection.prepareStatement(SQL4);
						statement.setString(1, filename + ".txt");
						statement.setInt(2, id);
						statement.setInt(3, obj.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")).getX());
						statement.setInt(4, obj.getBoard().getCell(Simple_API.StringToJSON(String.valueOf(i), "I"), Simple_API.StringToJSON(String.valueOf(j), "J")).getY());
						statement.executeUpdate();
						id++;
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
		Game obj = new Game(Simple_API.StringToJSON("20", "Row"), Simple_API.StringToJSON("75", "Column"));
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT rownum, columnnum FROM cell WHERE StateName=?");
			preparedStatement.setString(1, stateName);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
				obj.getBoard().setCell(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("rownum"))), "I"), Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("columnnum"))), "J"));
			
			String SQL = "SELECT Speed, Generations FROM controls WHERE StateName=?";
			//resultSet = statement.executeQuery("SELECT speed, generations FROM controls WHERE StateName=?");
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, stateName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				obj.getControl().setSpeedFactor(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("speed"))), "Speed"));
				obj.getControl().setScore(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("generations"))), "Score"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}