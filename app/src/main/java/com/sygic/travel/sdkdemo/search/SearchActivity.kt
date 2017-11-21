package com.sygic.travel.sdkdemo.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdk.places.model.query.PlacesQuery
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.list.PlacesAdapter
import com.sygic.travel.sdkdemo.utils.Utils
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.Collections

class SearchActivity : AppCompatActivity() {
	private lateinit var sdk: Sdk
	private var rvPlaces: RecyclerView? = null
	private var placesAdapter: PlacesAdapter? = null
	private var places: List<PlaceInfo>? = null
	private var lastQuery: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search)
		sdk = (application as Application).sdk

		initRecycler()
		loadPlaces(null)
	}

	// Recycler view initialization - list with dividers
	private fun initRecycler() {
		rvPlaces = findViewById(R.id.rv_result_places)
		rvPlaces!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		rvPlaces!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
		placesAdapter = PlacesAdapter(onPlaceClick, Utils.getDetailPhotoSize(this))
		rvPlaces!!.adapter = placesAdapter
	}

	// On a place click listener. Opens it's detail.
	private val onPlaceClick: PlacesAdapter.ViewHolder.PlaceClick
		get() = object : PlacesAdapter.ViewHolder.PlaceClick {
			override fun onPlaceClick(position: Int) {
				val placeDetailIntent = Intent(this@SearchActivity, PlaceDetailActivity::class.java)
				placeDetailIntent.putExtra(ID, places!![position].id)
				startActivity(placeDetailIntent)
			}
		}

	// Use the SDK to load places
	private fun loadPlaces(searchQuery: String?) {
		val query = PlacesQuery()
		query.query = searchQuery
		query.levels = listOf("poi")
		query.parents = listOf("city:1")
		query.limit = 128

		Single.fromCallable {
			// Places are sorted by rating, best rated places are at the top of the list
			sdk.placesFacade.getPlaces(query)
		}
			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.io())
			.subscribe({ data ->
				Collections.sort(data) { p1, p2 ->
					if (p1.rating == p2.rating) {
						0
					} else {
						if (p1.rating > p2.rating) -1 else 1
					}
				}
				runOnUiThread {
					renderPlacesList(data)
				}
			}, { exception ->
				Toast.makeText(this@SearchActivity, exception.message, Toast.LENGTH_LONG).show()
				exception.printStackTrace()
			})
	}

	private fun renderPlacesList(places: List<PlaceInfo>?) {
		this.places = places
		placesAdapter!!.setPlaces(places!!)
		placesAdapter!!.notifyDataSetChanged()
	}

	// SEARCH

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.search, menu)
		val searchItem = menu.findItem(R.id.action_search)
		setSearchListeners(searchItem)
		return true
	}

	// Sets listners for search edit text.
	private fun setSearchListeners(searchItem: MenuItem) {
		val searchView = searchItem.actionView as SearchView
		val searchEdit = searchView.findViewById<EditText>(R.id.search_src_text)

		searchEdit.imeOptions = EditorInfo.IME_ACTION_SEARCH
		searchEdit.setOnEditorActionListener(getOnKeyboardEnterClickListener())
		searchView.setOnQueryTextListener(onQueryTextListener)
	}


	private fun getOnKeyboardEnterClickListener(): TextView.OnEditorActionListener {
		return TextView.OnEditorActionListener { tv, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				search(tv.text.toString())
			}
			false
		}
	}

	private val onQueryTextListener: SearchView.OnQueryTextListener
		get() = object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				search(query)
				return true
			}

			override fun onQueryTextChange(query: String): Boolean {
				search(query)
				return true
			}
		}

	fun search(query: String) {
		if (query != lastQuery) {
			lastQuery = query
			loadPlaces(query)
		}
	}

	companion object {
		private val ID = "id"
	}
}
