package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Login(
    val email: String = "",
    val pass: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Login {
            val email = snapshot.child("e-mail").getValue(String::class.java) ?: ""
            val pass = snapshot.child("pass").getValue(String::class.java) ?: ""
            return Login(
                email = email,
                pass = pass
            )
        }
    }
}
