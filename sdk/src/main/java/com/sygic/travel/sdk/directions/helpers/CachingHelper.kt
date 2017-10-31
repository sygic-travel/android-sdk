package com.sygic.travel.sdk.directions.helpers

import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CachingHelper<T> constructor(directory: File) {
	private val diskLruCache = DiskLruCache.open(directory, 1, 1, 1024 * 1024 * 10)!! // 10 MB

	fun put(key: String, objectToStore: T) {
		val editor: DiskLruCache.Editor?
		try {
			editor = diskLruCache.edit(key)
			if (editor == null) {
				return
			}

			val out = ObjectOutputStream(editor.newOutputStream(0))
			out.writeObject(objectToStore)
			out.close()
			editor.commit()
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}

	@Suppress("UNCHECKED_CAST")
	fun get(key: String): T? {
		val snapshot: DiskLruCache.Snapshot?

		try {
			snapshot = diskLruCache.get(key)
			if (snapshot == null) {
				return null
			}
			val outputStream = ObjectInputStream(snapshot.getInputStream(0))
			return outputStream.readObject() as? T
		} catch (e: IOException) {
			e.printStackTrace()
		} catch (ex: ClassNotFoundException) {
			ex.printStackTrace()
		}

		return null
	}
}
