package com.sygic.travel.sdkdemo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdk.model.place.TagStats;
import com.sygic.travel.sdkdemo.gallery.GalleryActivity;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

public class PlaceDetailActivity extends AppCompatActivity {
	private static final String TAG = PlaceDetailActivity.class.getSimpleName();

	public static final String ID = "id";
	public static final String REFERENCE_TITLE = "reference_title";
	public static final String REFERENCE_URL = "reference_url";

	private String id;
	private Views views;
	private String pricePattern, ratingPattern;
	private int tagPadding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_detail);

		id = getIntent().getStringExtra(ID);
		views = new Views();
		pricePattern = "Price: %.2f";
		ratingPattern = "Rating: %.2f";
		tagPadding = getResources().getDimensionPixelSize(R.dimen.tag_padding);
		loadPlaceDetail();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Observables need to be unsubscribed, when the activity comes to background
		StSDK.getInstance().unsubscribeObservable();
	}

	private void loadPlaceDetail() {
		// Use the SDK to load detailed information about a place
		StSDK.getInstance().getPlaceDetailed(id, getPlaceCallback());
	}

	private void renderPlaceDetail(Place place) {
		Detail detail = place.getDetail();
		String mediaUrlTemplate = "url";


		setTitle(place.getName());

		if(detail.getMainMedia() != null) {
			if(detail.getMainMedia().getMedia() != null && detail.getMainMedia().getMedia().size() > 0) {
				mediaUrlTemplate = detail.getMainMedia().getMedia().get(0).getUrlTemplate();
			}
		}

		if(!mediaUrlTemplate.equals("")) {
			Picasso
				.with(this)
				.load(Utils.getPhotoUrl(this, mediaUrlTemplate))
				.placeholder(R.drawable.ic_photo_camera)
				.into(views.ivPhoto);
		}
		views.ivPhoto.setOnClickListener(getOnPhotoClickListener());

		views.tvName.setText(place.getName());
		views.tvNameSuffix.setText(place.getNameSuffix());
		views.tvPerex.setText(place.getPerex());
		if(detail.getDescription() != null) {
			views.tvDescription.setText(detail.getDescription().getText());
		}
		if(place.getPrice() != null) {
			views.tvPrice.setText(String.format(pricePattern, place.getPrice().getValue()));
		}
		views.tvRating.setText(String.format(ratingPattern, place.getRating()));
		views.tvAddress.setText(detail.getAddress());
		views.tvPhone.setText(detail.getPhone());
		views.tvEmail.setText(detail.getEmail());
		views.tvAdmission.setText(detail.getAdmission());
		views.tvOpeningHours.setText(detail.getOpeningHours());
		renderTags(detail.getTags());
		renderReferences(detail.getReferences());
	}

	private View.OnClickListener getOnPhotoClickListener() {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGallery();
			}
		};
	}

	private void startGallery() {
		Intent galleryIntent = new Intent(this, GalleryActivity.class);
		galleryIntent.putExtra(ID, id);
		startActivity(galleryIntent);
	}

	private void renderTags(List<TagStats> tags) {
		if(tags == null){
			views.fblTags.setVisibility(View.GONE);
		} else {
			for(TagStats tag : tags) {
				TextView tvTag = createFlexTagTextView(tag.getName());
				views.fblTags.addView(tvTag);
				((FlexboxLayout.LayoutParams) tvTag.getLayoutParams())
					.setMargins(0, 0, tagPadding, tagPadding);
			}
		}
	}

	private void renderReferences(List<Reference> references) {
		if(references != null){
			for(Reference reference : references) {
				TextView tvReference = new TextView(this);
				tvReference.setText(getReferenceText(reference));
				tvReference.setClickable(true);
				int[] attrs = new int[]{R.attr.selectableItemBackground};
				TypedArray typedArray = obtainStyledAttributes(attrs);
				int backgroundResource = typedArray.getResourceId(0, 0);
				tvReference.setBackgroundResource(backgroundResource);
				typedArray.recycle();
				tvReference.setOnClickListener(getOnReferenceClickListener(reference.getUrl(), reference.getTitle()));
				tvReference.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.reference_padding));
				views.llReferencesList.addView(tvReference);
			}
		}
	}

	private View.OnClickListener getOnReferenceClickListener(
		final String referenceUrl,
		final String referenceTitle
	) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(referenceUrl != null && !referenceUrl.equals("")){
					Intent referenceIntent = new Intent(PlaceDetailActivity.this, ReferenceActivity.class);
					referenceIntent.putExtra(REFERENCE_URL, referenceUrl);
					referenceIntent.putExtra(REFERENCE_TITLE, referenceTitle);
					startActivity(referenceIntent);
				} else {
					Toast.makeText(PlaceDetailActivity.this, "No reference URL.", Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	private String getReferenceText(Reference reference) {
		StringBuilder referenceBuilder = new StringBuilder();
		referenceBuilder
			.append(reference.getTitle())
			.append("\n")
			.append("Price: $")
			.append(reference.getPrice())
			.append("\n")
			.append("URL: ")
			.append(reference.getUrl());
		return referenceBuilder.toString();
	}

	private TextView createFlexTagTextView(String tag) {
		TextView textView = new TextView(this);
		textView.setBackgroundResource(R.drawable.tag_background);
		textView.setText(tag);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(tagPadding, tagPadding, tagPadding,tagPadding);
		return textView;
	}

	// This callback is passed to SDK's method for loading detailed information
	private Callback<Place> getPlaceCallback(){
		return new Callback<Place>() {
			@Override
			public void onSuccess(Place placeDetailed) {
				// if successful, the SDK return specific data, so it can be displayed
				renderPlaceDetail(placeDetailed);
			}

			@Override
			public void onFailure(Throwable t) {
				Toast.makeText(PlaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
			}
		};
	}

	private class Views {
		ImageView ivPhoto;
		TextView tvName, tvNameSuffix, tvPerex, tvDescription, tvPrice, tvRating, tvAddress, tvPhone,
				tvEmail, tvAdmission, tvOpeningHours;
		FlexboxLayout fblTags;
		LinearLayout llReferencesList;

		Views(){
			ivPhoto = (ImageView) findViewById(R.id.iv_detail_photo);
			tvName = (TextView) findViewById(R.id.tv_name);
			tvNameSuffix = (TextView) findViewById(R.id.tv_name_suffix);
			tvPerex = (TextView) findViewById(R.id.tv_perex);
			tvDescription = (TextView) findViewById(R.id.tv_description);
			tvPrice = (TextView) findViewById(R.id.tv_price);
			tvRating = (TextView) findViewById(R.id.tv_rating);
			tvAddress = (TextView) findViewById(R.id.tv_address);
			tvPhone = (TextView) findViewById(R.id.tv_phone);
			tvEmail = (TextView) findViewById(R.id.tv_email);
			tvAdmission = (TextView) findViewById(R.id.tv_admission);
			tvOpeningHours = (TextView) findViewById(R.id.tv_opening_hours);
			fblTags = (FlexboxLayout) findViewById(R.id.fbl_tags);
			llReferencesList = (LinearLayout) findViewById(R.id.ll_references_list);
		}
	}
}
