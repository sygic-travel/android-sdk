package com.sygic.travel.sdk.common.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val migration2_3 = object : Migration(2, 3) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("UPDATE `trip_day_items` SET `transport_mode` = 'PUBLIC_TRANSPORT' WHERE `transport_mode` = 'PUBLIC_TRANSIT'")
		database.execSQL("DELETE FROM `favorites` WHERE `id` LIKE ':%'")
	}
}
