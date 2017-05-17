package com.sygic.travel.sdkdemo.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlacesListActivity extends AppCompatActivity {
	private static final String TAG = PlacesListActivity.class.getSimpleName();

	public static final String ID = "id";

	private RecyclerView rvPlaces;
	private PlacesAdapter placesAdapter;
	private List<Place> places;

	private CategoriesDialog categoriesDialog;
	private List<String> selectedCateoriesKeys = new ArrayList<>();
	private String titlePattern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		categoriesDialog = new CategoriesDialog(this, getOnCategoriesClick());
		titlePattern = getString(R.string.title_activity_list) + " - %s";

		initRecycler();
		loadPlaces();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Observables need to be unsubscribed, when the activity comes to background
		StSDK.getInstance().unsubscribeObservable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// There's only one option in the menu - categories filter.
		getMenuInflater().inflate(R.menu.places, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_filter_places) {
			// Show dialog with categories
			categoriesDialog.show();
		}
		return super.onOptionsItemSelected(item);
	}

	// Recycler view initialization - list with dividers
	private void initRecycler() {
		rvPlaces = (RecyclerView) findViewById(R.id.rv_places);
		rvPlaces.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvPlaces.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		placesAdapter = new PlacesAdapter(getOnPlaceClick(), Utils.getDetailPhotoSize(this));
		rvPlaces.setAdapter(placesAdapter);
	}

	// On a place click listener. Opens it's detail.
	private PlacesAdapter.ViewHolder.PlaceClick getOnPlaceClick() {
		return new PlacesAdapter.ViewHolder.PlaceClick() {
			@Override
			public void onPlaceClick(int position) {
				Intent placeDetailIntent = new Intent(PlacesListActivity.this, PlaceDetailActivity.class);
				placeDetailIntent.putExtra(ID, places.get(position).getId());
				startActivity(placeDetailIntent);
			}
		};
	}

	// Use the SDK to load places
	private void loadPlaces() {
		Query query = new Query();
		query.setLevels(Collections.singletonList("poi"));
		query.setCategories(selectedCateoriesKeys);
		query.setParents(Collections.singletonList("city:1"));
		query.setLimit(128);
		StSDK.getInstance().getPlaces(query, getPlacesCallback());
	}

	private void renderPlacesList(List<Place> places) {
		this.places = places;
		placesAdapter.setPlaces(places);
		placesAdapter.notifyDataSetChanged();
	}

	// On a category click listener.
	private CategoriesAdapter.ViewHolder.CategoryClick getOnCategoriesClick() {
		return new CategoriesAdapter.ViewHolder.CategoryClick() {
			@Override
			public void onCategoryClick(String categoryKey, String categoryName) {
				if(selectedCateoriesKeys.contains(categoryKey)){
					categoriesDialog.dismiss();
					return;
				}

				// Set activity's title
				if(categoryKey.equals("all")){
					selectedCateoriesKeys.clear();
					setTitle(getString(R.string.title_activity_list));
				} else {
					selectedCateoriesKeys.add(categoryKey);
					setTitle(String.format(titlePattern, categoryName));
				}

				// Reload places
				loadPlaces();
				categoriesDialog.dismiss();
			}
		};
	}

	private Callback<List<Place>> getPlacesCallback() {
		return new Callback<List<Place>>() {
			@Override
			public void onSuccess(List<Place> places) {
				// Places are sorted by rating, best rated places are at the top of the list
				Collections.sort(places, new Comparator<Place>() {
					@Override
					public int compare(Place p1, Place p2) {
						if(p1.getRating() == p2.getRating()){
							return 0;
						} else {
							return p1.getRating() > p2.getRating() ? -1 : 1;
						}
					}
				});
				renderPlacesList(places);
			}

			@Override
			public void onFailure(Throwable t) {
				Toast.makeText(PlacesListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
				t.printStackTrace();
			}
		};
	}
}
