package com.sygic.travel.sdk.di

import android.arch.persistence.room.Room
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.favorites.db.StDb
import com.sygic.travel.sdk.favorites.service.FavoriteService

internal val dbModule = Kodein.Module {
	bind<FavoriteService>() with singleton {
		FavoriteService(
			Room.databaseBuilder(
				instance<Context>(),
				StDb::class.java,
				"st-sdk-db"
			).build())
	}
}
