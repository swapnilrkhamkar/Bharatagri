package com.assignment.bharatagri.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.assignment.bharatagri.BuildConfig
import com.assignment.bharatagri.R
import com.assignment.bharatagri.model.Movie
import kotlinx.android.synthetic.main.adapter_movie.view.*

class MovieAdapter(val context: Context, var movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    lateinit var onItemClick: ((Movie) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(movies[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(movies[adapterPosition])
            }
        }

        fun setData(movie: Movie) {

            itemView.txtMovieName.text = movie.original_title
            itemView.txtMovieDesc.text = movie.overview
            itemView.imgMovie.load(BuildConfig.IMAGE_API + movie.poster_path) {
                crossfade(true)
                memoryCachePolicy(CachePolicy.ENABLED)
            }

        }

    }
}