package com.example.skincancer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class SkinCancerRecyclerViewAdapter (items: List<SkinCancerVO>, listener: ListSkinCancerFragment.OnListSkinCancerFragmentInteractionListener)
    : RecyclerView.Adapter<SkinCancerRecyclerViewAdapter.SkinCancerViewHolder>() {

    private var mValues: List<SkinCancerVO> = items
    private var mListener: ListSkinCancerFragment.OnListSkinCancerFragmentInteractionListener = listener
	
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkinCancerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewsearchskincancer_item, parent, false)
        return SkinCancerViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SkinCancerViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.searchSkinCancerIdView.text = " " + mValues[position].getId() + " "
        holder.searchSkinCancerDatesView.text = " " + mValues[position].getDates() + " "
        val dimage: Bitmap? = try {
            // convert base64 to bitmap android
            val decodedString: ByteArray = Base64.decode(mValues[position].getImages(), Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            decodedByte
        }
        catch (e: Exception) {
            e.message
            null
        }
        holder.searchSkinCancerImagesView.setImageBitmap(dimage)
        holder.searchSkinCancerOutcomeView.text = " " + mValues[position].getOutcome() + " "

        holder.mView.setOnClickListener { mListener.onListSkinCancerFragmentInteraction(holder.mItem) }
    }
    
    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class SkinCancerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mView: View
        var searchSkinCancerIdView: TextView
        var searchSkinCancerDatesView: TextView
        var searchSkinCancerImagesView: ImageView
        var searchSkinCancerOutcomeView: TextView
        lateinit var mItem: SkinCancerVO

        init {
            mView = view
            searchSkinCancerIdView = view.findViewById(R.id.searchSkinCancerIdView)
            searchSkinCancerDatesView = view.findViewById(R.id.searchSkinCancerDatesView)
            searchSkinCancerImagesView = view.findViewById(R.id.searchSkinCancerImagesView)
            searchSkinCancerOutcomeView = view.findViewById(R.id.searchSkinCancerOutcomeView)
        }

        override fun toString(): String {
            return super.toString() + " " + mItem
        }

    }
}
