package com.DB;


import com.BL.DB_I;
import com.JSON_API.Simple_API;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;

public class DB_SQL implements DB_I {
	String jdbcUrl = "jdbc:mysql://localhost:3306/gameoflife";
	String username = "root";
	String password = "zxasqw123edc";
	
	@Override
	public void delete(JSONObject StateName) {
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			String SQL = "DELETE FROM state WHERE StateName=?";
			
			PreparedStatement statement = connection.prepareStatement(SQL);
			
			statement.setString(1, Simple_API.JSONToString(StateName));
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void save(JSONObject obj, JSONObject filename) {
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			String SQL1 = "INSERT INTO state(StateName) VALUES (?)";
			String SQL2 = "INSERT INTO board(StateName, rowsnum, columnsnum) VALUES (?, ?, ?)";
			String SQL3 = "INSERT INTO controls VALUES (?, ?, ?)";
			String SQL4 = "INSERT INTO cell(StateName, ID, rownum, columnnum) VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(SQL1);
			statement.setString(1, Simple_API.JSONToString(filename) + ".txt");
			statement.executeUpdate();
			
			statement = connection.prepareStatement(SQL2);
			statement.setString(1, Simple_API.JSONToString(filename) + ".txt");
			statement.setInt(2, 20);
			statement.setInt(3, 75);
			statement.executeUpdate();
			
			
			statement = connection.prepareStatement(SQL3);
			statement.setString(1, Simple_API.JSONToString(filename) + ".txt");
			statement.setInt(2, (Integer) obj.get("Generations"));
			statement.setInt(3, Math.round((Float) obj.get("Speed")));
			statement.executeUpdate();
			int id = 1;
			JSONArray jsonArray = (JSONArray) obj.get("AliveCells");
			for (int i = 0; i < jsonArray.size(); i = i + 2) {
				statement = connection.prepareStatement(SQL4);
				statement.setString(1, Simple_API.JSONToString(filename) + ".txt");
				statement.setInt(2, id++);
				statement.setInt(3, (Integer) jsonArray.get(i + 0));
				statement.setInt(4, (Integer) jsonArray.get(i + 1));
				statement.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public JSONObject view() {
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
		
		return Simple_API.StringToJSON(result, "States");
	}
	
	@Override
	public JSONObject load(JSONObject stateName) {
		JSONObject jsonObject = new JSONObject();
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			Statement statement = connection.createStatement();
			
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT rownum, columnnum FROM cell WHERE StateName=?");
			preparedStatement.setString(1, Simple_API.JSONToString(stateName));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			JSONArray jsonArray = new JSONArray();
			while (resultSet.next()) {
				jsonArray.add(Integer.parseInt(resultSet.getString("rownum")));
				jsonArray.add(Integer.parseInt(resultSet.getString("columnnum")));
				//obj.getBoard().setCell(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("rownum"))), "I"), Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("columnnum"))), "J"));
			}
			jsonObject.put("AliveCells", jsonArray);
			
			String SQL = "SELECT Speed, Generations FROM controls WHERE StateName=?";
			//resultSet = statement.executeQuery("SELECT speed, generations FROM controls WHERE StateName=?");
			preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, Simple_API.JSONToString(stateName));
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				jsonObject.put("Speed", Integer.parseInt(resultSet.getString("Speed")));
				jsonObject.put("Generations", Integer.parseInt(resultSet.getString("Generations")));
				//obj.getControl().setSpeedFactor(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("speed"))), "Speed"));
				//obj.getControl().setScore(Simple_API.StringToJSON(String.valueOf(Integer.parseInt(resultSet.getString("generations"))), "Score"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}