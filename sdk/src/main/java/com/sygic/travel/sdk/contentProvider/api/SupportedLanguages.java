package com.sygic.travel.sdk.contentProvider.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by michal.murin on 16.2.2017.
 */

public class SupportedLanguages {
	private static final List<String> supportedLanguages;
	public static String EN = "en";

	public static String getAvailableLanguage(String locale){
		if(!supportedLanguages.contains(locale)){
			return "en";
		} else {
			return locale;
		}
	}

	public static String getActualLocale(){
		String locale = Locale.getDefault().getLanguage();
		if(supportedLanguages.contains(locale)){
			return locale;
		} else {
			return EN;
		}
	}

	static {
		supportedLanguages = new ArrayList<>();
		supportedLanguages.add("fr");
		supportedLanguages.add("de");
		supportedLanguages.add("es");
		supportedLanguages.add("nl");
		supportedLanguages.add("pt");
		supportedLanguages.add("it");
		supportedLanguages.add("ru");
		supportedLanguages.add("cs");
		supportedLanguages.add("sk");
		supportedLanguages.add("zh");
		supportedLanguages.add("ko");
		supportedLanguages.add("pl");
		supportedLanguages.add("tr");

	}
}
