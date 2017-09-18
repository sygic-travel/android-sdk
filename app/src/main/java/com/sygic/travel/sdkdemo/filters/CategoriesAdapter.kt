package com.sygic.travel.sdkdemo.filters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sygic.travel.sdkdemo.R

class CategoriesAdapter(private val categoryClick: ViewHolder.CategoryClick) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val vItem = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.category_item, parent, false)
		return ViewHolder(vItem, categoryClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.tvCategory.text = categoriesNames[position]
	}

	override fun getItemCount(): Int {
		return categoriesNames.size
	}

	class ViewHolder(vItem: View, private val categoryClick: ViewHolder.CategoryClick) : RecyclerView.ViewHolder(vItem), View.OnClickListener {
		internal val tvCategory: TextView = vItem as TextView

		init {
			tvCategory.setOnClickListener(this)
		}

		override fun onClick(tvCategory: View) {
			categoryClick.onCategoryClick(
				categoriesKeys[adapterPosition],
				categoriesNames[adapterPosition]
			)
		}

		interface CategoryClick {
			fun onCategoryClick(categoryKey: String, categoryName: String)
		}
	}

	companion object {
		private val categoriesKeys = arrayOf("all", "sightseeing", "shopping", "eating", "discovering", "playing", "traveling", "going_out", "hiking", "sports", "relaxing")

		private val categoriesNames = arrayOf("All categories", "Sightseeing", "Shopping", "Restaurants", "Museums", "Family", "Transport", "Nightlife", "Outdoors", "Sports", "Relaxation")
	}
}
