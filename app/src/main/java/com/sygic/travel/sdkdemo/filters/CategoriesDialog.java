package com.sygic.travel.sdkdemo.filters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.gallery.DividerDecoration;

public class CategoriesDialog extends Dialog{
	private CategoriesAdapter.ViewHolder.CategoryClick onCategoryClick;

	public CategoriesDialog(@NonNull Context context, CategoriesAdapter.ViewHolder.CategoryClick onCategoryClick) {
		super(context);
		this.onCategoryClick = onCategoryClick;
		render();
	}

	private void render() {
		setContentView(R.layout.categories_dialog);
		RecyclerView rvCategories = (RecyclerView) findViewById(R.id.rv_categories);
		rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
		rvCategories.addItemDecoration(new DividerDecoration(getContext(), R.drawable.line_divider));
		rvCategories.setAdapter(new CategoriesAdapter(onCategoryClick));
	}
}
