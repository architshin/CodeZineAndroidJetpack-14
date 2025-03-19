package com.websarva.wings.android.pagingremotemediatorjava.data.local;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface PhoneDAO {
	@Query("SELECT * FROM phones ORDER BY id")
	PagingSource<Integer, Phone> findAll();
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	ListenableFuture<List<Long>> insertPhoneList(List<Phone> phones);
	@Query("DELETE FROM phones")
	ListenableFuture<Integer> deleteAll();
	@Query("SELECT MAX(id) FROM phones")
	ListenableFuture<Long> findLastId();
}
