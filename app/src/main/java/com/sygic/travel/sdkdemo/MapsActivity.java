package com.sygic.travel.sdkdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.media.Media;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;

import java.util.List;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.USER_X_API_KEY;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		StSDK.initialize(USER_X_API_KEY, this);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

		mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
			}
		});


		/*******************************************************************
		 * Simple usage of SDK
		 *******************************************************************/
		Query query = new Query(
			null, null, "eating", null, "city:1", null, null, null, null, 20
		);

		Callback<List<Place>> placesBack = new Callback<List<Place>>() {
			@Override
			public void onSuccess(List<Place> data) {
				Log.d("TEST_APP", "Places: onSuccess");
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Places: onFailure");
				t.printStackTrace();
			}
		};

		Callback<Detail> detailBack = new Callback<Detail>() {
			@Override
			public void onSuccess(Detail data) {
				Log.d("TEST_APP", "Detail: onSuccess");
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Detail: onFailure");
			}
		};

		Callback<Media> mediaBack = new Callback<Media>() {
			@Override
			public void onSuccess(Media data) {
				Log.d("TEST_APP", "Media: onSuccess");
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Media: onFailure");
			}
		};

		StSDK.getInstance().getPlaces(query, placesBack);
//		StSDK.getInstance().getPlaceDetailed("poi:447", detailBack);
//		StSDK.getInstance().getPlaceMedia("poi:447", mediaBack);
		/********************************************************************/
	}
}
