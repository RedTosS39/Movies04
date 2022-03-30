package com.example.retrofit_practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter {
    class CustomAdapter(
        private val mList: List<Result>?, val mItemClickListener: ItemClickListener
    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        interface ItemClickListener {
            fun onItemClick(position: Int)
        }

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_design, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            // val itemsViewModel = mList[position]
            val itemsViewModel = mList?.get(position)

            Picasso.get().load("https://image.tmdb.org/t/p/w500" + itemsViewModel?.poster_path)
                .into(holder.imageView)

            // sets the image to the imageview from our itemHolder class


        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList!!.size
        }

        // Holds the views for adding it to image and text
        inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val imageView: ImageView = itemView.findViewById(R.id.card_view_imageview)

            init {
                ItemView.setOnClickListener {
                    mList?.get(layoutPosition)?.id?.let {
                        mItemClickListener.onItemClick(it)
                    }
                }
            }
        }
    }
}