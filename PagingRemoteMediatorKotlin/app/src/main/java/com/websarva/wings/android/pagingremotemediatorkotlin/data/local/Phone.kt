package com.websarva.wings.android.pagingremotemediatorkotlin.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phones")
data class Phone(
	@PrimaryKey
	val id: Long,
	val phoneNo: String
)
