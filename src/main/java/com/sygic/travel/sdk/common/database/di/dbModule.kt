package com.sygic.travel.sdk.common.database.di

import android.arch.persistence.room.Room
import android.content.Context
import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.common.database.migrations.migration1_2
import com.sygic.travel.sdk.common.database.migrations.migration2_3
import com.sygic.travel.sdk.common.database.migrations.migration3_4
import org.koin.dsl.module.module

internal val dbModule = module {
	single {
		Room.databaseBuilder(
			get<Context>(),
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
