package com.example.aesencryption.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class EnkripsiTextViewModel : ViewModel() {
    var originalText by mutableStateOf("")
    var encryptedText by mutableStateOf("")
    var decryptedText by mutableStateOf("")
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

    fun encryptText() {
        val key = encryptionKey.toByteArray(StandardCharsets.UTF_8)
        val encryptedBytes = encryptData(originalText.toByteArray(StandardCharsets.UTF_8), key)
        encryptedText = encryptedBytes?.toHexString() ?: ""
    }

    fun decryptText() {
        val key = encryptionKey.toByteArray(StandardCharsets.UTF_8)
        val encryptedBytes = encryptedText.hexStringToByteArray()
        val decryptedBytes = decryptData(encryptedBytes!!, key)
        decryptedText = decryptedBytes?.toString(StandardCharsets.UTF_8) ?: ""
    }

    fun clearForm(){
        originalText = ""
        encryptedText = ""
        decryptedText = ""
        encryptionKey = ""
    }

}

fun ByteArray.toHexString(): String {
    val hexChars = "0123456789ABCDEF".toCharArray()
    val result = StringBuilder()
    for (byte in this) {
        val high = byte.toInt() ushr 4 and 0x0F
        val low = byte.toInt() and 0x0F
        result.append(hexChars[high])
        result.append(hexChars[low])
    }
    return result.toString()
}

fun String.hexStringToByteArray(): ByteArray? {
    val len = length
    if (len % 2 != 0) {
        return null
    }
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        val hex = substring(i, i + 2)
        val byteValue = hex.toIntOrNull(16) ?: return null
        data[i / 2] = byteValue.toByte()
        i += 2
    }
    return data
}

fun encryptData(data: ByteArray, key: ByteArray): ByteArray? {
    try {
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        val secretKey = SecretKeySpec(key, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(data)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun decryptData(data: ByteArray, key: ByteArray): ByteArray? {
    try {
        val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
        val secretKey = SecretKeySpec(key, "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(data)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
