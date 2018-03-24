package com.posks.taxikosovashqip.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.model.TaxiModel

class TaxiAdapter(context: Context, favorites: ArrayList<TaxiModel>)
    : ArrayAdapter<TaxiModel>(context, 0, favorites) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var localConvertView = convertView
        // Get the data item for this position
        val taxiModel = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view

        if (localConvertView == null) {
            localConvertView = LayoutInflater.from(context).inflate(
                    R.layout.item_taxi,
                    parent,
                    false
            )
        }

        // Lookup view for data population
        val taxiInfoTextView = localConvertView!!.findViewById(R.id.taxi_info_text_view) as TextView
        val callViberImageView = localConvertView.findViewById(R.id.call_viber_image_view) as ImageView
        val callPhoneImageView = localConvertView.findViewById(R.id.call_phone_image_view) as ImageView

        taxiInfoTextView.text = taxiModel.taxiName

        if (taxiModel.viberOption != null) {
            callViberImageView.visibility = VISIBLE
        } else {
            callViberImageView.visibility = GONE
        }

        // Return the completed view to render on screen
        return localConvertView
    }
}