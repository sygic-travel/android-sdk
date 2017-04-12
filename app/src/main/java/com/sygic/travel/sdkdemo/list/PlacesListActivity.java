package com.sygic.travel.sdkdemo.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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

		initRecycler();
		loadPlaces();
	}

	@Override
	protected void onPause() {
		super.onPause();
		StSDK.getInstance().unsubscribeObservable();
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
		queries.add(new Query(null, null, null, null, "city:1", null, null, null, null, 100));
		StSDK.getInstance().getPlaces(queries, getPlacesCallback());
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
