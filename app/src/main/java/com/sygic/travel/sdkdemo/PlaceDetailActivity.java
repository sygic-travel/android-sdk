package com.sygic.travel.sdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.TagStats;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;

public class PlaceDetailActivity extends AppCompatActivity {
	private static final String TAG = PlaceDetailActivity.class.getSimpleName();

	private String guid;
	private Views views;
	private String pricePattern, ratingPattern;
	private int tagPadding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_detail);

		guid = getIntent().getStringExtra(GUID);
		views = new Views();
		pricePattern = "Price: %.2f";
		ratingPattern = "Rating: %.2f";
		tagPadding = getResources().getDimensionPixelSize(R.dimen.tag_padding);
		loadPlaceDetail();
	}

	private void loadPlaceDetail() {
		StSDK.getInstance().getPlaceDetailed(guid, new PlaceDetailCallback());
	}

	private void renderPlaceDetail(Detail placeDetail) {
//		Picasso.
//			with(this)
//			.load(Utils.getPhotoUrl(this, placeDetail.getMainMedia().getUrlTemplate()))
//			.into(views.ivPhoto);
		views.tvName.setText(placeDetail.getName());
		views.tvNameSuffix.setText(placeDetail.getNameSuffix());
		views.tvPerex.setText(placeDetail.getPerex());
		views.tvDescription.setText(placeDetail.getDescription().getText());
		if(placeDetail.getPrice() != null) {
			views.tvPrice.setText(String.format(pricePattern, placeDetail.getPrice().getValue()));
		}
		views.tvRating.setText(String.format(ratingPattern, placeDetail.getRating()));
		views.tvAddress.setText(placeDetail.getAddress());
		views.tvPhone.setText(placeDetail.getPhone());
		views.tvEmail.setText(placeDetail.getEmail());
		views.tvAdmission.setText(placeDetail.getAdmission());
		views.tvOpeningHours.setText(placeDetail.getOpeningHours());
		renderTags(placeDetail.getTags());
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

	private TextView createFlexTagTextView(String tag) {
		TextView textView = new TextView(this);
		textView.setBackgroundResource(R.drawable.tag_background);
		textView.setText(tag);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(tagPadding, tagPadding, tagPadding,tagPadding);
		return textView;
	}

	private class PlaceDetailCallback extends Callback<Detail> {
		@Override
		public void onSuccess(Detail placeDetail) {
			renderPlaceDetail(placeDetail);
		}

		@Override
		public void onFailure(Throwable t) {

		}
	}

	private class Views {
		ImageView ivPhoto;
		TextView tvName, tvNameSuffix, tvPerex, tvDescription, tvPrice, tvRating, tvAddress, tvPhone,
				tvEmail, tvAdmission, tvOpeningHours;
		FlexboxLayout fblTags;

		Views(){
			ivPhoto = (ImageView) findViewById(R.id.iv_photo);
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
		}
	}
}
