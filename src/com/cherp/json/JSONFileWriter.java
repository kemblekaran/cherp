package com.cherp.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.cherp.entities.City;
import com.cherp.entities.Location;
import com.google.gson.stream.JsonWriter;

public class JSONFileWriter {

	private static String jsonPath = "F:\\server\\jsonfiles\\";
	static Writer writer;

	// method for writing into files
	public static Writer writeFiles(String fileName) {
		try {
			writer = new FileWriter(jsonPath + fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonCreator(List<T> list, Class<T> type) {

		String className = type.getSimpleName();
		
		// System.out.println("jsonList :" + jsonList.toString() + ",CLASS : " +
		// type.getSimpleName());

		// creates and writes into the jsonFile depending on the class Name
		switch (className) {
		
		// writes data of cities into city.json file at specified location
		case "City":
			System.out.println("In city switch");

			try {

				// writes into json file
				writeFiles("city.json");

				List<City> cityList = new ArrayList<>();
				cityList = (List<City>) list;
				System.out.println("cityList" + cityList);
				JsonWriter jw = new JsonWriter(writer);
				jw.beginObject();
				jw.name("data");
				jw.beginArray();
				for (City c : cityList) {
					jw.beginObject();
					jw.name("id").value(c.getId());
					jw.name("cityName").value(c.getCityName());
					jw.name("stateName").value(c.getStateName());
					jw.endObject();
				}
				jw.endArray();
				jw.endObject();
				jw.close();
			} catch (Exception e) {
			}

			break;

		// writes data of location into location.json file at specified location
		case "Location":
			System.out.println("In Location Servlet");

			try {
				writeFiles("location.json");

				List<Location> locList = new ArrayList<>();
				locList = (List<Location>) list;
				JsonWriter jw = new JsonWriter(writer);
				jw.beginObject();
				jw.name("data");
				jw.beginArray();
				for (Location l : locList) {
					jw.beginObject();
					jw.name("id").value(l.getId());
					jw.name("location").value(l.getLocation());

					jw.endObject();
				}
				jw.endArray();
				jw.endObject();
				jw.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;

		default:
			System.out.println("Default");
		}

		return null;
	}

}
