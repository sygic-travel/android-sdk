package com.sygic.travel.sdkdemo.filters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sygic.travel.sdkdemo.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
	private static String[] categoriesKeys = {"all", "sightseeing", "shopping", "eating", "discovering", "playing",
		"traveling", "going_out", "hiking", "sports", "relaxing"};

	private static String[] categoriesNames = {"All categories", "Sightseeing", "Shopping", "Restaurants", "Museums", "Family",
		"Transport", "Nightlife", "Outdoors", "Sports", "Relaxation"};

	private ViewHolder.CategoryClick categoryClick;

	public CategoriesAdapter(ViewHolder.CategoryClick categoryClick) {
		this.categoryClick = categoryClick;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vItem = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.category_item, parent, false);
		return new ViewHolder(vItem, categoryClick);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tvCategory.setText(categoriesNames[position]);
	}

	@Override
	public int getItemCount() {
		if(categoriesNames == null) {
			return 0;
		}
		return categoriesNames.length;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private TextView tvCategory;
		private CategoryClick categoryClick;

		public ViewHolder(View vItem, CategoryClick categoryClick) {
			super(vItem);
			this.categoryClick = categoryClick;
			tvCategory = (TextView) vItem;
			tvCategory.setOnClickListener(this);
		}

		@Override
		public void onClick(View tvCategory) {
			categoryClick.onCategoryClick(
				categoriesKeys[getAdapterPosition()],
				categoriesNames[getAdapterPosition()]
			);
		}

		public interface CategoryClick{
			void onCategoryClick(String categoryKey, String categoryName);
		}
	}
}
