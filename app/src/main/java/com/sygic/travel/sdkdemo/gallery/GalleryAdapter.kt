package com.sygic.travel.sdkdemo.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.sygic.travel.sdk.places.model.media.Medium
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.utils.Utils

class GalleryAdapter(
	private val galleryPhotoClick: ViewHolder.GalleryPhotoClick,
	private val photoSize: String
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
	private var gallery: List<Medium>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val vItem = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.gallery_item, parent, false)
		return ViewHolder(vItem, galleryPhotoClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		Picasso
			.with(holder.ivPhoto.context)
			.load(gallery?.get(position)?.urlTemplate?.
				replace(Utils.PHOTO_SIZE_PLACEHOLDER, photoSize))
			.placeholder(R.drawable.ic_photo_camera)
			.into(holder.ivPhoto)
	}

	override fun getItemCount(): Int {
		if (gallery != null) {
			return gallery!!.size
		} else {
			return 0
		}
	}

	fun setGallery(gallery: List<Medium>) {
		this.gallery = gallery
	}

	class ViewHolder(
		vItem: View,
		private val galleryPhotoClick: ViewHolder.GalleryPhotoClick
	) : RecyclerView.ViewHolder(vItem), View.OnClickListener {
		var ivPhoto: ImageView = vItem.findViewById(R.id.iv_gallery_photo)

		init {
			vItem.setOnClickListener(this)
		}

		interface GalleryPhotoClick {
			fun onPhotoClick(position: Int, ivPhoto: ImageView)
		}

		override fun onClick(v: View) {
			galleryPhotoClick.onPhotoClick(adapterPosition, ivPhoto)
		}
	}
}
