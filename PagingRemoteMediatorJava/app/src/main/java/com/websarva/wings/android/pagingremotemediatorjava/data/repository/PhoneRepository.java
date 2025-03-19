package com.websarva.wings.android.pagingremotemediatorjava.data.repository;

import android.app.Application;

import androidx.annotation.OptIn;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import com.websarva.wings.android.pagingremotemediatorjava.data.local.AppDatabase;
import com.websarva.wings.android.pagingremotemediatorjava.data.local.Phone;
import com.websarva.wings.android.pagingremotemediatorjava.data.local.PhoneDAO;
import com.websarva.wings.android.pagingremotemediatorjava.data.remote.PhoneRemoteMediator;


public class PhoneRepository {
	private static final int ITEMS_PER_PAGE = 50;
	private AppDatabase _db;

	public PhoneRepository(Application application) {
		_db = AppDatabase.getDatabase(application);
	}

	@OptIn(markerClass = ExperimentalPagingApi.class)
	public Pager<Integer, Phone> getAllPhoneListPager() {
		PhoneDAO phoneDAO = _db.createPhoneDAO();
		PagingConfig pagingConfig = new PagingConfig(ITEMS_PER_PAGE);
		PhoneRemoteMediator phoneRemoteMediator = new PhoneRemoteMediator(_db);
		Pager<Integer, Phone> phoneListPager = new Pager<>(pagingConfig, null, phoneRemoteMediator, () -> phoneDAO.findAll());
		return phoneListPager;
	}
}
