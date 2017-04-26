package com.sygic.travel.sdk.contentProvider.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>Determines the suitable locale.</p>
 */
public class SupportedLanguages {
	private static final List<String> supportedLanguages;
	public static String EN = "en";

	/**
	 * @return Device's current locale, if it is supported. Returns english locale otherwise.
	 */
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
