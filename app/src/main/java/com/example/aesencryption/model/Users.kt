package com.example.aesencryption.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    var idUser: String? = null,
    var nama: String? = null,
    var email: String? = null,
    var password: String? = null
)
