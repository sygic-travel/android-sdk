package com.sygic.travel.sdkdemo.gallery

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.sygic.travel.sdk.StSDK
import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.utils.Utils

class GalleryActivity : AppCompatActivity() {

    private var rvGallery: RecyclerView? = null
    private var galleryAdapter: GalleryAdapter? = null
    private var gallery: List<Medium>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        initRecycler()
        val id = intent.getStringExtra(ID)

        // Load photos from API, using id
        StSDK.getInstance().getPlaceMedia(id, mediaCallback)
    }

    override fun onPause() {
        super.onPause()

        // Observables need to be unsubscribed, when the activity comes to background
        StSDK.getInstance().unsubscribeObservable()
    }

    private fun initRecycler() {
        rvGallery = findViewById(R.id.rv_gallery)
        rvGallery!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGallery!!.addItemDecoration(DividerDecoration(this, R.drawable.line_divider))
        galleryAdapter = GalleryAdapter(onPhotoClick, Utils.getDetailPhotoSize(this))
        rvGallery!!.adapter = galleryAdapter
    }

    // On a photo click listener. Opens new activity with the chosen photo.
    private val onPhotoClick: GalleryAdapter.ViewHolder.GalleryPhotoClick
        get() = object : GalleryAdapter.ViewHolder.GalleryPhotoClick {
            override fun onPhotoClick(position: Int, ivPhoto: ImageView) {
                val photoIntent = Intent(this@GalleryActivity, PhotoActivity::class.java)
                photoIntent.putExtra(THUMBNAIL_URL, gallery!![position].urlTemplate)
                startActivity(photoIntent)
            }
        }

    private // Update the adapter's data.
    val mediaCallback: Callback<List<Medium>?>
        get() = object : Callback<List<Medium>?>() {
            override fun onSuccess(data: List<Medium>?) {
                this@GalleryActivity.gallery = data
                galleryAdapter!!.setGallery(data!!)
                galleryAdapter!!.notifyDataSetChanged()
            }

            override fun onFailure(t: Throwable) {
                Log.d(TAG, "Media: onFailure")
                t.printStackTrace()
            }
        }

    companion object {
        private val TAG = GalleryActivity::class.java.simpleName

        val ID = "id"
        val THUMBNAIL_URL = "thumbnail_url"
    }
}
