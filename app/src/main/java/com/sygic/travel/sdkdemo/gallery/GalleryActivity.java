package com.sygic.travel.sdkdemo.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sygic.travel.sdk.StSDK;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.media.Media;
import com.sygic.travel.sdkdemo.R;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.List;

import static com.sygic.travel.sdk.model.place.Place.GUID;

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
		StSDK.getInstance().getPlaceMedia(guid, new MediaCallback());
	}

	private void initRecycler() {
		rvGallery = (RecyclerView) findViewById(R.id.rv_gallery);
		rvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvGallery.addItemDecoration(new DividerDecoration(this, R.drawable.line_divider));
		rvGallery.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		galleryAdapter = new GalleryAdapter(getOnPhotoClick(), Utils.getDetailPhotoSize(this));
		rvGallery.setAdapter(galleryAdapter);
	}

	private GalleryAdapter.ViewHolder.galleryPhotoClick getOnPhotoClick() {
		return new GalleryAdapter.ViewHolder.galleryPhotoClick() {
			@Override
			public void onPhotoClick(int position) {

			}
		};
	}

	private class MediaCallback extends Callback<List<Media>> {
		@Override
		public void onSuccess(List<Media> gallery) {
			GalleryActivity.this.gallery = gallery;
			galleryAdapter.setGallery(gallery);
			galleryAdapter.notifyDataSetChanged();
		}

		@Override
		public void onFailure(Throwable t) {

		}
	}
}
