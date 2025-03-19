package com.websarva.wings.android.pagingremotemediatorkotlin.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.AppDatabase
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.Phone

@OptIn(ExperimentalPagingApi::class)
class PhoneRemoteMediator(
	private val _db: AppDatabase
) : RemoteMediator<Int, Phone>() {
	override suspend fun load(loadType: LoadType, state: PagingState<Int, Phone>): MediatorResult {
		var returnVal: MediatorResult
		val phoneDAO = _db.createPhoneDAO()
		try {
			when(loadType) {
				LoadType.REFRESH -> {
					phoneDAO.deleteAll()
					returnVal = MediatorResult.Success(false)
				}
				LoadType.PREPEND -> {
					returnVal = MediatorResult.Success(true)
				}
				LoadType.APPEND -> {
					val lastId = phoneDAO.findLastId()
					val startKey = lastId + 1
					val endKey = startKey + state.config.pageSize - 1
					val fetchedPhoneList = fetchPhoneList(startKey, endKey)
					phoneDAO.insertPhoneList(fetchedPhoneList)
					val listSize = fetchPhoneListSize()
					if(endKey < listSize) {
						returnVal = MediatorResult.Success(false)
					}
					else {
						returnVal = MediatorResult.Success(true)
					}
				}
			}
		}
		catch(ex: Exception) {
			Log.e("PhoneRemoteMediator", "例外発生", ex)
			returnVal = MediatorResult.Error(ex)
		}
		return returnVal
	}
}
