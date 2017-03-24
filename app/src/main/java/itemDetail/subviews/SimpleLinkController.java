package itemDetail.subviews;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import itemDetail.Duration;
import itemDetail.PlaceDetailReferenceUtils;
import itemDetail.ReferenceWrapper;
import itemDetail.fragment.PlaceDetailFragmentFactories;


public class SimpleLinkController implements PlaceDetailSubview {
	private PlaceDetailSubviewModel dependenciesData;
	private View rootView;
	private TextView tvTitle, tvCount, tvSubtext;
	private ImageView ivIcon;
	private String navInfoPattern;

	public SimpleLinkController(PlaceDetailSubviewModel dependenciesData) {
		this.dependenciesData = dependenciesData;
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, PlaceDetailFragmentFactories factories) {
		rootView = activity.getLayoutInflater().inflate(R.layout.row_action_layout, null);
		tvTitle = (TextView) rootView.findViewById(R.id.tv_link_title);
		tvCount = (TextView) rootView.findViewById(R.id.tv_link_count);
		ivIcon = (ImageView) rootView.findViewById(R.id.sdv_link_icon);
		tvSubtext = (TextView) rootView.findViewById(R.id.tv_subtext);

		switch(dependenciesData.getType()){
			case PlaceDetailSubviewModel.REFERENCE:{
				renderReference(activity);
				break;
			}

			case PlaceDetailSubviewModel.MULTIPLE_REFERENCES:{
				renderMultipleReferences(factories);
				break;
			}

			case PlaceDetailSubviewModel.PHONE_NUMBER:{
				renderPhoneNumber(factories);
				break;
			}

			case PlaceDetailSubviewModel.EMAIL:{
				rederEmail(factories);
				break;
			}

			case PlaceDetailSubviewModel.ADD_NOTE:{
				renderAddNote(factories, activity);
				break;
			}

			case PlaceDetailSubviewModel.DURATION:{
				renderDuration(factories);
				break;
			}

			case PlaceDetailSubviewModel.LAT_LNG_DRIVE:{
				loadDrive(activity);
				break;
			}
		}
		llContent.addView(rootView);
	}

	private void loadDrive(final Activity activity) {
		final LatLngModel llModel = (LatLngModel) dependenciesData;
		show(activity.getString(R.string.item_detail_drive), R.drawable.ic_car);
		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//activity.navigateToPoi(new SKCoordinate(llModel.getLng(), llModel.getLat()));
			}
		});
	}

	private void renderDuration(PlaceDetailFragmentFactories factories) {
		SimpleDetailModel model = (SimpleDetailModel) dependenciesData;
		show(model.getData(), R.drawable.ic_time);
		renderCount("\u00b1 " + new Duration(model.getNumber()).getShortFormattedDuration());
		rootView.setOnClickListener(factories.getUserDataActivityListener());
	}

	private void renderAddNote(PlaceDetailFragmentFactories factories, Activity activity) {
		String data = ((SimpleDetailModel) dependenciesData).getData();
		show(data, R.drawable.ic_add_note);
		rootView.setOnClickListener(factories.getUserDataActivityListener());
		LinearLayout llContainer = (LinearLayout) rootView.findViewById(R.id.ll_container);
		llContainer.addView(activity.getLayoutInflater().inflate(R.layout.thick_divider, null));
	}

	private void rederEmail(PlaceDetailFragmentFactories factories) {
		String email = ((SimpleDetailModel) dependenciesData).getData();
		show(R.string.custom_place_hint_email, R.drawable.ic_mail);
		showTvSubtext(email);
		rootView.setOnClickListener(factories.getOnEmailClickListener(email));
	}

	private void renderPhoneNumber(PlaceDetailFragmentFactories factories) {
		String phoneNumber = ((SimpleDetailModel) dependenciesData).getData();
		show(R.string.custom_place_hint_phone, R.drawable.ic_phone);
		showTvSubtext(phoneNumber);
		rootView.setOnClickListener(factories.getOnPhoneClickListener(phoneNumber));
	}

	private void renderMultipleReferences(final PlaceDetailFragmentFactories factories) {
		final MultipleReferencesModel multipleReferencesModel = (MultipleReferencesModel)dependenciesData;
		String type = PlaceDetailReferenceUtils.normalizeReferenceType(multipleReferencesModel.getReference().getType());
		switch(type){
			case PlaceDetailReferenceUtils.TOUR:{
				show(R.string.item_detail_tour, R.drawable.ic_detail_tours);
				break;
			}
			case PlaceDetailReferenceUtils.PASS:{
				show(R.string.item_detail_accepted_passes, R.drawable.ic_passes);
				break;
			}
			case PlaceDetailReferenceUtils.SIMPLE_LINK:{
				show(R.string.item_detail_external_link, R.drawable.ic_links);
				break;
			}
		}

		renderCount(multipleReferencesModel.getOtherReferences().size() + "");
		rootView.setOnClickListener(factories.getOnReferenceListClickListener());
	}

	private void renderReference(final Activity activity) {
		final Reference reference = ((MultipleReferencesModel)dependenciesData).getReference();
		String type = PlaceDetailReferenceUtils.normalizeReferenceType(reference.getType());
		switch(type){
			case PlaceDetailReferenceUtils.OFFICIAL:{
				show(R.string.item_detail_official_website, R.drawable.ic_website);
				showTvSubtext(reference.getUrl());
				break;
			}
			case PlaceDetailReferenceUtils.FACEBOOK:{
				show(R.string.item_detail_facebook, R.drawable.ic_fb);
				showTvSubtext(reference.getUrl());
				break;
			}
			case PlaceDetailReferenceUtils.WIKI:{
				show(R.string.item_detail_wikipedia, R.drawable.ic_wiki);
				break;
			}
		}

		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PlaceDetailReferenceUtils.showReferenceUrl(
					activity,
					new ReferenceWrapper(reference),
					PlaceDetailReferenceUtils.DETAIL
				);
			}
		});

	}

	private void show(int stringId, int iconId){
		tvTitle.setText(stringId);
		ivIcon.setImageResource(iconId);
	}

	private void show(String string, int iconId){
		tvTitle.setText(string);
		ivIcon.setImageResource(iconId);
	}

	public void setVisibility(boolean visible){
		if(visible){
			rootView.setVisibility(View.VISIBLE);
		} else{
			rootView.setVisibility(View.GONE);
		}
	}

	private void showTvSubtext(String text){
		tvSubtext.setVisibility(View.VISIBLE);
		tvSubtext.setText(text);
	}

	private void renderCount(String count){
		tvCount.setVisibility(View.VISIBLE);
		tvCount.setText(count);
	}
}
