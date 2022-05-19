package com.example.movieforchill.model.room.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.movieforchill.model.LoginApprove
import com.example.movieforchill.model.Session
import com.example.movieforchill.model.Token
import com.example.movieforchill.model.retrofit.api.RetrofitInstance

class LoginRepository(application: Application) {

    private var prefSettings: SharedPreferences = application.getSharedPreferences(
        APP_SETTINGS,
        Context.MODE_PRIVATE
    ) as SharedPreferences
    private var editor: SharedPreferences.Editor = prefSettings.edit()
    private val workWithApi = RetrofitInstance.getPostApi()
    val context = application

    private fun getSessionId(): String {
        var session = ""
        try {
            session =
                prefSettings.getString(SESSION_ID_KEY, "") as String
        } catch (e: Exception) {
        }
        return session
    }

    suspend fun deleteSession() {
        SESSION_ID = getSessionId()
        try {
            workWithApi
                .deleteSession(sessionId = Session(session_id = SESSION_ID))
        } catch (e: Exception) {
            editor.clear().commit()
        }
    }

    suspend fun login(username: String, password: String): String {
        var session = ""
        try {
            val responseGet = workWithApi.getToken()
            if (responseGet.isSuccessful) {
                val loginApprove = LoginApprove(
                    username = username,
                    password = password,
                    request_token = responseGet.body()?.request_token as String
                )
                val responseApprove = workWithApi.approveToken(
                    loginApprove = loginApprove
                )
                if (responseApprove.isSuccessful) {
                    val response =
                        workWithApi.createSession(token = responseApprove.body() as Token)
                    if (response.isSuccessful) {
                        session = response.body()?.session_id as String
                    }
                }
            } else {
                Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
            }
        } catch (e: java.lang.Exception) {
            Toast.makeText(context, "Нет подключение к интернету", Toast.LENGTH_SHORT).show()
        }

        return session
    }

    companion object {

        private var SESSION_ID = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }
}