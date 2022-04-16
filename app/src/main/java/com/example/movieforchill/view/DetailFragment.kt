package com.example.movieforchill.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.movieforchill.model.retrofit.api.RetrofitInstance
import com.example.movieforchill.databinding.DetailFragmentBinding
import com.example.movieforchill.model.Result
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailFragment : Fragment(), CoroutineScope {

    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var result: Result
    override val coroutineContext: CoroutineContext = Dispatchers.Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieDetails()

    }

    /*private fun onFavoriteClickListener() {

        binding.ivDetailIcon.setOnClickListener {
            Snackbar.make(
                binding.,
                "Добавить фильм${deletedItem.body} был удалён",
                Snackbar.LENGTH_LONG
            ).setAction("Не удалять", View.OnClickListener {
                adapter.list.add(position, deletedItem)
                adapter.notifyItemInserted(position)
            }).show()


            val sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
            if (sessionId.isNotEmpty()) {
                addFavorite(movieId, sessionId)
            }
        }
    }

    private fun addFavorite(movieId: Int, sessionId: String) {

        launch {

            val postMovie = PostMovie(media_id = movieId, favorite = true)
            apiService.addFavorite(
                session_id = sessionId,
                postMovie = postMovie
            )
        }
    }*/

    private fun getMovieDetails(){
        launch {
            result = args.result
            var response = RetrofitInstance.getPostApi().getMovieDetail(id = result.id)
            Picasso.get().load(IMAGE_URL + response.posterPath).into(binding.ivDetailIcon)
            binding.movieTitle.text = response.title
            binding.movieOverview.text = response.overview
            binding.dateRelease.text = response.releaseDate
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}