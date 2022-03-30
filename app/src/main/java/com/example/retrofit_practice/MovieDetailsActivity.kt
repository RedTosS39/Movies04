package com.example.retrofit_practice


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val API_KEY: String = "c5822a12fa740c5cd4ed818c615d0aac"

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var banner: ImageView
    lateinit var releaseDate: TextView
    lateinit var score: TextView
    lateinit var overview: TextView
    lateinit var description: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        init() //Id's
        val id = intent.getIntExtra("id", 0)
        Log.d("id_intent", "onCreate: $id")


        val apiInterface = id.let {
            ApiInterface.create().getMovieDetails(it, API_KEY) //API KEY

        }

        apiInterface.enqueue(object : Callback<MovieDetails>,
            MovieAdapter.CustomAdapter.ItemClickListener {
            override fun onResponse(
                call: Call<MovieDetails>,
                response: Response<MovieDetails>
            ) {

                title.text = response?.body()?.title
                Log.d("id_intent", "onResponse: ${response?.body()?.title} ")

                releaseDate.text = response?.body()?.release_date
                score.text = response?.body()?.vote_average.toString()
                overview.text = response?.body()?.tagline
                description.text = response.body()?.overview.toString()

                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500" + response.body()?.backdrop_path)
                    .into(banner)

            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(position: Int) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun init() {
        title = findViewById(R.id.movie_details_title)
        banner = findViewById(R.id.movie_details_image_banner)
        releaseDate = findViewById(R.id.movie_details_date)
        score = findViewById(R.id.movie_details_score)
        overview = findViewById(R.id.movie_details_overview_label)
        description = findViewById(R.id.movie_details_description_label)
    }

}
