package com.websarva.wings.android.pagingremotemediatorkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.pagingremotemediatorkotlin.data.local.Phone
import com.websarva.wings.android.pagingremotemediatorkotlin.databinding.ActivityMainBinding
import com.websarva.wings.android.pagingremotemediatorkotlin.ui.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	private val _mainViewModel by viewModels<MainViewModel>()
	private lateinit var _activityMainBinding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		_activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(_activityMainBinding.root)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		_activityMainBinding.rvPhoneList.layoutManager = LinearLayoutManager(this@MainActivity)
		val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
		_activityMainBinding.rvPhoneList.addItemDecoration(decoration)
		val adapter = PhoneListAdapter(PhoneComparator())
		_activityMainBinding.rvPhoneList.adapter = adapter

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				_mainViewModel.phoneListFlow.collectLatest {
					adapter.submitData(it)
				}
			}
		}
	}

	private inner class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val _tvId: TextView
		private val _tvPhoneNo: TextView

		init {
			_tvId = itemView.findViewById(android.R.id.text2)
			_tvPhoneNo = itemView.findViewById(android.R.id.text1)
		}

		fun bind(item: Phone?) {
			if (item != null) {
				_tvId.text = item.id.toString()
				_tvPhoneNo.text = item.phoneNo
			}
		}
	}

	private inner class PhoneListAdapter(diffCallback: DiffUtil.ItemCallback<Phone>) : PagingDataAdapter<Phone, PhoneViewHolder>(diffCallback) {
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
			val inflater = LayoutInflater.from(this@MainActivity)
			val row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
			return PhoneViewHolder(row)
		}

		override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
			val item: Phone? = getItem(position)
			holder.bind(item)
		}
	}

	private class PhoneComparator : DiffUtil.ItemCallback<Phone>() {
		override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
			return oldItem.id == newItem.id
		}

		override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean {
			return oldItem == newItem
		}
	}
}
