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
import com.sygic.travel.sdk.model.media.Media;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;
import static com.sygic.travel.sdk.model.place.Place.THUMBNAIL_URL;

public class GalleryActivity extends AppCompatActivity {
	private static final String TAG = GalleryActivity.class.getSimpleName();

	private String guid;
	private RecyclerView rvGallery;
	private GalleryAdapter galleryAdapter;
	private List<Media> gallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		initRecycler();
		guid = getIntent().getStringExtra(GUID);
		StSDK.getInstance().getPlaceMedia(guid, getMediaCallback());
	}

	private void initRecycler() {
		rvGallery = (RecyclerView) findViewById(R.id.rv_gallery);
		rvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvGallery.addItemDecoration(new DividerDecoration(this, R.drawable.line_divider));
//		rvGallery.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		galleryAdapter = new GalleryAdapter(getOnPhotoClick(), Utils.getDetailPhotoSize(this));
		rvGallery.setAdapter(galleryAdapter);
	}

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

	private Callback<List<Media>> getMediaCallback() {
		return new Callback<List<Media>>() {
			@Override
			public void onSuccess(List<Media> gallery) {
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
