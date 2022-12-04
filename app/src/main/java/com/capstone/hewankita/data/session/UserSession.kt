package com.capstone.hewankita.data.session

import android.content.Context
import android.content.SharedPreferences
import com.capstone.hewankita.data.remote.response.LoginResult

class UserSession(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    companion object {
        private const val NAME = "name"
        private const val LOGIN = "isLogin"
        private const val TOKEN = "token"
        private const val PREFS = "prefs"
    }
}