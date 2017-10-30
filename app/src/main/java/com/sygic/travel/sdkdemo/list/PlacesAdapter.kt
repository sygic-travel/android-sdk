package com.sygic.travel.sdkdemo.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdkdemo.R

class PlacesAdapter(
	private val placeClick: PlacesAdapter.ViewHolder.PlaceClick,
	private val photoSize: String
) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {
	private var places: List<PlaceInfo>? = null

	override fun getItemCount(): Int {
		if (places != null) {
			return places!!.size
		}
		return 0
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val vItem = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.places_item, parent, false)
		return PlacesAdapter.ViewHolder(vItem, placeClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		var photoUrl = "url"

		if (places!![position].thumbnailUrl != null) {
			photoUrl = places!![position].thumbnailUrl!!
		}

		Picasso
			.with(holder.ivPlacePhoto.context)
			.load(photoUrl)
			.placeholder(R.drawable.ic_photo_camera)
			.resizeDimen(R.dimen.places_item_photo_size, R.dimen.places_item_photo_size)
			.into(holder.ivPlacePhoto)

		holder.tvPlaceName.text = places!![position].name
	}

	fun setPlaces(places: List<PlaceInfo>) {
		this.places = places
	}

	class ViewHolder(vItem: View, private val placeClick: ViewHolder.PlaceClick) : RecyclerView.ViewHolder(vItem), View.OnClickListener {
		internal val ivPlacePhoto: ImageView = vItem.findViewById(R.id.iv_place_item_photo)
		internal val tvPlaceName: TextView = vItem.findViewById(R.id.tv_place_item_name)

		init {
			vItem.setOnClickListener(this)
		}

		override fun onClick(v: View) {
			placeClick.onPlaceClick(adapterPosition)
		}

		interface PlaceClick {
			fun onPlaceClick(position: Int)
		}
	}
}
