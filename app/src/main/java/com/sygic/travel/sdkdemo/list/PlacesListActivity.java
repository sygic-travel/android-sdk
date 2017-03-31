package com.sygic.travel.sdkdemo.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.gallery.DividerDecoration;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.USER_X_API_KEY;
import static com.sygic.travel.sdk.model.place.Place.GUID;

public class PlacesListActivity extends AppCompatActivity {
	private static final String TAG = PlacesListActivity.class.getSimpleName();

	private RecyclerView rvPlaces;
	private PlacesAdapter placesAdapter;
	private List<Place> places;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		StSDK.initialize(USER_X_API_KEY, this);
		initRecycler();
		loadPlaces();
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
		Query query = new Query(
			null, null, null, null, "city:1", null, null, null, null, 100
		);
		StSDK.getInstance().getPlaces(query, getPlacesCallback());
	}

	private void renderPlacesList(List<Place> places) {
		this.places = places;
		placesAdapter.setPlaces(places);
		placesAdapter.notifyDataSetChanged();
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
