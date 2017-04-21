package com.sygic.travel.sdkdemo.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.filters.CategoriesAdapter;
import com.sygic.travel.sdkdemo.filters.CategoriesDialog;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;

public class PlacesListActivity extends AppCompatActivity {
	private static final String TAG = PlacesListActivity.class.getSimpleName();

	private RecyclerView rvPlaces;
	private PlacesAdapter placesAdapter;
	private List<Place> places;
	private CategoriesDialog categoriesDialog;
	private String selectedCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		categoriesDialog = new CategoriesDialog(this, getOnCategoriesClick());
		initRecycler();
		loadPlaces();
	}

	@Override
	protected void onPause() {
		super.onPause();
		StSDK.getInstance().unsubscribeObservable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.places, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_filter_places) {
			categoriesDialog.show();
		}
		return super.onOptionsItemSelected(item);
	}

	private void initRecycler() {
		rvPlaces = (RecyclerView) findViewById(R.id.rv_places);
		rvPlaces.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//		rvPlaces.addItemDecoration(new DividerDecoration(this, R.drawable.line_divider));
		rvPlaces.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		placesAdapter = new PlacesAdapter(getOnPlaceClick(), Utils.getDetailPhotoSize(this));
		rvPlaces.setAdapter(placesAdapter);
	}

	private PlacesAdapter.ViewHolder.PlaceClick getOnPlaceClick() {
		return new PlacesAdapter.ViewHolder.PlaceClick() {
			@Override
			public void onPlaceClick(int position) {
				Intent placeDetailIntent = new Intent(PlacesListActivity.this, PlaceDetailActivity.class);
				placeDetailIntent.putExtra(GUID, places.get(position).getGuid());
				startActivity(placeDetailIntent);
			}
		};
	}

	private void loadPlaces() {
		List<Query> queries = new ArrayList<>();
		queries.add(new Query(null, null, selectedCategory, null, "city:1", null, null, null, 128));
		StSDK.getInstance().getPlaces(queries, getPlacesCallback());
	}

	private void renderPlacesList(List<Place> places) {
		this.places = places;
		placesAdapter.setPlaces(places);
		placesAdapter.notifyDataSetChanged();
	}

	private CategoriesAdapter.ViewHolder.CategoryClick getOnCategoriesClick() {
		return new CategoriesAdapter.ViewHolder.CategoryClick() {
			@Override
			public void onCategoryClick(String category) {
				selectedCategory = category.equals("reset") ? null : category;
				loadPlaces();
				categoriesDialog.dismiss();
			}
		};
	}

	private Callback<List<Place>> getPlacesCallback() {
		return new Callback<List<Place>>() {
			@Override
			public void onSuccess(List<Place> places) {
				renderPlacesList(places);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Places: onFailure");
				t.printStackTrace();
			}
		};
	}
}
