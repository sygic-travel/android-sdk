package com.sygic.travel.sdk.common.database.di

import android.arch.persistence.room.Room
import android.content.Context
import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.common.database.migrations.migration1_2
import com.sygic.travel.sdk.common.database.migrations.migration2_3
import com.sygic.travel.sdk.common.database.migrations.migration3_4
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton


internal val dbModule = Kodein.Module("dbModule") {
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
