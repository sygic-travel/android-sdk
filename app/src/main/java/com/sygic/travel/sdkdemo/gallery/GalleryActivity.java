package com.sygic.travel.sdkdemo.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.media.Medium;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {
	private static final String TAG = GalleryActivity.class.getSimpleName();

	public static final String ID = "id";
	public static final String THUMBNAIL_URL = "thumbnail_url";

	private RecyclerView rvGallery;
	private GalleryAdapter galleryAdapter;
	private List<Medium> gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		initRecycler();
		String id = getIntent().getStringExtra(ID);

		// Load photos from API, using id
		StSDK.getInstance().getPlaceMedia(id, getMediaCallback());
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Observables need to be unsubscribed, when the activity comes to background
		StSDK.getInstance().unsubscribeObservable();
	}

	private void initRecycler() {
		rvGallery = (RecyclerView) findViewById(R.id.rv_gallery);
		rvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvGallery.addItemDecoration(new DividerDecoration(this, R.drawable.line_divider));
		galleryAdapter = new GalleryAdapter(getOnPhotoClick(), Utils.getDetailPhotoSize(this));
		rvGallery.setAdapter(galleryAdapter);
	}

	// On a photo click listener. Opens new activity with the chosen photo.
	private GalleryAdapter.ViewHolder.GalleryPhotoClick getOnPhotoClick() {
		return new GalleryAdapter.ViewHolder.GalleryPhotoClick() {
			@Override
			public void onPhotoClick(int position, ImageView ivPhoto) {
				Intent photoIntent = new Intent(GalleryActivity.this, PhotoActivity.class);
				photoIntent.putExtra(THUMBNAIL_URL, gallery.get(position).getUrlTemplate());
				startActivity(photoIntent);
			}
		};
	}

	private Callback<List<Medium>> getMediaCallback() {
		return new Callback<List<Medium>>() {
			@Override
			public void onSuccess(List<Medium> gallery) {
				// Update the adapter's data.
				GalleryActivity.this.gallery = gallery;
				galleryAdapter.setGallery(gallery);
				galleryAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Media: onFailure");
				t.printStackTrace();
			}
		};
	}
}
