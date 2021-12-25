package com.JSON_API;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.StringWriter;

public class Simple_API {
	private static String getSecondElement(String element, int position) {
		return element.substring(element.indexOf(':') + position, element.length() - position);
	}
	
	public static JSONObject StringToJSON(String child, String key) {
		JSONObject object = new JSONObject();
		try {
			object.put(key, child);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static String JSONToString(JSONObject doc) {
		return getSecondElement(doc.toString(), 2);
	}
	
	public static JSONObject BooleanToJSON(Boolean state, String key) {
		JSONObject object = new JSONObject();
		try {
			object.put(key, state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static Boolean JSONToBoolean(JSONObject doc) {
		return Boolean.parseBoolean(getSecondElement(doc.toString(), 1));
	}
	
	public static JSONObject BooleanArrayToJSON(Boolean[][] array, String key) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 75; j++) {
					jsonArray.add(array[i][j]);
				}
			}
			jsonObject.put(key, jsonArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static Boolean[][] JSONToBooleanArray(JSONObject doc, String key) {
		Boolean[][] status = new Boolean[20][75];
		try {
			JSONArray jsonArray = (JSONArray) doc.get(key);
			for (int i = 0, index = 0; i < 20; i++) {
				for (int j = 0; j < 75; j++, index++) {
					status[i][j] = (Boolean) jsonArray.get(index);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static void printJSON(JSONObject doc) throws IOException {
		StringWriter out = new StringWriter();
		doc.writeJSONString(out);
		
		String jsonText = out.toString();
		System.out.println(jsonText);
	}
}