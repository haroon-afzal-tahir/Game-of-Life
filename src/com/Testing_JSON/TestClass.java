package com.Testing_JSON;

import com.JSON_API.Simple_API;
import org.json.simple.JSONObject;

import java.io.IOException;

public class TestClass {
	public static void main(String[] args) throws IOException {
		JSONObject jsonObject = Simple_API.StringToJSON("0.15", "Float");
		float num = Float.parseFloat(Simple_API.JSONToString(jsonObject));
		System.out.println(num);
	}
}