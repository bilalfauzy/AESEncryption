package com.example.aesencryption.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aesencryption.model.Texts
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TextViewModel: ViewModel() {
    private var db = Firebase.firestore

    private val _getTextsbyUser = MutableStateFlow<List<Texts>>(emptyList())
    val getTextsbyUser: StateFlow<List<Texts>> = _getTextsbyUser

    fun createTextEnkripsi(text: Texts, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("teks").document(text.idText!!)
                .set(text)
                .await()
        }
    }

    fun getTextEnkripsibyUser(emailUser: String){
        viewModelScope.launch(Dispatchers.IO){
            val snapshot = db.collection("teks")
                .whereEqualTo("emailUser", emailUser)
                .get()
                .await()

            val texts = snapshot.toObjects(Texts::class.java)
            _getTextsbyUser.value = texts
        }
    }

    fun deleteTextEnkripsi(idTexts: String, context: Context){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("teks").document(idTexts!!)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "Berhasil menghapus data enkripsi!", Toast.LENGTH_SHORT).show()
                }
                .await()
        }
    }
}