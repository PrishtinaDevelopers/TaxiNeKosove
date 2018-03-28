package com.posks.taxikosovashqip.adapter

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.posks.taxikosovashqip.Keys
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
            callViberImageView.setOnClickListener({ callViber(taxiModel.viberOption!!) })
        } else {
            callViberImageView.visibility = GONE
        }

        callPhoneImageView.setOnClickListener({ showCallPhoneDialog(taxiModel) })

        taxiInfoTextView.setOnClickListener({ showTaxiInfoDialog(taxiModel) })
        // Return the completed view to render on screen
        return localConvertView
    }

    private fun callViber(viberNumber: String) {
        try {
            val uri = Uri.parse(Keys.callUriType + Uri.encode(viberNumber))
            val intent = Intent(Keys.viberIntent)
            intent.setClassName(Keys.viberPackageName, Keys.viberClassName)
            intent.data = uri
            context.startActivity(intent)
        } catch (e: Exception) {
            if (e::javaClass == ActivityNotFoundException::javaClass) {
                Toast.makeText(context, context.getString(R.string.viber_not_installed), Toast.LENGTH_SHORT).show()
            }
            e.printStackTrace()
        }
    }

    private fun callPhone(phoneNumber: String) {
        val localIntent = Intent(Keys.phoneCallIntent, Uri.parse(Keys.callUriType + phoneNumber))
        context.startActivity(localIntent)
    }

    private fun showCallPhoneDialog(taxiModel: TaxiModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_phone_call)
        dialog.window!!.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        dialog.show()

        if (taxiModel.numberIpko == null) {
            dialog.findViewById<ImageView>(R.id.dialog_call_ipko_image_view).visibility = View.GONE
        }

        if (taxiModel.numberVala == null) {
            dialog.findViewById<ImageView>(R.id.dialog_call_vala_image_view).visibility = View.GONE
        }

        dialog.findViewById<ImageView>(R.id.dialog_call_vala_image_view)
                .setOnClickListener({
                    callPhone(taxiModel.numberVala!!)
                })
        dialog.findViewById<ImageView>(R.id.dialog_call_ipko_image_view)
                .setOnClickListener({
                    callPhone(taxiModel.numberIpko!!)
                })
    }

    private fun showTaxiInfoDialog(taxiModel: TaxiModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_taxi_info)
        dialog.window!!.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        dialog.show()

        dialog.findViewById<TextView>(R.id.taxi_info_taxi_name).text = context.getString(
                R.string.taxi_info_taxi_name_placeholder,
                taxiModel.taxiName
        )
        dialog.findViewById<TextView>(R.id.taxi_info_taxi_location).text = context.getString(
                R.string.taxi_info_taxi_location_placeholder,
                taxiModel.cityName
        )
        dialog.findViewById<TextView>(R.id.taxi_info_taxi_number).text = context.getString(
                R.string.taxi_info_taxi_number_placeholder,
                taxiModel.numberVala,
                taxiModel.numberIpko
        )

        dialog.findViewById<Button>(R.id.taxi_info_call_button).setOnClickListener({
            showCallPhoneDialog(taxiModel)
            dialog.dismiss()
        })

        dialog.findViewById<Button>(R.id.taxi_info_cancel_button).setOnClickListener({
            dialog.dismiss()
        })
    }
}