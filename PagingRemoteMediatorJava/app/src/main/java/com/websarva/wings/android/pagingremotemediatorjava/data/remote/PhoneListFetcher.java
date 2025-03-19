package com.websarva.wings.android.pagingremotemediatorjava.data.remote;


import com.websarva.wings.android.pagingremotemediatorjava.data.local.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PhoneListFetcher implements Callable<List<Phone>> {
	private final long _startKey;
	private final long _endKey;

	public PhoneListFetcher(long startKey, long endKey) {
		_startKey = startKey;
		if(endKey >= 334) {
			endKey = 334;
		}
		_endKey = endKey;
	}

	@Override
	public List<Phone> call() {
		List<Phone> extractedPhoneList = new ArrayList<>();
		for(long i = _startKey; i <= _endKey; i++) {
			int phoneNoInt = (int) (99999999 * Math.random());
			String phoneNo = "090" + String.format("%08d", phoneNoInt);
			Phone phone = new Phone();
			phone.id = i;
			phone.phoneNo = phoneNo;
			extractedPhoneList.add(phone);
		}
		return extractedPhoneList;
	}
}
