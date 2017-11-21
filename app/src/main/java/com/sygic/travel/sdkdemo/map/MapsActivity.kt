package com.sygic.travel.sdkdemo.map

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdk.places.geo.quadkey.QuadkeysGenerator
import com.sygic.travel.sdk.places.geo.spread.CanvasSize
import com.sygic.travel.sdk.places.geo.spread.SpreadResult
import com.sygic.travel.sdk.places.geo.spread.SpreadedPlace
import com.sygic.travel.sdk.places.geo.spread.Spreader
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdk.places.model.geo.Bounds
import com.sygic.travel.sdk.places.model.query.PlacesQuery
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.filters.CategoriesAdapter
import com.sygic.travel.sdkdemo.filters.CategoriesDialog
import com.sygic.travel.sdkdemo.utils.MarkerBitmapGenerator
import com.sygic.travel.sdkdemo.utils.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import java.util.HashMap

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
	private lateinit var sdk: Sdk
	private var map: GoogleMap? = null
	private var spreader: Spreader? = null

	private var categoriesDialog: CategoriesDialog? = null
	private val selectedCategoriesKeys = ArrayList<String>()
	private var titlePattern: String? = null

	private var vMain: View? = null

	internal var mapMovesCounter = 0
	private val currentMarkers = HashMap<String, Marker>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_maps)
		sdk = (application as Application).sdk

		vMain = findViewById(R.id.ll_main)
		spreader = Spreader(resources)
		categoriesDialog = CategoriesDialog(this, onCategoriesClick)
		titlePattern = getString(R.string.title_activity_maps) + " - %s"

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		val mapFragment = supportFragmentManager
			.findFragmentById(R.id.map) as SupportMapFragment
		mapFragment.getMapAsync(this)
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

	// Google maps specific method called, when the map is initialiazed and ready.
	override fun onMapReady(googleMap: GoogleMap) {
		map = googleMap

		// Center map to London
		val londonLatLng = LatLng(51.5116983, -0.1205079)
		map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(londonLatLng, 14f))

		// Set on marker's info window (bubble) click listener - starts place's detail activity.
		map!!.setOnInfoWindowClickListener { marker ->
			val id = marker.tag as String?
			val placeDetailIntent = Intent(this@MapsActivity, PlaceDetailActivity::class.java)
			placeDetailIntent.putExtra(ID, id)
			startActivity(placeDetailIntent)
		}

		// Set on camera move listener, so we can load new places
		map!!.setOnCameraMoveListener {
			if (++mapMovesCounter % 15 == 0) {
				removeOutOfBoundsMarkers()
				loadPlaces()
				mapMovesCounter = 0
			}
		}

		loadPlaces()
	}

	private fun removeOutOfBoundsMarkers() {
		val latLngBounds = map!!.projection.visibleRegion.latLngBounds
		val toRemove = ArrayList<String>()

		for (visibleMarker in currentMarkers.values) {
			if (!latLngBounds.contains(visibleMarker.position)) {
				toRemove.add((visibleMarker.tag as String?)!!)
			}
		}

		for (idToRemove in toRemove) {
			currentMarkers[idToRemove]?.remove()
			currentMarkers.remove(idToRemove)
		}
	}


	// Use the SDK to load places
	private fun loadPlaces() {
		// Generate quadkeys from the map's bondings and zoom
		val quadkeys = QuadkeysGenerator.generateQuadkeys(
			mapBounds,
			map!!.cameraPosition.zoom.toInt()
		)

		val query = PlacesQuery()
		query.levels = listOf("poi")
		query.categories = selectedCategoriesKeys
		query.mapTiles = quadkeys
		query.mapSpread = 1
		query.bounds = mapBounds
		query.parents = listOf("city:1")
		query.limit = 32

		Single.fromCallable { sdk.placesFacade.getPlaces(query) }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ data ->
				showPlacesOnMap(data)
			}, { exception ->
				Toast.makeText(this@MapsActivity, exception.message, Toast.LENGTH_LONG).show()
				exception.printStackTrace()
			})
	}

	// On category click listener
	private // Set activity's title
		// Reload places
	val onCategoriesClick: CategoriesAdapter.ViewHolder.CategoryClick
		get() = object : CategoriesAdapter.ViewHolder.CategoryClick {
			override fun onCategoryClick(categoryKey: String, categoryName: String) {
				if (selectedCategoriesKeys.contains(categoryKey)) {
					categoriesDialog!!.dismiss()
					return
				}
				if (categoryKey == "all") {
					selectedCategoriesKeys.clear()
					title = getString(R.string.title_activity_maps)
				} else {
					selectedCategoriesKeys.add(categoryKey)
					title = String.format(titlePattern!!, categoryName)
				}
				loadPlaces()
				categoriesDialog!!.dismiss()
			}
		}

	// Returns map's Bounds
	private // Map bounding_box are widened for the purposes of this sample. In a real app bounding_box without
		// the BOUNDS_OFFSET should be used.
	val mapBounds: Bounds
		get() {
			val latLngBounds = map!!.projection.visibleRegion.latLngBounds
			val bounds = Bounds()
			bounds.south = latLngBounds.southwest.latitude.toFloat()
			bounds.west = latLngBounds.southwest.longitude.toFloat()
			bounds.north = latLngBounds.northeast.latitude.toFloat()
			bounds.east = latLngBounds.northeast.longitude.toFloat()

			return bounds
		}

	private fun showPlacesOnMap(places: List<PlaceInfo>?) {
		// Spread loaded places
		val spreadResult = spreader!!.spreadPlacesOnMap(
			places,
			mapBounds,
			CanvasSize(vMain!!.measuredWidth, vMain!!.measuredHeight)
		)

		// Remove markers, which would not be visible with new spread result
		removeHiddenPlaces(spreadResult)

		// Remove markers which are on map, but no in new spread result
		removeOldMarkers(spreadResult)

		// Update icons for marker which stay on the map after new spread result
		updateCurrentMarkers(spreadResult)

		// Create markers for spread places
		createNewMarkers(spreadResult)
	}

	private fun removeHiddenPlaces(spreadResult: SpreadResult) {
		for (hiddenPlace in spreadResult.hiddenPlaces!!) {
			val removedMarker = currentMarkers.remove(hiddenPlace.id)
			removedMarker?.remove()
		}
	}

	private fun removeOldMarkers(spreadResult: SpreadResult) {
		val toRemove = ArrayList<String>()
		for (visibleMarkerId in currentMarkers.keys) {
			var shouldRemove = true
			for (spreadedPlace in spreadResult.visiblePlaces!!) {
				if (visibleMarkerId == spreadedPlace.place!!.id) {
					shouldRemove = false
					break
				}
			}
			if (shouldRemove) {
				toRemove.add(visibleMarkerId)
			}
		}

		for (idToRemove in toRemove) {
			currentMarkers[idToRemove]?.remove()
			currentMarkers.remove(idToRemove)
		}
	}

	private fun updateCurrentMarkers(spreadResult: SpreadResult) {
		for (spreadedPlace in spreadResult.visiblePlaces!!) {
			val visibleMarker = currentMarkers[spreadedPlace.place!!.id]
			visibleMarker?.setIcon(getMarkerBitmapDescriptor(spreadedPlace))
		}
	}

	private fun createNewMarkers(spreadResult: SpreadResult) {
		for (spreadedPlace in spreadResult.visiblePlaces!!) {
			val place = spreadedPlace.place
			if (currentMarkers.keys.contains(place!!.id)) {
				continue
			}

			val newMarker = map!!.addMarker(MarkerOptions()
				.position(LatLng(place.location!!.lat.toDouble(), place.location!!.lng.toDouble()))
				.title(place.name)
				.snippet(place.perex)
				.icon(getMarkerBitmapDescriptor(spreadedPlace))
			)
			newMarker.tag = place.id
			currentMarkers.put(place.id!!, newMarker)
		}
	}

	// This method returns bitmap descriptor, which is used for Google maps specifically
	private fun getMarkerBitmapDescriptor(spreadedPlace: SpreadedPlace): BitmapDescriptor? {
		try {
			// create marker's custom bitmap descriptor, if possible
			val markerBitmap = MarkerBitmapGenerator.createMarkerBitmap(this, spreadedPlace)
			if (markerBitmap != null) {
				return BitmapDescriptorFactory.fromBitmap(markerBitmap)
			} else {
				return null
			}
		} catch (e: Exception) {
			// If anything goes wrong, Google maps default pin with specific hue is returned.
			return BitmapDescriptorFactory.defaultMarker(getMarkerHue(spreadedPlace.place!!))
		}

	}

	// Return hue for Google maps default pin
	private fun getMarkerHue(place: PlaceInfo): Float {
		var markerHue = BitmapDescriptorFactory.HUE_RED
		if (place.categories != null && place.categories!!.isNotEmpty()) {
			markerHue = Utils.getMarkerHue(place.categories!![0])
		}
		return markerHue
	}

	companion object {
		val ID = "id"
	}
}
