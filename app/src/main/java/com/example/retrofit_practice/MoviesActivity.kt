package com.example.retrofit_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

// getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.ic_android_black_24dp, "Item " + i))
        }


        //retrofit response
        val apiInterface =
            ApiInterface.create().getMovies("c5822a12fa740c5cd4ed818c615d0aac") //API KEY

        apiInterface.enqueue(object : Callback<Movies>,
            MovieAdapter.CustomAdapter.ItemClickListener {
            override fun onResponse(call: Call<Movies>?, response: Response<Movies>?) {
                Log.d("test2", "onResponse:  ${response?.body()?.results}")

                // This will pass the ArrayList to our Adapter
                val adapter = MovieAdapter.CustomAdapter(response?.body()?.results, this)

                // Setting the Adapter with the recyclerview
                recyclerview.adapter = adapter


                //         if(response?.body() != null)
                //   recyclerAdapter.setMovieListItems(response.body()!!)
            }

            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                Log.d("test2", "onFailure:  ${t?.message}")
            }

            override fun onItemClick(id: Int) {
                Toast.makeText(this@MoviesActivity, "Click $id", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MoviesActivity, MovieDetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })

    }


    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

}