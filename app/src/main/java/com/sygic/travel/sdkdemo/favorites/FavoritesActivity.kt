package com.sygic.travel.sdkdemo.favorites

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.sygic.travel.sdk.StSDK
import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.list.PlacesAdapter
import com.sygic.travel.sdkdemo.utils.Utils

class FavoritesActivity : AppCompatActivity() {

	private var rvPlaces: RecyclerView? = null
	private var placesAdapter: PlacesAdapter? = null
	private var places: List<Place>? = null

	private val favoritesIdsCallback = object : Callback<List<String>?>() {
		override fun onSuccess(data: List<String>?) {
			// Places are sorted by rating, best rated places are at the top of the list
			loadFavorites(data!!)
		}

		override fun onFailure(t: Throwable) {
			runOnUiThread {
				Toast.makeText(this@FavoritesActivity, t.message, Toast.LENGTH_LONG).show()
			}
			t.printStackTrace()
		}
	}

	private val favoritesCallback = object : Callback<List<Place>?>() {
		override fun onSuccess(data: List<Place>?) {
			// Places are sorted by rating, best rated places are at the top of the list
			renderPlacesList(data!!)
		}

		override fun onFailure(t: Throwable) {
			runOnUiThread {
				Toast.makeText(this@FavoritesActivity, t.message, Toast.LENGTH_LONG).show()
			}
			t.printStackTrace()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list)

		initRecycler()
	}

	override fun onResume() {
		super.onResume()
		loadFavoritesIds()
	}

	// Recycler view initialization - list with dividers
	private fun initRecycler() {
		rvPlaces = findViewById(R.id.rv_places) as RecyclerView
		rvPlaces!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		rvPlaces!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
		placesAdapter = PlacesAdapter(onPlaceClick, Utils.getDetailPhotoSize(this))
		rvPlaces!!.adapter = placesAdapter
	}

	// On a place click listener. Opens it's detail.
	private val onPlaceClick = object : PlacesAdapter.ViewHolder.PlaceClick {
		override fun onPlaceClick(position: Int) {
			val placeDetailIntent = Intent(this@FavoritesActivity, PlaceDetailActivity::class.java)
			placeDetailIntent.putExtra(ID, places?.get(position)?.id)
			startActivity(placeDetailIntent)
		}
	}

	// Use the SDK to load favorite places' ids from database
	private fun loadFavoritesIds() {
		StSDK.getInstance().getFavoritesIds(favoritesIdsCallback)
	}

	// Use the SDK to load favorite place from api
	private fun loadFavorites(favoritesIds: List<String>) {
		StSDK.getInstance().getPlacesDetailed(favoritesIds, favoritesCallback)
	}

	private fun renderPlacesList(places: List<Place>) {
		this.places = places
		placesAdapter!!.setPlaces(places)
		placesAdapter!!.notifyDataSetChanged()
	}

	companion object {
		private val TAG = FavoritesActivity::class.java.simpleName

		val ID = "id"
	}
}
