package com.sygic.travel.sdk.common.database.migrations

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

val migration1_2 = object : Migration(1, 2) {
	override fun migrate(database: SupportSQLiteDatabase) {
		database.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` TEXT NOT NULL, `owner_id` TEXT NOT NULL, `name` TEXT, `version` INTEGER NOT NULL, `url` TEXT NOT NULL, `updated_at` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL, `privacy_level` TEXT NOT NULL, `starts_on` INTEGER, `days_count` INTEGER NOT NULL, `destinations` TEXT NOT NULL, `is_changed` INTEGER NOT NULL, `edit` INTEGER NOT NULL, `manage` INTEGER NOT NULL, `delete` INTEGER NOT NULL, `squareMediaId` TEXT, `squareUrlTemplate` TEXT, `landscapeMediaId` TEXT, `landscapeUrlTemplate` TEXT, `portraitId` TEXT, `portraitUrlTemplate` TEXT, `videoPreviewId` TEXT, `videoPreviewUrlTemplate` TEXT, PRIMARY KEY(`id`))")
		database.execSQL("CREATE TABLE IF NOT EXISTS `trip_days` (`trip_id` TEXT NOT NULL, `day_index` INTEGER NOT NULL, `note` TEXT, PRIMARY KEY(`trip_id`, `day_index`), FOREIGN KEY(`trip_id`) REFERENCES `trips`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
		database.execSQL("CREATE TABLE IF NOT EXISTS `trip_day_items` (`trip_id` TEXT NOT NULL, `day_index` INTEGER NOT NULL, `item_index` INTEGER NOT NULL, `placeId` TEXT NOT NULL, `startTime` INTEGER, `duration` INTEGER, `note` TEXT, `transport_mode` TEXT, `transport_avoid` TEXT, `transport_start_time` INTEGER, `transport_duration` INTEGER, `transport_note` TEXT, PRIMARY KEY(`trip_id`, `day_index`, `item_index`), FOREIGN KEY(`trip_id`) REFERENCES `trips`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )")
		database.execSQL("CREATE TABLE IF NOT EXISTS `favorites` (`id` TEXT NOT NULL, `state` INTEGER NOT NULL, PRIMARY KEY(`id`))")
		database.execSQL("INSERT INTO `favorites` SELECT id, 1 FROM `favorite`") // state 1 = mark all favorites for sync to server
		database.execSQL("DROP TABLE `favorite`")
	}
}
