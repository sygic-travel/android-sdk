package com.sygic.travel.sdkdemo.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

/**
 * Created by michal.murin on 31.3.2017.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

	private PlacesAdapter.ViewHolder.PlaceClick placeClick;
	private String photoSize;
	private List<Place> places;

	public PlacesAdapter(
		PlacesAdapter.ViewHolder.PlaceClick placeClick,
		String photoSize
	) {
		this.placeClick = placeClick;
		this.photoSize = photoSize;
	}

	@Override
	public int getItemCount() {
		if(places != null) {
			return places.size();
		}
		return 0;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View vItem = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.places_item, parent, false);
		return new PlacesAdapter.ViewHolder(vItem, placeClick);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		String photoUrl = "url";

		if(places.get(position).getThumbnailUrl() != null) {
			photoUrl = places.get(position).getThumbnailUrl();
		}

		Picasso
			.with(holder.ivPlacePhoto.getContext())
			.load(photoUrl)
			.placeholder(R.drawable.ic_photo_camera)
			.resizeDimen(R.dimen.places_item_photo_size, R.dimen.places_item_photo_size)
			.into(holder.ivPlacePhoto);

		holder.tvPlaceName.setText(places.get(position).getName());
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private final ImageView ivPlacePhoto;
		private final TextView tvPlaceName;
		private final PlaceClick placeClick;

		public ViewHolder(View vItem, PlaceClick placeClick) {
			super(vItem);
			this.placeClick = placeClick;

			ivPlacePhoto = (ImageView) vItem.findViewById(R.id.iv_place_item_photo);
			tvPlaceName = (TextView) vItem.findViewById(R.id.tv_place_item_name);
			vItem.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			placeClick.onPlaceClick(getAdapterPosition());
		}

		public interface PlaceClick {
			void onPlaceClick (int position);
		}
	}
}
