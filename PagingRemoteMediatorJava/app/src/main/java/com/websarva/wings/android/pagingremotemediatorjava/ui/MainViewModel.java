package com.websarva.wings.android.pagingremotemediatorjava.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.websarva.wings.android.pagingremotemediatorjava.data.local.Phone;
import com.websarva.wings.android.pagingremotemediatorjava.data.repository.PhoneRepository;

import kotlinx.coroutines.CoroutineScope;

public class MainViewModel extends AndroidViewModel {
	private PhoneRepository _phoneRepository;
	private LiveData<PagingData<Phone>> _phoneListLiveData;

	public MainViewModel(@NonNull Application application) {
		super(application);
		_phoneRepository = new PhoneRepository(application);

		Pager<Integer, Phone> phoneListPager = _phoneRepository.getAllPhoneListPager();
		CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(MainViewModel.this);
		_phoneListLiveData = PagingLiveData.cachedIn(PagingLiveData.getLiveData(phoneListPager), viewModelScope);
	}

	public LiveData<PagingData<Phone>> getPhoneListLiveData() {
		return _phoneListLiveData;
	}
}
