package com.example.movieforchill.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieforchill.model.Session
import com.example.movieforchill.model.retrofit.api.RetrofitInstance

import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private var prefSettings: SharedPreferences = application.getSharedPreferences(
        APP_SETTINGS,
        Context.MODE_PRIVATE
    ) as SharedPreferences
    private var editor: SharedPreferences.Editor = prefSettings.edit()


    fun deleteSession() {

        viewModelScope.launch {
            SESSION_ID = getSessionId()
            try {
                RetrofitInstance.getPostApi().deleteSession(sessionId = Session(session_id = SESSION_ID))
            } catch (e: Exception) {
                editor.clear().commit()
            }
        }
    }
    private fun getSessionId(): String {
        var session = ""
        try {
            session =
                prefSettings.getString(SESSION_ID_KEY, "") as String
        } catch (e: Exception) {
        }
        return session
    }
    companion object {

        private var SESSION_ID = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }


}