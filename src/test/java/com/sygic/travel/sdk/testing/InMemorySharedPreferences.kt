package com.sygic.travel.sdk.testing

import android.content.SharedPreferences

internal class InMemorySharedPreferences : SharedPreferences {
	private val data = mutableMapOf<String, Any?>()
	private val editor = InMemorySharedPreferencesEditor(data)

	override fun contains(key: String): Boolean {
		return data.contains(key)
	}

	override fun getBoolean(key: String, defValue: Boolean): Boolean {
		return data.getOrDefault(key, defValue) as Boolean
	}

	override fun getInt(key: String, defValue: Int): Int {
		return data.getOrDefault(key, defValue) as Int
	}

	override fun getAll(): MutableMap<String, *> {
		return data
	}

	override fun edit(): SharedPreferences.Editor {
		return editor
	}

	override fun getLong(key: String, defValue: Long): Long {
		return data.getOrDefault(key, defValue) as Long
	}

	override fun getFloat(key: String, defValue: Float): Float {
		return data.getOrDefault(key, defValue) as Float
	}

	override fun getStringSet(key: String, defValues: MutableSet<String>?): MutableSet<String>? {
		@Suppress("UNCHECKED_CAST")
		return data.getOrDefault(key, defValues) as MutableSet<String>?
	}

	override fun getString(key: String, defValue: String?): String? {
		return data.getOrDefault(key, defValue) as String?
	}

	override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
		TODO()
	}

	override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
		TODO()
	}

	class InMemorySharedPreferencesEditor(
		private val data: MutableMap<String, Any?>
	) : SharedPreferences.Editor {
		override fun clear(): SharedPreferences.Editor {
			data.clear()
			return this
		}

		override fun putLong(key: String, value: Long): SharedPreferences.Editor {
			data[key] = value
			return this
		}

		override fun putInt(key: String, value: Int): SharedPreferences.Editor {
			data[key] = value
			return this
		}

		override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
			data[key] = value
			return this
		}

		override fun putStringSet(key: String, values: MutableSet<String>?): SharedPreferences.Editor {
			data[key] = values
			return this
		}

		override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
			data[key] = value
			return this
		}

		override fun putString(key: String, value: String?): SharedPreferences.Editor {
			data[key] = value
			return this
		}

		override fun remove(key: String): SharedPreferences.Editor {
			data.remove(key)
			return this
		}

		override fun commit(): Boolean {
			return true
		}

		override fun apply() {
		}
	}
}
