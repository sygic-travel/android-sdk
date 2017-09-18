package com.sygic.travel.sdkdemo.filters

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.gallery.DividerDecoration

class CategoriesDialog(context: Context, private val onCategoryClick: CategoriesAdapter.ViewHolder.CategoryClick) : Dialog(context) {

	init {
		render()
	}

	private fun render() {
		setContentView(R.layout.categories_dialog)
		val rvCategories = findViewById<RecyclerView>(R.id.rv_categories)
		rvCategories.layoutManager = LinearLayoutManager(context)
		rvCategories.addItemDecoration(DividerDecoration(context, R.drawable.line_divider))
		rvCategories.adapter = CategoriesAdapter(onCategoryClick)
	}
}
