package placeDetail.referenceList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

import placeDetail.PlaceDetailConstants;
import placeDetail.PlaceDetailReferenceUtils;
import placeDetail.ReferenceWrapper;
import placeDetail.utils.Utils;

public class ReferencesListActivity extends AppCompatActivity {
	private String guid;
	private List<Reference> references;
	private ReferencesListAdapter adapter;
	private RecyclerView rvReferences;
	private ActionBar supportActionBar;


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reference_list_activity);
		supportActionBar = Utils.setToolbar(this);
		supportActionBar.setDisplayHomeAsUpEnabled(true);

		if(getIntent().hasExtra(PlaceDetailConstants.FEATURE_TITLE)){
			supportActionBar.setTitle(getIntent().getStringExtra(PlaceDetailConstants.FEATURE_TITLE));
		} else {
			supportActionBar.setTitle("");
		}

		Callback<Detail> detailBack = new Callback<Detail>() {
			@Override
			public void onSuccess(Detail data) {
				references = data.getReferences();
				proceedWithLoadedReferences();
			}

			@Override
			public void onFailure(Throwable t) {
				showErrorAndFinish();
			}
		};

		if(getIntent().hasExtra(PlaceDetailConstants.GUID)){
			guid = getIntent().getStringExtra(PlaceDetailConstants.GUID);
			StSDK.getInstance().getPlaceDetailed(guid, detailBack);
		} else {
			showErrorAndFinish();
		}
	}

	private void proceedWithLoadedReferences(){
		adapter = new ReferencesListAdapter(
			references,
			getReferenceClick(),
			getResources()
		);
		rvReferences = (RecyclerView)findViewById(R.id.rv_references);
		rvReferences.setLayoutManager(new LinearLayoutManager(this));
		rvReferences.setAdapter(adapter);
	}

	private void showErrorAndFinish(){
		Toast.makeText(this, "Did not get reference ids", Toast.LENGTH_LONG).show();
		finish();
		return;
	}


	private ReferencesListAdapter.ReferenceViewHolder.ReferenceClick getReferenceClick(){
		return new ReferencesListAdapter.ReferenceViewHolder.ReferenceClick() {
			@Override
			public void referenceClicked(Reference reference) {
				PlaceDetailReferenceUtils.showReferenceUrl(
					ReferencesListActivity.this,
					new ReferenceWrapper(reference),
					PlaceDetailReferenceUtils.DETAIL
				);
			}
		};
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		onBackPressed();
		return super.getSupportParentActivityIntent();
	}
}
