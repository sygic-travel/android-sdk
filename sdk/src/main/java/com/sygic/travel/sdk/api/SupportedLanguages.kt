package com.sygic.travel.sdk.api

import java.util.ArrayList
import java.util.Locale

/**
 * Determines the appropriate locale.
 */
internal object SupportedLanguages {
	private val supportedLanguages: MutableList<String>
	var EN = "en"

	/**
	 * @return Device's current locale, if it is supported. Returns English locale otherwise.
	 */
	val actualLocale: String
		get() {
			val locale = Locale.getDefault().language
			if (supportedLanguages.contains(locale)) {
				return locale
			} else {
				return EN
			}
		}

	init {
		supportedLanguages = ArrayList<String>()
		supportedLanguages.add("fr")
		supportedLanguages.add("de")
		supportedLanguages.add("es")
		supportedLanguages.add("nl")
		supportedLanguages.add("pt")
		supportedLanguages.add("it")
		supportedLanguages.add("ru")
		supportedLanguages.add("cs")
		supportedLanguages.add("sk")
		supportedLanguages.add("zh")
		supportedLanguages.add("ko")
		supportedLanguages.add("pl")
		supportedLanguages.add("tr")
	}
}
