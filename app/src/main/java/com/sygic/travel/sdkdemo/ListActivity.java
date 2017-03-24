package com.sygic.travel.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;

import placeDetail.fragment.PlaceDetailFragment;
import placeDetail.fragment.PlaceDetailListener;

public class ListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		final PlaceDetailFragment fragment = new PlaceDetailFragment();
		fragment.setDependencies(this, new PlaceDetailListener() {
			@Override
			public void onRenderFinished() {

			}

			@Override
			public void onUserDataClick() {

			}
		});

		Callback<Detail> detailBack = new Callback<Detail>() {
			@Override
			public void onSuccess(Detail data) {
				fragment.loadWithFeature(data);
				getSupportFragmentManager().beginTransaction().add(R.id.activity_list, fragment).commitAllowingStateLoss();
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d("TEST_APP", "Detail: onFailure");
			}
		};

		StSDK.getInstance().getPlaceDetailed("poi:447", detailBack);


	}
}
