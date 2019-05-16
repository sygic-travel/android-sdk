package com.sygic.travel.sdk.migrations

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.sygic.travel.sdk.common.database.migrations.migration1_2
import com.sygic.travel.sdk.common.database.migrations.migration2_3
import com.sygic.travel.sdk.common.database.migrations.migration3_4
import com.sygic.travel.sdk.common.database.migrations.migration4_5
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationsTest {
	@Test
	fun testOverallMigration() {
		val testHelper = MigrationTestHelper(
			InstrumentationRegistry.getInstrumentation(),
			com.sygic.travel.sdk.common.database.Database::class.qualifiedName,
			FrameworkSQLiteOpenHelperFactory()
		)

		val dbName = "test_db"
		testHelper.createDatabase(dbName, 1).close()
		testHelper.runMigrationsAndValidate(dbName, 2, true, migration1_2)
		testHelper.runMigrationsAndValidate(dbName, 3, true, migration2_3)
		testHelper.runMigrationsAndValidate(dbName, 4, true, migration3_4)
		testHelper.runMigrationsAndValidate(dbName, 5, true, migration4_5)
	}
}
