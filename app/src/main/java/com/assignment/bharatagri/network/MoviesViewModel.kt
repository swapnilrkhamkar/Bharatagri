package com.assignment.bharatagri.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.bharatagri.BuildConfig
import com.assignment.bharatagri.client.APIClient
import com.assignment.bharatagri.model.Movie
import com.assignment.bharatagri.model.Result
import com.assignment.bharatagri.urlInterface.APIInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesViewModel : ViewModel() {
    private lateinit var sources: MutableLiveData<Resource<List<Movie>>>
    private lateinit var compositeDisposable: CompositeDisposable
    val movie = MutableLiveData<Movie>()

    fun getImages(s: String): LiveData<Resource<List<Movie>>> {
        sources = MutableLiveData()
        compositeDisposable = CompositeDisposable()
        loadSources(s)
        return sources
    }

    private fun loadSources(s: String) {
        val apiService = APIClient.getClient().create(APIInterface::class.java)
        val call = apiService.getMovies(BuildConfig.API_KEY, s)

        compositeDisposable.add(
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        )

    }

    private fun handleResponse(result: Result) {
        Log.e("JBN|SBH ", "res " + result)

        sources.postValue(Resource.success(result.results))
    }

    private fun handleError(error: Throwable) {
        Log.e("JBN|SBH ", " " + error)
        sources.postValue(Resource.error(error, null))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun sendMovie(movie: Movie) {
        this.movie.value = movie
    }
}

