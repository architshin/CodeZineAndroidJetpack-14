package com.websarva.wings.android.pagingremotemediatorjava.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Phone.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	private static AppDatabase _instance;

	public static AppDatabase getDatabase(Context context) {
		if (_instance == null) {
			_instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "phones_db").build();
		}
		return _instance;
	}

	public abstract PhoneDAO createPhoneDAO();
}
