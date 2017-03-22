package itemDetail.referenceList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

import itemDetail.ItemDetailConstants;
import itemDetail.ItemDetailReferenceUtils;
import itemDetail.ReferenceWrapper;
import itemDetail.Screen;
import itemDetail.toBeDeleted.Utils;

public class ReferencesListActivity extends Screen {
	private String guid;
	private List<Reference> references;
	private ReferenceListAdapter adapter;
	private RecyclerView rvReferences;


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reference_list_activity);
		setToolbar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);

		if(getIntent().hasExtra(ItemDetailConstants.FEATURE_TITLE)){
			supportActionBar.setTitle(getIntent().getStringExtra(ItemDetailConstants.FEATURE_TITLE));
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

		if(getIntent().hasExtra(ItemDetailConstants.GUID)){
			guid = getIntent().getStringExtra(ItemDetailConstants.GUID);
			StSDK.getInstance().getPlaceDetailed(guid, detailBack);
		} else {
			showErrorAndFinish();
		}




	}

	private void proceedWithLoadedReferences(){
		adapter = new ReferenceListAdapter(
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


	private ReferenceListAdapter.ReferenceViewHolder.ReferenceClick getReferenceClick(){
		return new ReferenceListAdapter.ReferenceViewHolder.ReferenceClick() {
			@Override
			public void referenceClicked(Reference reference) {
				ItemDetailReferenceUtils.showReferenceUrl(
					ReferencesListActivity.this,
					new ReferenceWrapper(reference),
					ItemDetailReferenceUtils.DETAIL
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
