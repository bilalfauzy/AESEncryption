package com.example.aesencryption.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Texts (
    var idText: String? = null,
    var emailUser: String? = null,
    var originalText: String? = null,
    var enkripsi: String? = null,
    var enkripsiKey: String? = null,
    var jenisEnkripsi: String? = null
)