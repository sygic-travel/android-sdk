package com.sygic.travel.sdkdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


		/*******************************************************************
		 * Simple usage of SDK
		 *******************************************************************/
		Query query = new Query(
			null, null, "eating", null, "city:1", null, null, null, null, 20
		);
		Callback<List<Place>> placesBack = new Callback<List<Place>>() {
			@Override
			public void onSuccess(List<Place> data) {
				Log.d("TEST_APP", "Places: onResponse");
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Places: onFailure");
				t.printStackTrace();
			}
		};

		Callback<Place> detailBack = new Callback<Place>() {
			@Override
			public void onSuccess(Place data) {
				Log.d("TEST_APP", "Detail: onSuccess");
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Detail: onFailure");
			}
		};

		String userXApiKey = "qBei674Bdt5lk2rTkphqP1jiXC7M96HR26BFNSGw"; //TODO only for testing
		StSDK.initialize(userXApiKey, this);
		StSDK.getInstance().getPlaces(query, placesBack);
		StSDK.getInstance().getPlaceDetailed("poi:447", detailBack);
		/********************************************************************/
	}
}
