package com.sygic.travel.sdkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdkdemo.list.PlacesListActivity;
import com.sygic.travel.sdkdemo.map.MapsActivity;
import com.sygic.travel.sdkdemo.search.SearchActivity;

import static com.sygic.travel.sdk.contentProvider.api.StApiConstants.USER_X_API_KEY;

public class DemoMainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_main);

		StSDK.initialize(USER_X_API_KEY, this);
		initUI();
	}

	private void initUI() {
		findViewById(R.id.btn_map_activity).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startMapActivity();
			}
		});
		findViewById(R.id.btn_list_activity).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startListActivity();
			}
		});
		findViewById(R.id.btn_search_activity).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startSearchActivity();
			}
		});
	}

	private void startMapActivity() {
		Intent mapIntent = new Intent(this, MapsActivity.class);
		startActivity(mapIntent);
	}

	private void startListActivity() {
		Intent mapIntent = new Intent(this, PlacesListActivity.class);
		startActivity(mapIntent);
	}

	private void startSearchActivity() {
		Intent searchIntent = new Intent(this, SearchActivity.class);
		startActivity(searchIntent);
	}
}
