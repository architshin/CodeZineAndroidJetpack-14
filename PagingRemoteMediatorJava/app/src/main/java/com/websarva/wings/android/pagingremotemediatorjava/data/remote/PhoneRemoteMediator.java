package com.websarva.wings.android.pagingremotemediatorjava.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.paging.ExperimentalPagingApi;
import androidx.paging.ListenableFutureRemoteMediator;
import androidx.paging.LoadType;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.websarva.wings.android.pagingremotemediatorjava.data.local.AppDatabase;
import com.websarva.wings.android.pagingremotemediatorjava.data.local.Phone;
import com.websarva.wings.android.pagingremotemediatorjava.data.local.PhoneDAO;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@OptIn(markerClass = ExperimentalPagingApi.class)
public class PhoneRemoteMediator extends ListenableFutureRemoteMediator<Integer, Phone> {
	private final AppDatabase _db;

	public PhoneRemoteMediator(AppDatabase db) {
		_db = db;
	}

	@NotNull
	@Override
	public ListenableFuture<MediatorResult> loadFuture(@NonNull LoadType loadType, @NonNull PagingState<Integer, Phone> pagingState) {
		ListenableFuture<MediatorResult> returnVal;
		PhoneDAO phoneDAO = _db.createPhoneDAO();
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {
			switch(loadType) {
				case REFRESH:
					ListenableFuture<Integer> deleteResultFuture = phoneDAO.deleteAll();
					deleteResultFuture.get();
					returnVal = Futures.immediateFuture(new MediatorResult.Success(false));
					break;
				case PREPEND:
					returnVal = Futures.immediateFuture(new MediatorResult.Success(true));
					break;
				default:
					ListenableFuture<Long> lastIdFuture = phoneDAO.findLastId();
					Long lastIdL = lastIdFuture.get();
					long lastId = 0;
					if(lastIdL != null) {
						lastId = lastIdL;
					}
					long startKey = lastId + 1;
					long endKey = startKey + pagingState.getConfig().pageSize - 1;
					PhoneListFetcher phoneListFetcher = new PhoneListFetcher(startKey, endKey);
					Future<List<Phone>> phoneListFuture = executorService.submit(phoneListFetcher);
					List<Phone> phoneList = phoneListFuture.get();
					ListenableFuture<List<Long>> insertResultFuture = phoneDAO.insertPhoneList(phoneList);
					insertResultFuture.get();
					PhoneListSizeFetcher phoneListSizeFetcher = new PhoneListSizeFetcher();
					Future<Integer> listSizeFuture = executorService.submit(phoneListSizeFetcher);
					int listSize = listSizeFuture.get();
					MediatorResult result;
					if (endKey < listSize) {
						result = new MediatorResult.Success(false);
					}
					else {
						result = new MediatorResult.Success(true);
					}
					returnVal = Futures.immediateFuture(result);
					break;
			}
		}
		catch(Exception ex) {
			Log.e("PhoneRemoteMediator", "例外発生", ex);
			returnVal = Futures.immediateFuture(new MediatorResult.Error(ex));
		}
		return returnVal;
	}
}
