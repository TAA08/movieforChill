package com.example.movieforchill.data.net

import com.example.movieforchill.domain.models.actors.Credits
import com.example.movieforchill.domain.models.movie.MovieState
import com.example.movieforchill.domain.models.movie.MoviesModel
import com.example.movieforchill.domain.models.movie.PostMovie
import com.example.movieforchill.domain.models.movie.Result
import com.example.movieforchill.domain.models.session.LoginApprove
import com.example.movieforchill.domain.models.session.Session
import com.example.movieforchill.domain.models.session.Token
import com.example.movieforchill.domain.models.user.Avatar
import retrofit2.Response
import retrofit2.http.*

interface MoviesApiService { // интерфейс для создания гет запроса
    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<MoviesModel>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Response<Result>

    @GET("authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<Token>

    @POST("authentication/token/validate_with_login")
    suspend fun approveToken(
        @Query("api_key") apiKey: String = API_KEY,
        @Body loginApprove: LoginApprove
    ): Response<Token>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body token: Token
    ): Response<Session>

    @Headers(
        "Accept: application/json;charset=utf-8",
        "Content-type: application/json;charset=utf-8"
    )
    @POST("account/{account_id}/favorite")
    suspend fun addFavorite(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Body postMovie: PostMovie
    )

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorites(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<MoviesModel>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String = API_KEY,
        @Body sessionId: Session
    )

    @GET("movie/{movie_id}/account_states")
    suspend fun getMovieStates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID
    ): Response<MovieState>

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("session_id") session_id: String = SESSION_ID
    ): Response<Avatar>

    @GET("movie/{movie_id}/credits")
    suspend fun  getMovieCredits(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ) : Response<Credits>

    companion object {

        private var SESSION_ID = ""
        private var API_KEY = "2672c5e5006041431f2be6a17b6f7ceb"
        private var LANGUAGE = "ru"
        private var PAGE = 1
    }


}