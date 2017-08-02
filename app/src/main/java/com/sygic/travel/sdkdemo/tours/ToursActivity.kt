package com.sygic.travel.sdkdemo.tours

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.sygic.travel.sdk.StSDK
import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.ToursQuery
import com.sygic.travel.sdkdemo.R
import com.sygic.travel.sdkdemo.detail.PlaceDetailActivity
import com.sygic.travel.sdkdemo.detail.ReferenceActivity

class ToursActivity : AppCompatActivity(), ToursAdapter.ListItemClickListener {

    private var rvTours: RecyclerView? = null
    private var toursAdapter: ToursAdapter? = null
    private var tourCallback: Callback<List<Tour>?>? = null
    private var tours: List<Tour>? = null


    override fun onListItemClick(clickedItemIndex: Int) {
        val referenceIntent = Intent(this, ReferenceActivity::class.java)
        val clickedTour = tours?.get(clickedItemIndex)

        if (clickedTour?.url != null && clickedTour.url != "") {
            referenceIntent.putExtra(PlaceDetailActivity.REFERENCE_URL, clickedTour.url)
            referenceIntent.putExtra(PlaceDetailActivity.REFERENCE_TITLE, clickedTour.title)
        } else Toast.makeText(this, "No reference URL.", Toast.LENGTH_LONG).show()

        startActivity(referenceIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tours_list)

        initRecycler()
        loadTours()
    }

    private fun loadTours() {
        val tourQuery = ToursQuery(
                destinationId = "city:1",
                page = 1,
                sortBy = ToursQuery.SortBy.RATING,
                sortDirection = ToursQuery.SortDirection.ASC
        )
        StSDK.getInstance().getTours(tourQuery, getToursCallback())
    }

    private fun getToursCallback(): Callback<List<Tour>?>? {
        if (tourCallback == null) {
            tourCallback = object : Callback<List<Tour>?>() {
                override fun onSuccess(data: List<Tour>?) {
                    if (data != null) {
                        tours = data
                        bindTours()
                    }
                }

                override fun onFailure(t: Throwable) {
                    Toast.makeText(this@ToursActivity, t.message, Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }
            }
        }
        return tourCallback
    }

    // Recycler view initialization - list with dividers
    private fun initRecycler() {
        rvTours = findViewById(R.id.rv_tours)
        rvTours!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvTours!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        toursAdapter = ToursAdapter(this)
        rvTours!!.adapter = toursAdapter
    }

    private fun bindTours() {
        toursAdapter?.setTours(tours)
        toursAdapter?.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        // Observables need to be unsubscribed, when the activity comes to background
        StSDK.getInstance().unsubscribeObservable()
    }
}

