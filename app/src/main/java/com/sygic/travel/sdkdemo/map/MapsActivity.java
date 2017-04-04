package com.sygic.travel.sdkdemo.map;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.PermissionsUtils;
import com.sygic.travel.sdkdemo.utils.spread.DimensConfig;
import com.sygic.travel.sdkdemo.utils.spread.PlacesSpreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;
import static com.sygic.travel.sdkdemo.utils.Utils.getMarkerHue;

public class MapsActivity
	extends AppCompatActivity
	implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback
{
	private static final String TAG = "SdkDemoApp-MapActivity";

	public static final float ZOOM_FOR_DETAIL = 18f;
	public static final float ZOOM_FOR_CITY = 15f;
	public static final float ZOOM_FOR_COUNTRY = 7f;

	private GoogleMap mMap;
	private PermissionsUtils permissionsUtils;
	private PlacesSpreader placesSpreader;

	private Callback<List<Place>> placesCallback;
	private HashMap<String, Marker> placeMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		permissionsUtils = new PermissionsUtils(findViewById(R.id.ll_main));

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		StSDK.getInstance().unsubscribeObservable();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		placesSpreader  = new PlacesSpreader(getDimensConfig());
		placesCallback = getPlacesCallback();
		placeMarkers = new HashMap<>();

		LatLng londonLatLng = new LatLng(51.5116983, -0.1205079);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(londonLatLng, 14));
		mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				loadPlaces();
			}
		});
		mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				String guid = ((String) marker.getTag());
				Intent placeDetailIntent = new Intent(MapsActivity.this, PlaceDetailActivity.class);
				placeDetailIntent.putExtra(GUID, guid);
				startActivity(placeDetailIntent);
			}
		});

		enableMyLocation();
		loadPlaces();
	}

	private DimensConfig getDimensConfig() {
		return new DimensConfig(
			getResources().getDimensionPixelSize(R.dimen.marker_margin_popular),
			getResources().getDimensionPixelSize(R.dimen.marker_margin_big),
			getResources().getDimensionPixelSize(R.dimen.marker_margin_medium),
			getResources().getDimensionPixelSize(R.dimen.marker_margin_small),
			getResources().getDimensionPixelSize(R.dimen.marker_size_popular),
			getResources().getDimensionPixelSize(R.dimen.marker_size_big),
			getResources().getDimensionPixelSize(R.dimen.marker_size_medium),
			getResources().getDimensionPixelSize(R.dimen.marker_size_small)
		);
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
		List<Query> queries = new ArrayList<>();
		queries.add(new Query(null, getBoundsString(), null, null, "city:1", null, null, null, null, 100));
		StSDK.getInstance().getPlaces(queries, placesCallback);
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
			if(placeMarkers.keySet().contains(place.getGuid())){
				continue;
			}
			float markerColor = BitmapDescriptorFactory.HUE_RED;
			if(place.getCategories() != null && place.getCategories().size() > 0){
				markerColor = getMarkerHue(place.getCategories().get(0));
			}
			Marker newMarker = mMap.addMarker(new MarkerOptions()
				.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
				.title(place.getName())
				.snippet(place.getPerex())
				.icon(BitmapDescriptorFactory.defaultMarker(markerColor))
			);
			newMarker.setTag(place.getGuid());

			placeMarkers.put(place.getGuid(), newMarker);

			if(placeMarkers.size() >= 50){
				break;
			}
		}
	}

	private void removeMarkers() {
		if(placeMarkers != null){
			List<String> removedMarkers = new ArrayList<>();
			LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

			for(String key : placeMarkers.keySet()) {
				Marker placeMarker = placeMarkers.get(key);

				if(!bounds.contains(placeMarker.getPosition())) {
					placeMarker.remove();
					removedMarkers.add(key);
				}
			}

			for(String removedMarker : removedMarkers) {
				placeMarkers.remove(removedMarker);
			}
		}
	}

	private Callback<List<Place>> getPlacesCallback() {
		if(placesCallback == null){
			placesCallback = new Callback<List<Place>>() {
				@Override
				public void onSuccess(List<Place> places) {
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
