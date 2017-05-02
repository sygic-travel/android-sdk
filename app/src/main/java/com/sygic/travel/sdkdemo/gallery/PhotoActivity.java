package com.sygic.travel.sdkdemo.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.Utils;

import static com.sygic.travel.sdk.model.place.Place.THUMBNAIL_URL;

public class PhotoActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		loadPhoto();
	}

	// Show a single photo
	private void loadPhoto() {
		String photoUrl;
		String photoUrlTemplate = getIntent().getStringExtra(THUMBNAIL_URL);
		if(photoUrlTemplate != null && !photoUrlTemplate.equals("")){
			photoUrl = photoUrlTemplate.replace(Utils.PHOTO_SIZE_PLACEHOLDER, Utils.getDetailPhotoSize(this));
		} else {
			photoUrl = "no-url";
		}

		Picasso
			.with(this)
			.load(photoUrl)
			.placeholder(R.drawable.ic_photo_camera)
			.into((ImageView) findViewById(R.id.iv_gallery_photo));
	}
}
