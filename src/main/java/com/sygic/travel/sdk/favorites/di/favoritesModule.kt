package com.sygic.travel.sdk.favorites.di

import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.koin.dsl.module

internal val favoritesModule = module {
	single {
		checkUserDataSupport("Favorites")
		FavoritesFacade(get<FavoriteService>())
	}
	single {
		FavoriteService(get<FavoriteDao>())
	}
	single {
		get<Database>().favoriteDao()
	}
}
