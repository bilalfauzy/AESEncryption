package com.example.aesencryption.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aesencryption.model.Users
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel : ViewModel() {
    private var db = Firebase.firestore

    private val _userLogin = MutableStateFlow<List<Users>>(emptyList())
    val userLogin: StateFlow<List<Users>> = _userLogin

    private val _allUser = MutableStateFlow<List<Users>>(emptyList())
    val allUser: StateFlow<List<Users>> = _allUser

    fun getUserLogin(emailUser: String) {

        if (emailUser.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val snapshot = db.collection("users")
                        .whereEqualTo("email", emailUser)
                        .get()
                        .await()
                    val user = snapshot.toObjects<Users>()
                    _userLogin.value = user
                } catch (e: Exception) {
                    Log.e("UserLogin", "Gagal mengambil user login", e)
                }
            }
        }

    }
    fun getUserEmailPass(emailUser: String, passUser: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("users")
                    .whereEqualTo("email", emailUser)
                    .whereEqualTo("password", passUser)
                    .get().await()
                val users = snapshot.toObjects(Users::class.java)
                _allUser.value = users
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error getting users", e)
            }

        }
    }
}