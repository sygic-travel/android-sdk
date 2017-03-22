package com.sygic.travel.sdkdemo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.utils.PermissionsUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.USER_X_API_KEY;

public class MapsActivity
	extends FragmentActivity
	implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback
{
	private static final String TAG = "SdkDemoApp-MapActivity";

	private GoogleMap mMap;
	private PermissionsUtils permissionsUtils;

	private Callback<List<Place>> placesCallback;
	private List<Marker> placesMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		StSDK.initialize(USER_X_API_KEY, this);
		permissionsUtils = new PermissionsUtils(findViewById(R.id.fl_main));

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		placesCallback = getPlacesCallback();
		placesMarkers = new ArrayList<>();

		LatLng sydney = new LatLng(51.5116983, -0.1205079);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));

		mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				loadPlaces();
			}
		});

		enableMyLocation();
		loadPlaces();

//		StSDK.getInstance().getPlaceDetailed("poi:447", detailBack);
//		StSDK.getInstance().getPlaceMedia("poi:447", mediaBack);
		/********************************************************************/
	}

	private void enableMyLocation() {
		if(permissionsUtils.isPermitted(
			this,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION
		)) {
			mMap.setMyLocationEnabled(true);
		} else {
			permissionsUtils.requestLocationPermission(this);
		}
	}

	private void loadPlaces(){
		Query query = new Query(
			null, getBoundsString(), "sightseeing", null, "city:1", null, null, null, null, 20
		);
		StSDK.getInstance().getPlaces(query, placesCallback);
	}

	private String getBoundsString() {
		LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		LatLng ne = bounds.northeast;
		LatLng sw = bounds.southwest;

		return sw.latitude + "," + sw.longitude + "," + ne.latitude + "," + ne.longitude;
	}

	private void showPlacesOnMap(List<Place> places) {
		removeMarkers();
		for(Place place : places) {
			placesMarkers.add(mMap.addMarker(
				new MarkerOptions()
					.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
					.title(place.getName())
					.snippet(place.getPerex())
			));
		}
	}

	private void removeMarkers() {
		if(placesMarkers != null){
			for(Marker placesMarker : placesMarkers) {
				placesMarker.remove();
			}
			placesMarkers.clear();
		}
	}

	private Callback<List<Place>> getPlacesCallback() {
		if(placesCallback == null){
			placesCallback = new Callback<List<Place>>() {
				@Override
				public void onSuccess(List<Place> places) {
//					Log.d(TAG, "Places: onSuccess");
					showPlacesOnMap(places);
				}

				@Override
				public void onFailure(Throwable t) {
					Log.d(TAG, "Places: onFailure");
					t.printStackTrace();
				}
			};
		}
		return placesCallback;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == PermissionsUtils.REQUEST_LOCATION) {
			if (permissionsUtils.verifyPermissions(grantResults)){
				permissionsUtils.showGrantedSnackBar();
				mMap.setMyLocationEnabled(true);
			} else {
				permissionsUtils.showExplanationSnackbar(this, Manifest.permission.ACCESS_FINE_LOCATION);
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
