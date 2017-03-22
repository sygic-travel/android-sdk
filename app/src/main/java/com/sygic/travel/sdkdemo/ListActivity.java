package com.sygic.travel.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Reference;

import java.util.List;

import itemDetail.fragment.ItemDetailFragment;
import itemDetail.fragment.ItemDetailListener;

public class ListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		final ItemDetailFragment fragment = new ItemDetailFragment();
		fragment.setDependencies(this, new ItemDetailListener() {
			@Override
			public void onRenderFinished() {

			}

			@Override
			public boolean isFull() {
				return false;
			}

			@Override
			public void onUserDataClick() {

			}

			@Override
			public void onFodorsClick() {

			}

			@Override
			public void onReferencesListClick(List<String> referenceIds) {

			}

			@Override
			public void onLeadCreated(Reference reference) {

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
