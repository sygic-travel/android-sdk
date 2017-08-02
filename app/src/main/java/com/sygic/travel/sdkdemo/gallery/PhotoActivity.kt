package com.sygic.travel.sdkdemo.gallery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.utils.Utils

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        loadPhoto()
    }

    // Show a single photo
    private fun loadPhoto() {
        val photoUrl: String
        val photoUrlTemplate = intent.getStringExtra(THUMBNAIL_URL)
        if (photoUrlTemplate != null && photoUrlTemplate != "") {
            photoUrl = photoUrlTemplate.replace(Utils.PHOTO_SIZE_PLACEHOLDER, Utils.getDetailPhotoSize(this))
        } else {
            photoUrl = "no-url"
        }

        Picasso
                .with(this)
                .load(photoUrl)
                .placeholder(R.drawable.ic_photo_camera)
                .into(findViewById<ImageView>(R.id.iv_gallery_photo))
    }

    companion object {
        val THUMBNAIL_URL = "thumbnail_url"
    }
}
