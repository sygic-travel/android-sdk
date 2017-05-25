package com.sygic.travel.sdkdemo.map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import com.sygic.travel.sdk.geo.spread.CanvasSize;
import com.sygic.travel.sdk.geo.spread.SpreadResult;
import com.sygic.travel.sdk.geo.spread.SpreadedPlace;
import com.sygic.travel.sdk.geo.spread.Spreader;
import com.sygic.travel.sdk.model.geo.Bounds;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.filters.CategoriesAdapter;
import com.sygic.travel.sdkdemo.filters.CategoriesDialog;
import com.sygic.travel.sdkdemo.utils.MarkerBitmapGenerator;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends AppCompatActivity	implements OnMapReadyCallback {
	private static final String TAG = "SdkDemoApp-MapActivity";
	public static final String ID = "id";

	private GoogleMap map;
	private Spreader spreader;

	private CategoriesDialog categoriesDialog;
	private List<String> selectedCateoriesKeys = new ArrayList<>();
	private String titlePattern;

	private Callback<List<Place>> placesCallback;
	private View vMain;

	int mapMovesCounter = 0;
	private Map<String, Marker> currentMarkers = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		vMain = findViewById(R.id.ll_main);
		spreader = new Spreader(getResources());
		categoriesDialog = new CategoriesDialog(this, getOnCategoriesClick());
		titlePattern = getString(R.string.title_activity_maps) + " - %s";

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
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

	// Google maps specific method called, when the map is initialiazed and ready.
	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;

		placesCallback = getPlacesCallback();

		// Center map to London
		LatLng londonLatLng = new LatLng(51.5116983, -0.1205079);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(londonLatLng, 14));

		// Set on marker's info window (bubble) click listener - starts place's detail activity.
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				String id = ((String) marker.getTag());
				Intent placeDetailIntent = new Intent(MapsActivity.this, PlaceDetailActivity.class);
				placeDetailIntent.putExtra(ID, id);
				startActivity(placeDetailIntent);
			}
		});

		// Set on camera move listener, so we can load new places
		map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				if(++mapMovesCounter % 15 == 0) {
					removeOutOfBoundsMarkers();
					loadPlaces();
					mapMovesCounter = 0;
				}
			}
		});

		loadPlaces();
	}

	private void removeOutOfBoundsMarkers() {
		LatLngBounds latLngBounds = map.getProjection().getVisibleRegion().latLngBounds;
		List<String> toRemove = new ArrayList<>();

		for(Marker visibleMarker : currentMarkers.values()) {
			if(!latLngBounds.contains(visibleMarker.getPosition())) {
				toRemove.add((String) visibleMarker.getTag());
			}
		}

		for(String idToRemove : toRemove) {
			currentMarkers.get(idToRemove).remove();
			currentMarkers.remove(idToRemove);
		}
	}


	// Use the SDK to load places
	private void loadPlaces(){
		// Generate quadkeys from the map's bondings and zoom
		List<String> quadkeys = QuadkeysGenerator.generateQuadkeys(
			getMapBounds(),
			(int) map.getCameraPosition().zoom
		);

		Query query = new Query();
		query.setLevels(Collections.singletonList("poi"));
		query.setCategories(selectedCateoriesKeys);
		query.setMapTiles(quadkeys);
		query.setMapSpread(1);
		query.setBounds(getMapBounds());
		query.setParents(Collections.singletonList("city:1"));
		query.setLimit(32);
		StSDK.getInstance().getPlaces(query, placesCallback);
	}

	// On category click listener
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
					setTitle(getString(R.string.title_activity_maps));
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

	// Returns map's Bounds
	private Bounds getMapBounds() {
		LatLngBounds latLngBounds = map.getProjection().getVisibleRegion().latLngBounds;
		Bounds bounds = new Bounds();

		// Map bounds are widened for the purposes of this sample. In a real app bounds without
		// the BOUNDS_OFFSET should be used.
		bounds.setSouth((float) (latLngBounds.southwest.latitude));
		bounds.setWest((float) (latLngBounds.southwest.longitude));
		bounds.setNorth((float) (latLngBounds.northeast.latitude));
		bounds.setEast((float) (latLngBounds.northeast.longitude));

		return bounds;
	}

	private void showPlacesOnMap(List<Place> places) {
		// Spread loaded places
		SpreadResult spreadResult = spreader.spreadPlacesOnMap(
			places,
			getMapBounds(),
			new CanvasSize(vMain.getMeasuredWidth(), vMain.getMeasuredHeight())
		);

		// Remove markers, which would not be visible with new spread result
		removeHiddenPlaces(spreadResult);

		// Remove markers which are on map, but no in new spread result
		removeOldMarkers(spreadResult);

		// Update icons for marker which stay on the map after new spread result
		updateCurrentMarkers(spreadResult);

		// Create markers for spread places
		createNewMarkers(spreadResult);
	}

	private void removeHiddenPlaces(SpreadResult spreadResult) {
		for(Place hiddenPlace : spreadResult.getHiddenPlaces()) {
			Marker removedMarker = currentMarkers.remove(hiddenPlace.getId());
			if(removedMarker != null){
				removedMarker.remove();
			}
		}
	}

	private void removeOldMarkers(SpreadResult spreadResult) {
		List<String> toRemove = new ArrayList<>();
		for(String visibleMarkerId : currentMarkers.keySet()) {
			boolean shouldRemove = true;
			for(SpreadedPlace spreadedPlace : spreadResult.getVisiblePlaces()) {
				if(visibleMarkerId.equals(spreadedPlace.getPlace().getId())) {
					shouldRemove = false;
					break;
				}
			}
			if(shouldRemove){
				toRemove.add(visibleMarkerId);
			}
		}

		for(String idToRemove : toRemove) {
			currentMarkers.get(idToRemove).remove();
			currentMarkers.remove(idToRemove);
		}
	}

	private void updateCurrentMarkers(SpreadResult spreadResult) {
		for(SpreadedPlace spreadedPlace : spreadResult.getVisiblePlaces()) {
			Marker visibleMarker = currentMarkers.get(spreadedPlace.getPlace().getId());
			if(visibleMarker != null) {
				visibleMarker.setIcon(getMarkerBitmapDescriptor(spreadedPlace));
			}
		}
	}

	private void createNewMarkers(SpreadResult spreadResult) {
		for(SpreadedPlace spreadedPlace : spreadResult.getVisiblePlaces()) {
			Place place = spreadedPlace.getPlace();
			if(currentMarkers.keySet().contains(place.getId())){
				continue;
			}

			Marker newMarker = map.addMarker(new MarkerOptions()
				.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
				.title(place.getName())
				.snippet(place.getPerex())
				.icon(getMarkerBitmapDescriptor(spreadedPlace))
			);
			newMarker.setTag(place.getId());
			currentMarkers.put(place.getId(), newMarker);
		}
	}

	// This method returns bitmap descriptor, which is used for Google maps specifically
	private BitmapDescriptor getMarkerBitmapDescriptor(SpreadedPlace spreadedPlace) {
		try {
			// create marker's custom bitmap descriptor, if possible
			Bitmap markerBitmap = MarkerBitmapGenerator.createMarkerBitmap(this, spreadedPlace);
			if(markerBitmap != null) {
				return BitmapDescriptorFactory.fromBitmap(markerBitmap);
			} else {
				return null;
			}
		} catch(Exception e) {
			// If anything goes wrong, Google maps default pin with specific hue is returned.
			return BitmapDescriptorFactory.defaultMarker(getMarkerHue(spreadedPlace.getPlace()));
		}
	}

	// Return hue for Google maps default pin
	private float getMarkerHue(Place place) {
		float markerHue = BitmapDescriptorFactory.HUE_RED;
		if(place.getCategories() != null && place.getCategories().size() > 0){
			markerHue = Utils.getMarkerHue(place.getCategories().get(0));
		}
		return markerHue;
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
					Toast.makeText(MapsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
					t.printStackTrace();
				}
			};
		}
		return placesCallback;
	}
}
