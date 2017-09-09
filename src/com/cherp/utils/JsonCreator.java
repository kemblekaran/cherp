package com.cherp.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

public class JsonCreator {

	private Gson gson;

	public <E> void createJson(List<E> data, String filePath) {
		gson = new Gson();

		try {
			Writer writer = new FileWriter(filePath);
			// JsonWriter jw = new JsonWriter(writer);

			JsonElement je = gson.toJsonTree(data);

			JsonObject jo = new JsonObject();
			jo.add("data", je);

			System.out.println(jo.toString());

			gson.toJson(jo, writer);

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
