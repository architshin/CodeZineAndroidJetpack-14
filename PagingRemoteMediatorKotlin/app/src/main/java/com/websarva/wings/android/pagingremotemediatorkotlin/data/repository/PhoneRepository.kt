package com.websarva.wings.android.pagingremotemediatorkotlin.data.repository

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.AppDatabase
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.Phone
import com.websarva.wings.android.pagingremotemediatorkotlin.data.remote.PhoneRemoteMediator

private const val ITEMS_PER_PAGE = 50

class PhoneRepository(application: Application) {
	private val _db: AppDatabase

	init {
		_db = AppDatabase.getDatabase(application)
	}

	@OptIn(ExperimentalPagingApi::class)
	fun getAllPhoneListPager(): Pager<Int, Phone> {
		val phoneDAO = _db.createPhoneDAO()
		val pagingConfig = PagingConfig(ITEMS_PER_PAGE)
		val phoneListPager = Pager(pagingConfig, null, PhoneRemoteMediator(_db)) { phoneDAO.findAll() }
		return phoneListPager
	}
}
