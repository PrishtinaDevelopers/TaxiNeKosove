package com.posks.taxikosovashqip.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.posks.taxikosovashqip.Keys
import com.posks.taxikosovashqip.Keys.EXTRA_CITY_SELECTION
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.adapter.TaxiAdapter
import com.posks.taxikosovashqip.model.TaxiModel
import java.util.*

class TaxiDetailsActivity : AppCompatActivity() {
    private val taxiModelList: ArrayList<TaxiModel> = ArrayList()

    private lateinit var taxiListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_details)

        taxiListView = findViewById(R.id.taxi_list_view)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase
                .getReference(Keys.KEY_TAXIS_REFERENCE)
                .child(intent.getStringExtra(EXTRA_CITY_SELECTION))

        // Read from the database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val taxiModel = postSnapshot.getValue(TaxiModel::class.java)
                    taxiModelList.add(taxiModel!!)
                }
                setListAdapter(taxiModelList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //                Log.w(TAG, "Failed to read value.", error.toException());
            }
        })
    }

    private fun setListAdapter(taxiModelList: ArrayList<TaxiModel>) {
        val taxiAdapter = TaxiAdapter(applicationContext, taxiModelList)
        taxiListView.adapter = taxiAdapter
    }
}