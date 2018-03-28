package com.posks.taxikosovashqip.ui.fragment

import android.R.layout.simple_spinner_item
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.posks.taxikosovashqip.Keys.EXTRA_CITY_SELECTION
import com.posks.taxikosovashqip.Keys.KEY_CITIES_REFERENCE
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.model.CityModel
import com.posks.taxikosovashqip.ui.activity.TaxiDetailsActivity
import java.util.*

class MainFragment : Fragment() {
    private var citiesSpinner: Spinner? = null
    private val cityModelList: ArrayList<CityModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        citiesSpinner = rootView.findViewById(R.id.cities_spinner)

        // Write a message to the database
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference(KEY_CITIES_REFERENCE)

        // Read from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (postSnapshot in dataSnapshot.children) {
                    val cityModel = postSnapshot.getValue(CityModel::class.java)
                    cityModelList.add(cityModel!!)
                }

                populateSpinner(cityModelList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //                Log.w(TAG, "Failed to read value.", error.toException());
            }
        })

        rootView.findViewById<ImageView>(R.id.search_cities_image_view).setOnClickListener({
            if (cityModelList.size > 0 && citiesSpinner!!.selectedItemPosition > 0) {
                val intent = Intent(context, TaxiDetailsActivity::class.java)
                intent.putExtra(
                        EXTRA_CITY_SELECTION,
                        cityModelList[citiesSpinner!!.selectedItemPosition - 1].citySlug
                )
                startActivity(intent)
            }
        })
        return rootView
    }

    private fun populateSpinner(cityModelList: List<CityModel>) {
        val adapter = ArrayAdapter(context, simple_spinner_item, getCityNameArray(cityModelList))
        adapter.setDropDownViewResource(R.layout.item_cities_spinner)
        citiesSpinner!!.adapter = adapter
    }

    private fun getCityNameArray(cityModelList: List<CityModel>): Array<String?> {
        val cityNameArray = arrayOfNulls<String>(cityModelList.size + 1)
        val maxCount = cityModelList.size + 1
        var i = 0
        while (i < maxCount) {
            cityNameArray[i] = if (i == 0) getString(R.string.choose_city) else cityModelList[i - 1].cityName
            i++
        }
        return cityNameArray
    }
}