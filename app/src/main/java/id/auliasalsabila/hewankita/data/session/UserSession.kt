package id.auliasalsabila.hewankita.data.session

import android.content.Context
import android.content.SharedPreferences
import id.auliasalsabila.hewankita.data.remote.response.LoginResult

class UserSession(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getUser(value: LoginResult) {
        val edit = preferences.edit()
        edit.putString(NAME, value.name)
        edit.putString(TOKEN, value.token)
        edit.putBoolean(LOGIN, value.isLogin)
        edit.apply()
    }

    fun isLogin(): Boolean {
        return preferences.getBoolean(LOGIN, false)
    }

    fun getToken(): LoginResult {
        val user = LoginResult()
        user.token = preferences.getString(TOKEN, "").toString()
        return user
    }

    companion object {
        private const val NAME = "name"
        private const val LOGIN = "isLogin"
        private const val TOKEN = "token"
        private const val PREFS = "prefs"
    }
}