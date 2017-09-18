package com.sygic.travel.sdkdemo.tours

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.tours.ToursAdapter.ViewHolder


internal class ToursAdapter(
	private val onClickListener: ListItemClickListener
) : RecyclerView.Adapter<ViewHolder>() {

	private var tours: List<Tour>? = null

	override fun getItemCount(): Int {
		return tours?.size ?: 0
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		var photoUrl = "url"
		val tour = tours?.get(position)

		if (tour?.photoUrl != null) {
			photoUrl = tour.photoUrl!!
		}

		Picasso
			.with(holder.ivTourPhoto.context)
			.load(photoUrl)
			.placeholder(R.drawable.ic_photo_camera)
			.resizeDimen(R.dimen.tours_item_photo_size, R.dimen.tours_item_photo_size)
			.into(holder.ivTourPhoto)

		holder.tvTourName.text = tour?.title
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val vItem = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.tours_item, parent, false)
		return ViewHolder(vItem)
	}

	fun setTours(tours: List<Tour>?) {
		this.tours = tours
	}

	/**
	 * The interface that receives onClick.
	 */
	interface ListItemClickListener {
		fun onListItemClick(clickedItemIndex: Int)
	}

	internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
		internal val ivTourPhoto: ImageView = itemView.findViewById(R.id.iv_tour_item_photo)
		internal val tvTourName: TextView = itemView.findViewById(R.id.tv_tour_item_name)

		init {
			itemView.setOnClickListener(this)
		}

		override fun onClick(v: View?) {
			val clickedPosition = adapterPosition
			onClickListener.onListItemClick(clickedPosition)
		}
	}
}