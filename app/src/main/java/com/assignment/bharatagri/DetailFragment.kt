package com.assignment.bharatagri

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import coil.request.CachePolicy
import com.assignment.bharatagri.network.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private val model: MoviesViewModel by activityViewModels()
    private lateinit var activity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as Activity;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.movie.observe(viewLifecycleOwner, Observer { movie ->
            Log.e("DEatils", " $movie")
            imgMovieDetails.load(BuildConfig.IMAGE_API + movie.poster_path) {
                crossfade(true)
                memoryCachePolicy(CachePolicy.ENABLED)
            }
            txtDescDetails.text = movie.overview
            txtMovieNameDetails.text = movie.original_title
        })

        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
    }
}