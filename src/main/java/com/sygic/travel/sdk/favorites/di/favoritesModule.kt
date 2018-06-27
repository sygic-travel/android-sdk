package com.sygic.travel.sdk.favorites.di

import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao
import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val favoritesModule = Kodein.Module("favoritesModule") {
	bind<FavoritesFacade>() with singleton {
		checkUserDataSupport(instance<Boolean>("userDataSupported"), "Favorites")
		FavoritesFacade(instance<FavoriteService>())
	}
	bind<FavoriteService>() with singleton { FavoriteService(instance<FavoriteDao>()) }
	bind<FavoriteDao>() with singleton { instance<Database>().favoriteDao() }
}
