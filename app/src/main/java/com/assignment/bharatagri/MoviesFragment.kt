package com.assignment.bharatagri

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.bharatagri.adapter.MovieAdapter
import com.assignment.bharatagri.model.Movie
import com.assignment.bharatagri.network.Error
import com.assignment.bharatagri.network.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MoviesFragment : Fragment() {

    private lateinit var moviesList: List<Movie>
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var activity: Activity
    private val model: MoviesViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.activity = context as AppCompatActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager: RecyclerView.LayoutManager
        view.rvMovies.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity)
        view.rvMovies.layoutManager = layoutManager
        moviesList = ArrayList()

        showList(view, "unsort")

    }

    private fun showList(
        view: View,
        sort: String
    ) {
        model.getImages(sort).observe(viewLifecycleOwner, Observer { movies ->

            Log.e("RESOURCE", " $movies")
            if (movies.data != null) {
                view.progressBar.visibility = View.GONE
                moviesList = movies.data

                movieAdapter = MovieAdapter(
                    activity,
                    moviesList
                )
                view.rvMovies.adapter = movieAdapter

                movieAdapter.onItemClick = { movie ->
                    Log.e("SSSSCCCCC", " $movie")
                    val fragmentManager = parentFragmentManager
                    val detailFragment = DetailFragment()
                    val fragmentTransaction =
                        fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.parentMovieFragment, detailFragment)
                    fragmentTransaction.addToBackStack("")
                    fragmentTransaction.commit()
                    model.sendMovie(movie)
                }

            } else {
                view.progressBar.visibility = View.GONE
                if (movies.message != null) {
                    Error(activity, movies.message).showError()
                }

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.rating -> {
                showList(requireView(), "rating")
                true
            }
            R.id.date -> {
                showList(requireView(), "date")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}