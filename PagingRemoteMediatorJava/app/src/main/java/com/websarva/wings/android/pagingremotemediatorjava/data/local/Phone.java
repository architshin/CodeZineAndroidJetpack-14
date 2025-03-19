package com.websarva.wings.android.pagingremotemediatorjava.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
	@PrimaryKey
	public long id;
	@NonNull
	public String phoneNo;
}
