package com.sygic.travel.sdk.common.database.migrations

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

internal val migration3_4 = object : Migration(3, 4) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("ALTER TABLE `trips` ADD COLUMN `is_user_subscribed` INTEGER NOT NULL DEFAULT 0")
	}
}
