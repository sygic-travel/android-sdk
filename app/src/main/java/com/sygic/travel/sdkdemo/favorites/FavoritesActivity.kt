package com.sygic.travel.sdkdemo.favorites

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.list.PlacesAdapter
import com.sygic.travel.sdkdemo.utils.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoritesActivity : AppCompatActivity() {
	private lateinit var sdk: Sdk
	private var rvPlaces: RecyclerView? = null
	private var placesAdapter: PlacesAdapter? = null
	private var places: List<Place>? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list)
		sdk = (application as Application).sdk

		initRecycler()
	}

	override fun onResume() {
		super.onResume()
		loadFavoritesIds()
	}

	// Recycler view initialization - list with dividers
	private fun initRecycler() {
		rvPlaces = findViewById(R.id.rv_places)
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
		Single.fromCallable { sdk.favoritesFacade.getFavoritesIds() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ data ->
				loadFavorites(data!!)
			}, { exception ->
				Toast.makeText(this@FavoritesActivity, exception.message, Toast.LENGTH_LONG).show()
				exception.printStackTrace()
			})
	}

	// Use the SDK to load favorite place from api
	private fun loadFavorites(favoritesIds: List<String>) {
		Single.fromCallable { sdk.placesFacade.getPlacesDetailed(favoritesIds) }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ data ->
				renderPlacesList(data!!)
			}, { exception ->
				Toast.makeText(this@FavoritesActivity, exception.message, Toast.LENGTH_LONG).show()
				exception.printStackTrace()
			})
	}

	private fun renderPlacesList(places: List<Place>) {
		this.places = places
		placesAdapter!!.setPlaces(places)
		placesAdapter!!.notifyDataSetChanged()
	}

	companion object {
		val ID = "id"
	}
}
