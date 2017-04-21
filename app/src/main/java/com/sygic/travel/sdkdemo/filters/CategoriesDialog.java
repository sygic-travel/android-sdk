package com.sygic.travel.sdkdemo.filters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sygic.travel.sdkdemo.R;

/**
 * Created by michal.murin on 21.4.2017.
 */

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
//		rvCategories.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
		rvCategories.setAdapter(new CategoriesAdapter(onCategoryClick));
	}
}
