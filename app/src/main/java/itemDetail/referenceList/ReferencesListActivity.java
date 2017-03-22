package itemDetail.referenceList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

import itemDetail.ItemDetailReferenceUtils;
import itemDetail.ReferenceWrapper;

public class ReferencesListActivity extends AppCompatActivity {
	public static final String REFERENCES_GUIDS = "referencesGuids";

	private List<Integer> ids;
	private String guid;
	private List<Reference> references;
	private ReferenceListAdapter adapter;
	private RecyclerView rvReferences;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reference_list_activity);
		//setToolbar();
		//supportActionBar.setDisplayHomeAsUpEnabled(true);

		if(getIntent().hasExtra(ItemDetailActivity.FEATURE_TITLE)){
			supportActionBar.setTitle(getIntent().getStringExtra(ItemDetailActivity.FEATURE_TITLE));
		} else {
			supportActionBar.setTitle("");
		}

		if(getIntent().hasExtra(REFERENCES_GUIDS) && getIntent().hasExtra(SygicTravel.GUID)){
			ids = getIntent().getIntegerArrayListExtra(REFERENCES_GUIDS);
			guid = getIntent().getStringExtra(SygicTravel.GUID);
			if(ids != null){
				references = ItemDetailReferenceUtils.getByIds(ids, guid, sygicTravel);
			}
		} else {
			Toast.makeText(this, "Did not get reference ids", Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		adapter = new ReferenceListAdapter(
			references,
			getReferenceClick(),
			sygicTravel.getOrm(),
			sygicTravel.getTracker(),
			getSharedPreferences(
				SygicTravel.TOM_PREFERENCES,
				0
			),
			getResources()
		);
		rvReferences = (RecyclerView)findViewById(R.id.rv_references);
		rvReferences.setLayoutManager(new LinearLayoutManager(this));
		rvReferences.setAdapter(adapter);
	}



	private ReferenceListAdapter.ReferenceViewHolder.ReferenceClick getReferenceClick(){
		return new ReferenceListAdapter.ReferenceViewHolder.ReferenceClick() {
			@Override
			public void referenceClicked(Reference reference) {
				ItemDetailReferenceUtils.showReferenceUrl(
					ReferencesListActivity.this,
					sygicTravel,
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
