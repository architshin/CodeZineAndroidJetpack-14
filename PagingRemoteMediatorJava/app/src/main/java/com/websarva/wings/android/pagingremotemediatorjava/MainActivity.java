package com.websarva.wings.android.pagingremotemediatorjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websarva.wings.android.pagingremotemediatorjava.data.local.Phone;
import com.websarva.wings.android.pagingremotemediatorjava.databinding.ActivityMainBinding;
import com.websarva.wings.android.pagingremotemediatorjava.ui.MainViewModel;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding _activityMainBinding;
	private MainViewModel _mainViewModel;
	private PhoneListAdapter _phoneListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		_activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
		View contentView = _activityMainBinding.getRoot();
		setContentView(contentView);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		ViewModelProvider provider = new ViewModelProvider(MainActivity.this);
		_mainViewModel = provider.get(MainViewModel.class);

		_activityMainBinding.rvPhoneList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
		DividerItemDecoration decoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
		_activityMainBinding.rvPhoneList.addItemDecoration(decoration);
		_phoneListAdapter = new PhoneListAdapter(new PhoneComparator());
		_activityMainBinding.rvPhoneList.setAdapter(_phoneListAdapter);

		LiveData<PagingData<Phone>> phoneListLiveData = _mainViewModel.getPhoneListLiveData();
		phoneListLiveData.observe(MainActivity.this, new PhoneListObserver());
	}

	private class PhoneListObserver implements Observer<PagingData<Phone>> {
		@Override
		public void onChanged(PagingData<Phone> phonePagingData) {
			_phoneListAdapter.submitData(getLifecycle(), phonePagingData);
		}
	}

	private class PhoneViewHolder extends RecyclerView.ViewHolder {
		private final TextView _tvId;
		private final TextView _tvPhoneNo;

		public PhoneViewHolder(View itemView) {
			super(itemView);
			_tvPhoneNo = itemView.findViewById(android.R.id.text1);
			_tvId = itemView.findViewById(android.R.id.text2);
		}

		public void bind(Phone item) {
			if(item != null) {
				_tvId.setText(String.valueOf(item.id));
				_tvPhoneNo.setText(item.phoneNo);
			}
		}
	}

	private class PhoneListAdapter extends PagingDataAdapter<Phone, PhoneViewHolder> {
		public PhoneListAdapter(@NonNull DiffUtil.ItemCallback<Phone> diffCallback) {
			super(diffCallback);
		}

		@NonNull
		@Override
		public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
			View row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
			return new PhoneViewHolder(row);
		}

		@Override
		public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
			Phone item = getItem(position);
			holder.bind(item);
		}
	}

	private class PhoneComparator extends DiffUtil.ItemCallback<Phone> {
		@Override
		public boolean areItemsTheSame(@NonNull Phone oldItem, @NonNull Phone newItem) {
			return oldItem.id == newItem.id;
		}

		@Override
		public boolean areContentsTheSame(@NonNull Phone oldItem, @NonNull Phone newItem) {
			return oldItem.id == newItem.id && oldItem.phoneNo.equals(newItem.phoneNo);
		}
	}
}
