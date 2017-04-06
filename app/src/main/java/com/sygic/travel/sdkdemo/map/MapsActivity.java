package com.sygic.travel.sdkdemo.map;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;

import com.sygic.travel.sdk.geo.quadkey.QuadkeysGenerator;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.PermissionsUtils;
import com.sygic.travel.sdkdemo.utils.Utils;
import com.sygic.travel.sdkdemo.utils.spread.MarkerProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;

public class MapsActivity
	extends AppCompatActivity
	implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback
{
	private static final String TAG = "SdkDemoApp-MapActivity";

	public static final float ZOOM_FOR_DETAIL = 18f;
	public static final float ZOOM_FOR_CITY = 15f;
	public static final float ZOOM_FOR_COUNTRY = 7f;

	private GoogleMap map;
	private PermissionsUtils permissionsUtils;
	private MarkerProcessor markerProcessor;

	private Callback<List<Place>> placesCallback;
	private HashMap<String, Marker> placesMarkers;
	private View vMain;
	private String selectedMakerGuid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		vMain = findViewById(R.id.ll_main);
		permissionsUtils = new PermissionsUtils(vMain);

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
		map = googleMap;

		markerProcessor = new MarkerProcessor(
			getResources(),
			vMain.getMeasuredWidth(),
			vMain.getMeasuredHeight()
		);
		placesCallback = getPlacesCallback();
		placesMarkers = new HashMap<>();

		LatLng londonLatLng = new LatLng(51.5116983, -0.1205079);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(londonLatLng, 14));
		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				marker.showInfoWindow();
				selectedMakerGuid = (String) marker.getTag();
				return false;
			}
		});
		map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			final int MAP_MOVES_LOAD_LIMIT = 10;
			int movesCounter = 0;

			@Override
			public void onCameraMove() {
				if(++movesCounter == MAP_MOVES_LOAD_LIMIT) {
					movesCounter = 0;
					loadPlaces();
				}
			}
		});
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
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

	private void enableMyLocation() {
		if(permissionsUtils.isPermitted(
			this,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION
		)) {
			map.setMyLocationEnabled(true);
		} else {
			permissionsUtils.requestLocationPermission(this);
		}
	}

	private void loadPlaces(){
		List<String> quadkeys = QuadkeysGenerator.generateQuadkeys(
			getMapBoundingBox(),
			(int) map.getCameraPosition().zoom
		);
		List<Query> queries = new ArrayList<>();

		for(String quadkey : quadkeys) {
			queries.add(new Query(
				null, getMapBoundsString(), null, null, "city:1", 1, quadkey, null, 32)
			);
		}

		StSDK.getInstance().getPlaces(queries, placesCallback);
	}

	private String getMapBoundsString() {
		LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
		LatLng ne = bounds.northeast;
		LatLng sw = bounds.southwest;

		return sw.latitude + "," + sw.longitude + "," + ne.latitude + "," + ne.longitude;
	}

	private BoundingBox getMapBoundingBox() {
		LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
		BoundingBox boundingBox = new BoundingBox();
		boundingBox.setSouth((float) bounds.southwest.latitude);
		boundingBox.setWest((float) bounds.southwest.longitude);
		boundingBox.setNorth((float) bounds.northeast.latitude);
		boundingBox.setEast((float) bounds.northeast.longitude);

		return boundingBox;
	}

	private void showPlacesOnMap(List<Place> places) {
		markerProcessor.setNewMapPosition(
			getMapBoundingBox(),
			(int) map.getCameraPosition().zoom
		);

		removeMarkers();
		for(Place place : places) {
			BitmapDescriptor markerBitmapDescriptor = getMarkerBitmapDescriptor(place);
			if(markerBitmapDescriptor == null) {
				continue;
			}

			Marker newMarker = map.addMarker(new MarkerOptions()
				.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
				.title(place.getName())
				.snippet(place.getPerex())
				.icon(markerBitmapDescriptor)
			);
			newMarker.setTag(place.getGuid());

			placesMarkers.put(place.getGuid(), newMarker);
		}
	}

	private BitmapDescriptor getMarkerBitmapDescriptor(Place place) {
		try {
			Bitmap markerBitmap = markerProcessor.createMarkerBitmap(this, place);
			if(markerBitmap != null) {
				return BitmapDescriptorFactory.fromBitmap(markerBitmap);
			} else {
				return null;
			}
		} catch(Exception e) {
			return BitmapDescriptorFactory.defaultMarker(getMarkerHue(place));
		}
	}

	private float getMarkerHue(Place place) {
		float markerHue = BitmapDescriptorFactory.HUE_RED;
		if(place.getCategories() != null && place.getCategories().size() > 0){
			markerHue = Utils.getMarkerHue(place.getCategories().get(0));
		}
		return markerHue;
	}

	private void removeMarkers() {
		if(placesMarkers != null){
			if(selectedMakerGuid == null){
				map.clear();
				placesMarkers.clear();
				return;
			}

			List<String> removedMarkers = new ArrayList<>();

			for(String key : placesMarkers.keySet()) {
				Marker placeMarker = placesMarkers.get(key);
				String placeMarkerGuid = (String) placeMarker.getTag();

				if(placeMarkerGuid != null) {
					if(!placeMarkerGuid.equals(selectedMakerGuid)) {
						placeMarker.remove();
						removedMarkers.add(key);
					}
				}
			}

			for(String removedMarker : removedMarkers) {
				placesMarkers.remove(removedMarker);
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
				map.setMyLocationEnabled(true);
			} else {
				permissionsUtils.showExplanationSnackbar(this, Manifest.permission.ACCESS_FINE_LOCATION);
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
