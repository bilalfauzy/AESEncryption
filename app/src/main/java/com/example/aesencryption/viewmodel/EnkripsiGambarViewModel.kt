package com.example.aesencryption.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class EnkripsiGambarViewModel: ViewModel() {
    var encryptedImage by mutableStateOf<Bitmap?>(null)
    var selectedImage by mutableStateOf<Bitmap?>(null)
    var decryptedImage by mutableStateOf<Bitmap?>(null)
    var encryptionKey by mutableStateOf("")

    fun generateRandomKey(length: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-+={}[]:;',.<>?/_`~"
        val random = Random.Default

        val sb = StringBuilder(length)
        repeat(length) {
            val randomIndex = random.nextInt(characters.length)
            val randomChar = characters[randomIndex]
            sb.append(randomChar)
        }

        return sb.toString()
    }

    fun clearForm(){
        selectedImage = null
        encryptedImage = null
        decryptedImage = null
    }
}

//fun encryptImage(key: String) {
//    val selectedBitmap = selectedImage ?: return
//
//    viewModelScope.launch {
//        val encryptedBitmap = withContext(Dispatchers.Default) {
//            encryptBitmap(selectedBitmap, key)
//        }
//        encryptedImage = encryptedBitmap
//    }
//}
//
//fun decryptImage(key: String) {
//    val encryptedBitmap = encryptedImage ?: return
//
//    viewModelScope.launch {
//        val decryptedBitmap = withContext(Dispatchers.Default) {
//            decryptBitmap(encryptedBitmap, key)
//        }
//        decryptedImage = decryptedBitmap
//    }
//}

//private suspend fun encryptBitmap(bitmap: Bitmap, key: String): Bitmap? {
//    return withContext(Dispatchers.Default) {
//        val encryptedBytes = encryptDataImage(bitmap.toByteArray(), key.toByteArray())
//        encryptedBytes?.let {
//            BitmapFactory.decodeByteArray(it, 0, it.size)
//        }
//    }
//}
//
//
//private suspend fun decryptBitmap(bitmap: Bitmap, key: String): Bitmap? {
//    return withContext(Dispatchers.Default) {
//        val decryptedBytes = decryptDataImage(bitmap.toByteArray(), key.toByteArray())
//        decryptedBytes?.let {
//            BitmapFactory.decodeByteArray(it, 0, it.size)
//        }
//    }
//}
//
//private fun encryptDataImage(data: ByteArray, key: ByteArray): ByteArray? {
//    return try {
//        val secretKeySpec = SecretKeySpec(key, "AES")
//        val cipher = Cipher.getInstance("AES")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
//        cipher.doFinal(data)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//
//private fun decryptDataImage(data: ByteArray, key: ByteArray): ByteArray? {
//    return try {
//        val secretKeySpec = SecretKeySpec(key, "AES")
//        val cipher = Cipher.getInstance("AES")
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
//        cipher.doFinal(data)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//}
//
//private fun Bitmap.toByteArray(): ByteArray {
//    val stream = ByteArrayOutputStream()
//    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
//    return stream.toByteArray()
//}