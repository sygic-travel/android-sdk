package com.sygic.travel.sdk.common.database.di

import android.arch.persistence.room.Room
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.common.database.migrations.migration1_2
import com.sygic.travel.sdk.common.database.migrations.migration2_3
import com.sygic.travel.sdk.common.database.migrations.migration3_4

internal val dbModule = Kodein.Module {
	bind<Database>() with singleton {
		Room.databaseBuilder(
			instance<Context>(),
			Database::class.java,
			"st-sdk-db"
		)
			.addMigrations(
				migration1_2,
				migration2_3,
				migration3_4
			)
			.build()
	}
}
