package com.sygic.travel.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.sygic.travel.sdk.model.place.Place.GUID;

public class PlaceDetailActivity extends AppCompatActivity {
	private static final String TAG = PlaceDetailActivity.class.getSimpleName();

	private String guid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_detail);

		guid = getIntent().getStringExtra(GUID);
		loadPlaceDetail();
	}

	private void loadPlaceDetail() {

	}
}
