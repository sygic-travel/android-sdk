package com.sygic.travel.sdk.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import com.sygic.travel.sdk.BuildConfig


object KodeinSetup {

	fun setupKodein(context: Context, xApiKey: String) = Kodein {
		constant("apiKey") with xApiKey
		constant("isInDebugMode") with BuildConfig.DEBUG

		bind<Context>() with singleton { context }

		import(generalModule)
		import(sygicTravelApiModule)
		import(placesModule)
		import(toursModule)
		import(dbModule)
		import(favoritesModule)
	}
}
