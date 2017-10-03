package com.sygic.travel.sdkdemo.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.sygic.travel.sdk.StSDK
import com.sygic.travel.sdkdemo.utils.UiCallback
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.filters.CategoriesAdapter
import com.sygic.travel.sdkdemo.filters.CategoriesDialog
import com.sygic.travel.sdkdemo.utils.Utils
import java.util.*

class PlacesListActivity : AppCompatActivity() {
	private lateinit var stSdk: StSDK
	private var rvPlaces: RecyclerView? = null
	private var placesAdapter: PlacesAdapter? = null
	private var places: List<Place>? = null

	private var categoriesDialog: CategoriesDialog? = null
	private val selectedCateoriesKeys = ArrayList<String>()
	private var titlePattern: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		stSdk = (application as Application).stSdk
		setContentView(R.layout.activity_list)

		categoriesDialog = CategoriesDialog(this, onCategoriesClick)
		titlePattern = getString(R.string.title_activity_places) + " - %s"

		initRecycler()
		loadPlaces()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// There's only one option in the menu - categories filter.
		menuInflater.inflate(R.menu.places, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.action_filter_places) {
			// Show dialog with categories
			categoriesDialog!!.show()
		}
		return super.onOptionsItemSelected(item)
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
			val placeDetailIntent = Intent(this@PlacesListActivity, PlaceDetailActivity::class.java)
			placeDetailIntent.putExtra(ID, places?.get(position)?.id)
			startActivity(placeDetailIntent)
		}
	}

	// Use the SDK to load places
	private fun loadPlaces() {
		val query = PlacesQuery()
		query.levels = listOf("poi")
		query.categories = selectedCateoriesKeys
		query.parents = listOf("city:1")
		query.limit = 128
		stSdk.getPlaces(query, placesCallback)
	}

	private fun renderPlacesList(places: List<Place>) {
		this.places = places
		placesAdapter!!.setPlaces(places)
		placesAdapter!!.notifyDataSetChanged()
	}

	// On a category click listener.
	private val onCategoriesClick = object : CategoriesAdapter.ViewHolder.CategoryClick {
		override fun onCategoryClick(categoryKey: String, categoryName: String) {
			if (selectedCateoriesKeys.contains(categoryKey)) {
				categoriesDialog!!.dismiss()
				return
			}
			if (categoryKey == "all") {
				selectedCateoriesKeys.clear()
				title = getString(R.string.title_activity_places)  // Set activity's title
			} else {
				selectedCateoriesKeys.add(categoryKey)
				title = String.format(titlePattern!!, categoryName)  // Set activity's title
			}
			loadPlaces()  // Reload places
			categoriesDialog!!.dismiss()
		}
	}

	private val placesCallback = object : UiCallback<List<Place>?>(this) {
		override fun onSuccess(data: List<Place>?) {
			// Places are sorted by rating, best rated places are at the top of the list
			Collections.sort(data) { p1, p2 ->
				if (p1.rating == p2.rating) {
					0
				} else {
					if (p1.rating > p2.rating) -1 else 1
				}
			}
			runOnUiThread {
				renderPlacesList(data!!)
			}
		}

		override fun onUiFailure(exception: Throwable) {
			Toast.makeText(this@PlacesListActivity, exception.message, Toast.LENGTH_LONG).show()
			exception.printStackTrace()
		}
	}

	companion object {
		private val TAG = PlacesListActivity::class.java.simpleName

		val ID = "id"
	}
}
