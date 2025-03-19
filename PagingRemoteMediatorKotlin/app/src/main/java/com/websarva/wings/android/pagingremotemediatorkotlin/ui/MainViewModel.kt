package com.websarva.wings.android.pagingremotemediatorkotlin.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.Phone
import com.websarva.wings.android.pagingremotemediatorkotlin.data.repository.PhoneRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {
	val phoneListFlow: Flow<PagingData<Phone>>
	private val _phoneRepository: PhoneRepository

	init {
		_phoneRepository = PhoneRepository(application)
		val phoneListPager = _phoneRepository.getAllPhoneListPager()
		phoneListFlow = phoneListPager.flow.cachedIn(viewModelScope)
	}
}