package com.websarva.wings.android.pagingremotemediatorkotlin.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhoneDAO {
	@Query("SELECT * FROM phones ORDER BY id")
	fun findAll(): PagingSource<Int, Phone>
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPhoneList(phones: List<Phone>)
	@Query("DELETE FROM phones")
	suspend fun deleteAll()
	@Query("SELECT MAX(id) FROM phones")
	suspend fun findLastId(): Long
}
