package com.sygic.travel.sdk.api

import java.util.*

/**
 *
 * Determines the appropriate locale.
 */
object SupportedLanguages {
    private val supportedLanguages: MutableList<String>
    var EN = "en"

    /**
     * @return Device's current locale, if it is supported. Returns English locale otherwise.
     */
    val actualLocale: String
        get() {
            val locale = java.util.Locale.getDefault().language
            if (com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.contains(locale)) {
                return locale
            } else {
                return com.sygic.travel.sdk.api.SupportedLanguages.EN
            }
        }

    init {
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages = java.util.ArrayList<String>()
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("fr")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("de")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("es")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("nl")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("pt")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("it")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("ru")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("cs")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("sk")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("zh")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("ko")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("pl")
        com.sygic.travel.sdk.api.SupportedLanguages.supportedLanguages.add("tr")
    }
}
