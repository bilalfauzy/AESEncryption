package com.example.aesencryption.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Gambar(
    var idGambar: String? = null,
    var emailUser: String? = null,
    var gambarOriginal: String? = null,
    var gambarEnkripsi: String? = null,
    var enkripsiKey: String? = null,
    var jenisEnkripsi: String? = null
)
