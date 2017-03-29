package com.sygic.travel.sdkdemo.gallery;

/**
 * Created by michal.murin on 29.3.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sygic.travel.sdk.model.media.Media;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

	private final ViewHolder.galleryPhotoClick galleryPhotoClick;
	private int photoSize;
	private List<Media> gallery;

	public GalleryAdapter(
		ViewHolder.galleryPhotoClick galleryPhotoClick,
		int photoSize
	) {
		this.galleryPhotoClick = galleryPhotoClick;
		this.photoSize = photoSize;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vItem = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.gallery_item, parent, false);
		return new ViewHolder(vItem, galleryPhotoClick);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Picasso
			.with(holder.ivPhoto.getContext())
			.load(gallery.get(position).getUrlTemplate().replace("{size}", photoSize + "x" + photoSize))
			.placeholder(R.drawable.ic_photo_camera)
			.into(holder.ivPhoto);
	}

	@Override
	public int getItemCount() {
		if(gallery == null) {
			return 0;
		}
		return gallery.size();
	}

	public void setGallery(List<Media> gallery) {
		this.gallery = gallery;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public ImageView ivPhoto;
		private final galleryPhotoClick galleryPhotoClick;

		public ViewHolder(
			View vItem,
			galleryPhotoClick galleryPhotoClick
		) {
			super(vItem);
			this.galleryPhotoClick = galleryPhotoClick;

			ivPhoto = (ImageView) vItem.findViewById(R.id.iv_gallery_photo);
			vItem.setOnClickListener(this);
		}

		public interface galleryPhotoClick {
			void onPhotoClick(int position);
		}

		@Override
		public void onClick(View v) {
			galleryPhotoClick.onPhotoClick(getAdapterPosition());
		}
	}
}
