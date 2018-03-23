package com.posks.taxikosovashqip.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.model.CityModel

import java.util.ArrayList

import android.R.layout.simple_spinner_item
import com.posks.taxikosovashqip.Keys.KEY_CITIES_REFERENCE

class MainFragment : Fragment() {
    private var citiesSpinner: Spinner? = null

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
                val cityModelList = ArrayList<CityModel>()
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

        citiesSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                //TODO continue to next activity with citySlug
                Toast.makeText(context, " $i", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                Toast.makeText(context, getString(R.string.choose_city), Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    private fun populateSpinner(cityModelList: List<CityModel>) {
        val adapter = ArrayAdapter(context, simple_spinner_item, getCityNameArray(cityModelList))
        adapter.setDropDownViewResource(R.layout.item_cities_spinner)
        citiesSpinner!!.adapter = adapter
    }

    private fun getCityNameArray(cityModelList: List<CityModel>): Array<String?> {
        val cityNameArray = arrayOfNulls<String>(cityModelList.size)
        for (i in cityModelList.indices) {
            cityNameArray[i] = if (i == 0) getString(R.string.choose_city) else cityModelList[i - 1].cityName
        }
        return cityNameArray
    }
}