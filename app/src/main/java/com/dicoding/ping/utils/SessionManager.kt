package com.dicoding.ping.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object
    {
        //        for Register
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
        //        for Forgot Password
        private const val EMAIL_FORGOT_PASSW_KEY = "email_forgot_password"
        private const val OTP_FORGOT_PASSWORD_KEY = "otp_forgot_password"
        //        for Login
        private const val USERNAME_KEY = "username"
        private const val EMAil_USER = "email_user"
        private const val ROLE_USER = "role_user"
        private const val ADDRESS_USER = "address_user"
        private const val IS_LOGIN = "is_login"
        private const val USER_ID = "user_id"
        private const val CITY_NAME = "city_name"
        private const val FULL_ADDRESS = "full_address"
    }

    fun saveFullAddress(fullAddress: String) {
        val editor = prefs.edit()
        editor.putString(FULL_ADDRESS, fullAddress)
        editor.apply()
    }

    fun getFullAddress(): String? {
        return prefs.getString(FULL_ADDRESS, null)
    }

    fun saveCityName(cityName: String) {
        val editor = prefs.edit()
        editor.putString(CITY_NAME, cityName)
        editor.apply()
    }

    fun getCityName(): String? {
        return prefs.getString(CITY_NAME, null)
    }


    fun saveAddressUser(address: String) {
        val editor = prefs.edit()
        editor.putString(ADDRESS_USER, address)
        editor.apply()
    }

    fun getAddressUser(): String? {
        return prefs.getString(ADDRESS_USER, null)
    }

    fun saveUserId(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.apply()
    }

    fun saveIsLogin(isLogin: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }

    fun getIsLogin(): Boolean {
        return prefs.getBoolean(IS_LOGIN, false)
    }

    fun saveUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(USERNAME_KEY, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return prefs.getString(USERNAME_KEY, null)
    }


    fun saveRole(role: String) {
        val editor = prefs.edit()
        editor.putString(ROLE_USER, role)
        editor.apply()
    }

    fun getRole(): String? {
        return prefs.getString(ROLE_USER, null)
    }


    fun saveEmail(email: String) {
        val editor = prefs.edit()
        editor.putString(EMAIL_KEY, email)
        editor.apply()
    }

    fun getEmail(): String? {
        return prefs.getString(EMAIL_KEY, null)
    }

    fun saveEmailForgotPassword(email: String) {
        val editor = prefs.edit()
        editor.putString(EMAIL_FORGOT_PASSW_KEY, email)
        editor.apply()
    }

    fun getEmailForgotPassword(): String? {
        return prefs.getString(EMAIL_FORGOT_PASSW_KEY, null)
    }

    fun saveOtpForgotPassword(otp: String) {
        val editor = prefs.edit()
        editor.putString(OTP_FORGOT_PASSWORD_KEY, otp)
        editor.apply()
    }

    fun getOtpForgotPassword(): String? {
        return prefs.getString(OTP_FORGOT_PASSWORD_KEY, null)
    }

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN_KEY, null)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}