package com.sygic.travel.sdk.common.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val migration4_5 = object : Migration(4, 5) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("ALTER TABLE `trip_day_items` ADD COLUMN `transport_route_id` TEXT")
	}
}
