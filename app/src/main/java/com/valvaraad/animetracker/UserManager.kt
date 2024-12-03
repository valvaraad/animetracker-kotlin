package com.valvaraad.animetracker

import android.content.Context

class UserManager {
    companion object {
        fun getCurrentUser(context: Context): Int? {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("currentUserId", -1)
            return if (userId != -1) userId else null
        }

        fun saveCurrentUser(context: Context, userId: Int) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("currentUserId", userId)
            editor.apply()
        }

        fun removeCurrentUser(context: Context) {
            val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("currentUserId")
            editor.apply()
        }
    }

}