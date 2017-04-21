package com.sygic.travel.sdkdemo.map;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.sygic.travel.sdk.geo.spread.CanvasSize;
import com.sygic.travel.sdk.geo.spread.SpreadResult;
import com.sygic.travel.sdk.geo.spread.SpreadSizeConfig;
import com.sygic.travel.sdk.geo.spread.SpreadedPlace;
import com.sygic.travel.sdk.geo.spread.Spreader;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;
import com.sygic.travel.sdkdemo.PlaceDetailActivity;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.filters.CategoriesAdapter;
import com.sygic.travel.sdkdemo.filters.CategoriesDialog;
import com.sygic.travel.sdkdemo.utils.MarkerBitmapGenerator;
import com.sygic.travel.sdkdemo.utils.PermissionsUtils;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.ArrayList;
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
	private static final double BOUNDS_OFFSET = 0.05;

	private double canvasWidthRatio, canvasHeightRatio;

	private GoogleMap map;
	private PermissionsUtils permissionsUtils;
	private Spreader spreader;
	private List<SpreadSizeConfig> sizeConfigs;

	private CategoriesDialog categoriesDialog;
	private String selectedCategoryKey;
	private String titlePattern;

	private Callback<List<Place>> placesCallback;
	private View vMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		vMain = findViewById(R.id.ll_main);
		permissionsUtils = new PermissionsUtils(vMain);
		spreader = new Spreader();
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

	@Override
	public void onMapReady(GoogleMap googleMap) {
		map = googleMap;

		sizeConfigs = Utils.getSpreadSizeConfigs(getResources());
		placesCallback = getPlacesCallback();

		LatLng londonLatLng = new LatLng(51.5116983, -0.1205079);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(londonLatLng, 14));
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				String guid = ((String) marker.getTag());
				Intent placeDetailIntent = new Intent(MapsActivity.this, PlaceDetailActivity.class);
				placeDetailIntent.putExtra(GUID, guid);
				startActivity(placeDetailIntent);
			}
		});

		calculateCanvasSizeRatios();
		enableMyLocation();
		loadPlaces();
	}

	private void calculateCanvasSizeRatios() {
		LatLngBounds originalBounds = map.getProjection().getVisibleRegion().latLngBounds;
		LatLngBounds offsetBounds = new LatLngBounds(
			new LatLng(originalBounds.southwest.latitude - BOUNDS_OFFSET, originalBounds.southwest.longitude - BOUNDS_OFFSET),  //sw
			new LatLng(originalBounds.northeast.latitude + BOUNDS_OFFSET, originalBounds.northeast.longitude + BOUNDS_OFFSET)  //ne
		);

		final double dOffLat = offsetBounds.northeast.latitude - offsetBounds.southwest.latitude;
		final double dOrgLat = originalBounds.northeast.latitude - originalBounds.southwest.latitude;
		final double dOffLng = Math.max(Math.abs(offsetBounds.northeast.longitude), Math.abs(offsetBounds.southwest.longitude)) -
			Math.min(Math.abs(offsetBounds.northeast.longitude), Math.abs(offsetBounds.southwest.longitude));
		final double dOrgLng = Math.max(Math.abs(originalBounds.northeast.longitude), Math.abs(originalBounds.southwest.longitude)) -
			Math.min(Math.abs(originalBounds.northeast.longitude), Math.abs(originalBounds.southwest.longitude));
		canvasHeightRatio = dOffLat / dOrgLat;
		canvasWidthRatio = dOffLng / dOrgLng;
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
				null, getMapBoundsString(), selectedCategoryKey, null, "city:1", 1, quadkey, null, 32)
			);
		}

		StSDK.getInstance().getPlaces(queries, placesCallback);
	}

	private CategoriesAdapter.ViewHolder.CategoryClick getOnCategoriesClick() {
		return new CategoriesAdapter.ViewHolder.CategoryClick() {
			@Override
			public void onCategoryClick(String categoryKey, String categoryName) {
				if(selectedCategoryKey != null && selectedCategoryKey.equals(categoryKey)){
					categoriesDialog.dismiss();
					return;
				}

				if(categoryKey.equals("reset")){
					selectedCategoryKey = null;
					setTitle(getString(R.string.title_activity_maps));
				} else {
					selectedCategoryKey = categoryKey;
					setTitle(String.format(titlePattern, categoryName));
				}

				loadPlaces();
				categoriesDialog.dismiss();
			}
		};
	}

	private String getMapBoundsString() {
		LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
		LatLng ne = bounds.northeast;
		LatLng sw = bounds.southwest;

		return (sw.latitude - BOUNDS_OFFSET) + "," +
			(sw.longitude - BOUNDS_OFFSET) + "," +
			(ne.latitude + BOUNDS_OFFSET) + "," +
			(ne.longitude + BOUNDS_OFFSET);
	}

	private BoundingBox getMapBoundingBox() {
		LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
		BoundingBox boundingBox = new BoundingBox();
		boundingBox.setSouth((float) (bounds.southwest.latitude - BOUNDS_OFFSET));
		boundingBox.setWest((float) (bounds.southwest.longitude - BOUNDS_OFFSET));
		boundingBox.setNorth((float) (bounds.northeast.latitude + BOUNDS_OFFSET));
		boundingBox.setEast((float) (bounds.northeast.longitude + BOUNDS_OFFSET));

		return boundingBox;
	}

	private void showPlacesOnMap(List<Place> places) {
		BoundingBox boundingBox = getMapBoundingBox();

		map.clear();
		SpreadResult spreadResult = spreader.spread(
			places,
			sizeConfigs,
			boundingBox,
			new CanvasSize(
				(int) (vMain.getMeasuredWidth() * canvasWidthRatio),
				(int) (vMain.getMeasuredHeight() * canvasHeightRatio)
			)
		);

		for(SpreadedPlace spreadedPlace : spreadResult.getVisiblePlaces()) {
			Place place = spreadedPlace.getPlace();
			Marker newMarker = map.addMarker(new MarkerOptions()
				.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
				.title(place.getName())
				.snippet(place.getPerex())
				.icon(getMarkerBitmapDescriptor(spreadedPlace))
			);
			newMarker.setTag(place.getGuid());
		}
	}

	private BitmapDescriptor getMarkerBitmapDescriptor(SpreadedPlace spreadedPlace) {
		try {
			Bitmap markerBitmap = MarkerBitmapGenerator.createMarkerBitmap(this, spreadedPlace);
			if(markerBitmap != null) {
				return BitmapDescriptorFactory.fromBitmap(markerBitmap);
			} else {
				return null;
			}
		} catch(Exception e) {
			return BitmapDescriptorFactory.defaultMarker(getMarkerHue(spreadedPlace.getPlace()));
		}
	}

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
