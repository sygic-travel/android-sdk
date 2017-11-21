package com.sygic.travel.sdkdemo.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdk.places.model.Place
import com.sygic.travel.sdk.places.model.Reference
import com.sygic.travel.sdk.places.model.Tag
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.gallery.GalleryActivity
import com.sygic.travel.sdkdemo.utils.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlaceDetailActivity : AppCompatActivity() {
	private lateinit var sdk: Sdk
	private var views: Views? = null
	private var id: String? = null
	private var ratingPattern: String? = null
	private var tagPadding: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_place_detail)
		sdk = (application as Application).sdk

		id = intent.getStringExtra(ID)
		views = Views()
		ratingPattern = "Rating: %.2f"
		tagPadding = resources.getDimensionPixelSize(R.dimen.tag_padding)
		loadPlaceDetail()
		loadAllFavoritesIds()
	}

	private fun loadAllFavoritesIds() {
		Single.fromCallable { sdk.favoritesFacade.getPlaceIds() }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { data ->
				val isFavorite = data.contains(id)
				views?.cbFavorite?.isChecked = isFavorite
				setOnFavoriteChangeListener()
			}
	}

	private fun setOnFavoriteChangeListener() {
		views?.cbFavorite?.setOnCheckedChangeListener({ _, isChecked ->
			if (isChecked) {
				Single.fromCallable { sdk.favoritesFacade.addPlace(id!!) }
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({}, { exception ->
						Toast.makeText(this@PlaceDetailActivity, exception.message, Toast.LENGTH_LONG).show()
						exception.printStackTrace()
					})
			} else {
				Single.fromCallable { sdk.favoritesFacade.removePlace(id!!) }
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({}, { exception ->
						Toast.makeText(this@PlaceDetailActivity, exception.message, Toast.LENGTH_LONG).show()
						exception.printStackTrace()
					})
			}
		})
	}

	private fun loadPlaceDetail() {
		Single.fromCallable { sdk.placesFacade.getPlaceDetailed(id!!) }
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ data ->
				renderPlaceDetail(data!!)
			}, { exception ->
				Toast.makeText(this@PlaceDetailActivity, exception.message, Toast.LENGTH_LONG).show()
			})
	}

	private fun renderPlaceDetail(place: Place) {
		val detail = place.detail
		var mediaUrlTemplate = "url"


		title = place.name

		if (detail!!.mainMedia != null) {
			if (detail.mainMedia!!.media != null && detail.mainMedia!!.media!!.isNotEmpty()) {
				mediaUrlTemplate = detail.mainMedia!!.media!![0].urlTemplate!!
			}
		}

		if (mediaUrlTemplate != "") {
			Picasso
				.with(this)
				.load(Utils.getPhotoUrl(this, mediaUrlTemplate))
				.placeholder(R.drawable.ic_photo_camera)
				.into(views!!.ivPhoto)
		}
		views!!.ivPhoto.setOnClickListener(onPhotoClickListener)

		views!!.tvName.text = place.name
		views!!.tvNameSuffix.text = place.nameSuffix
		views!!.tvPerex.text = place.perex
		if (detail.description != null) {
			views!!.tvDescription.text = detail.description!!.text
		}
		views!!.tvRating.text = String.format(ratingPattern!!, place.rating)
		views!!.tvAddress.text = detail.address
		views!!.tvPhone.text = detail.phone
		views!!.tvEmail.text = detail.email
		views!!.tvAdmission.text = detail.admission
		views!!.tvOpeningHours.text = detail.openingHours
		renderTags(detail.tags)
		renderReferences(detail.references)
	}

	private val onPhotoClickListener: View.OnClickListener
		get() = View.OnClickListener { startGallery() }

	private fun startGallery() {
		val galleryIntent = Intent(this, GalleryActivity::class.java)
		galleryIntent.putExtra(ID, id)
		startActivity(galleryIntent)
	}

	private fun renderTags(tags: List<Tag>?) {
		if (tags == null) {
			views!!.fblTags.visibility = View.GONE
		} else {
			for (tag in tags) {
				val tvTag = createFlexTagTextView(tag.name!!)
				views!!.fblTags.addView(tvTag)
				(tvTag.layoutParams as FlexboxLayout.LayoutParams)
					.setMargins(0, 0, tagPadding, tagPadding)
			}
		}
	}

	private fun renderReferences(references: List<Reference>?) {
		if (references != null) {
			for (reference in references) {
				val tvReference = TextView(this)
				tvReference.text = getReferenceText(reference)
				tvReference.isClickable = true
				val attrs = intArrayOf(R.attr.selectableItemBackground)
				val typedArray = obtainStyledAttributes(attrs)
				val backgroundResource = typedArray.getResourceId(0, 0)
				tvReference.setBackgroundResource(backgroundResource)
				typedArray.recycle()
				tvReference.setOnClickListener(getOnReferenceClickListener(reference.url, reference.title!!))
				tvReference.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.reference_padding))
				views!!.llReferencesList.addView(tvReference)
			}
		}
	}

	private fun getOnReferenceClickListener(
		referenceUrl: String?,
		referenceTitle: String
	): View.OnClickListener {
		return View.OnClickListener {
			if (referenceUrl != null && referenceUrl != "") {
				val referenceIntent = Intent(this@PlaceDetailActivity, ReferenceActivity::class.java)
				referenceIntent.putExtra(REFERENCE_URL, referenceUrl)
				referenceIntent.putExtra(REFERENCE_TITLE, referenceTitle)
				startActivity(referenceIntent)
			} else {
				Toast.makeText(this@PlaceDetailActivity, "No reference URL.", Toast.LENGTH_LONG).show()
			}
		}
	}

	private fun getReferenceText(reference: Reference): String {
		val referenceBuilder = StringBuilder()
		referenceBuilder
			.append(reference.title)
			.append("\n")
			.append("Price: $")
			.append(reference.price)
			.append("\n")
			.append("URL: ")
			.append(reference.url)
		return referenceBuilder.toString()
	}

	private fun createFlexTagTextView(tag: String): TextView {
		val textView = TextView(this)
		textView.setBackgroundResource(R.drawable.tag_background)
		textView.text = tag
		textView.gravity = Gravity.CENTER
		textView.setPadding(tagPadding, tagPadding, tagPadding, tagPadding)
		return textView
	}

	private inner class Views internal constructor() {
		internal var ivPhoto: ImageView = findViewById(R.id.iv_detail_photo)
		internal var tvName: TextView = findViewById(R.id.tv_name)
		internal var tvNameSuffix: TextView = findViewById(R.id.tv_name_suffix)
		internal var tvPerex: TextView = findViewById(R.id.tv_perex)
		internal var tvDescription: TextView = findViewById(R.id.tv_description)
		internal var tvRating: TextView = findViewById(R.id.tv_rating)
		internal var tvAddress: TextView = findViewById(R.id.tv_address)
		internal var tvPhone: TextView = findViewById(R.id.tv_phone)
		internal var tvEmail: TextView = findViewById(R.id.tv_email)
		internal var tvAdmission: TextView = findViewById(R.id.tv_admission)
		internal var tvOpeningHours: TextView = findViewById(R.id.tv_opening_hours)
		internal var fblTags: FlexboxLayout = findViewById(R.id.fbl_tags)
		internal var llReferencesList: LinearLayout = findViewById(R.id.ll_references_list)
		internal var cbFavorite: CheckBox = findViewById(R.id.cb_favorite)
	}

	companion object {
		private val TAG = PlaceDetailActivity::class.java.simpleName

		val ID = "id"
		val REFERENCE_TITLE = "reference_title"
		val REFERENCE_URL = "reference_url"
	}
}
