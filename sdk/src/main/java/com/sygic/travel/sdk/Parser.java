package com.sygic.travel.sdk;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Parser {
	private static Gson gson = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	public static <PT> PT parseJson(String json, Type typeToken) {
		return gson.fromJson(json, typeToken);
	}
}
