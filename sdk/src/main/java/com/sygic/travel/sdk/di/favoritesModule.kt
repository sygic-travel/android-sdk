package com.sygic.travel.sdk.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.favorites.facade.FavoritesFacade
import com.sygic.travel.sdk.favorites.service.FavoriteService

internal val favoritesModule = Kodein.Module {
	bind<FavoritesFacade>() with singleton { FavoritesFacade(instance<FavoriteService>()) }
}
