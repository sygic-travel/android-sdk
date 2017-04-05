package com.sygic.travel.sdkdemo.map;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.sygic.travel.sdkdemo.utils.spread.DimensConfig;
import com.sygic.travel.sdkdemo.utils.spread.PlacesSpreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;
import static com.sygic.travel.sdkdemo.utils.Utils.getMarkerColor;

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
	private View vMain;

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
		mMap = googleMap;

		placesSpreader  = new PlacesSpreader(getResources(), getDimensConfig());
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
		List<String> quadkeys = QuadkeysGenerator.generateQuadkeys(
			getMapBoundingBox(),
			(int) mMap.getCameraPosition().zoom
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
		LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		LatLng ne = bounds.northeast;
		LatLng sw = bounds.southwest;

		return sw.latitude + "," + sw.longitude + "," + ne.latitude + "," + ne.longitude;
	}

	private BoundingBox getMapBoundingBox() {
		LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		BoundingBox boundingBox = new BoundingBox();
		boundingBox.setSouth((float) bounds.southwest.latitude);
		boundingBox.setWest((float) bounds.southwest.longitude);
		boundingBox.setNorth((float) bounds.northeast.latitude);
		boundingBox.setEast((float) bounds.northeast.longitude);

		return boundingBox;
	}

	private void showPlacesOnMap(List<Place> places) {
		placesSpreader.prepareForSpreadingNextCollection(
			getMapBoundingBox(),
			(int) mMap.getCameraPosition().zoom,
			vMain.getLayoutParams().width,
			vMain.getLayoutParams().height
		);

		removeMarkers();
		for(Place place : places) {
			if(placeMarkers.keySet().contains(place.getGuid())){
				continue;
			}

			BitmapDescriptor markerBitmapDescriptor = getMarkerBitmapDescriptor(place);
			if(markerBitmapDescriptor == null) {
				continue;
			}

			Marker newMarker = mMap.addMarker(new MarkerOptions()
				.position(new LatLng(place.getLocation().getLat(), place.getLocation().getLng()))
				.title(place.getName())
				.snippet(place.getPerex())
				.icon(markerBitmapDescriptor)
			);
			newMarker.setTag(place.getGuid());

			placeMarkers.put(place.getGuid(), newMarker);

			if(placeMarkers.size() >= 50){
				break;
			}
		}
	}

	private BitmapDescriptor getMarkerBitmapDescriptor(Place place) {
		try {
			Bitmap markerBitmap = createMarkerBitmap(place);
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

	public Bitmap createMarkerBitmap(Place place) {
		try {
			Bitmap markerBitmap;
			Canvas canvas;
			Paint paint;
			Rect rect;
			RectF rectF;

			int markerColor = ContextCompat.getColor(this, R.color.st_blue);
			int markerSize = getResources().getDimensionPixelSize(R.dimen.marker_size_dot);

			if(place.getCategories() != null && place.getCategories().size() > 0){
				markerColor = getMarkerColor(this, place.getCategories().get(0));
			}

			if(place.getMarker() != null && !place.getMarker().equals("")){
				markerSize = placesSpreader.getPlaceSizeAndInsert(place);
			}

			if(markerSize == 0){
				return null;
			}

			markerBitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(markerBitmap);
			paint = new Paint();
			rect = new Rect(0, 0, markerSize, markerSize);
			rectF = new RectF(rect);

			paint.setStyle(Paint.Style.FILL);
			paint.setColor(markerColor);
			canvas.drawRoundRect(rectF, markerSize >> 1, markerSize >> 1, paint);

			return markerBitmap;
		} catch (Exception exception){
			exception.printStackTrace();
			return null;
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
