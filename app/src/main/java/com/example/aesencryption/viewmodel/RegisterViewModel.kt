package com.example.aesencryption.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.aesencryption.model.Users
import com.example.aesencryption.routes.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID

class RegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    val idUser = mutableStateOf(
        UUID.randomUUID().toString()
    )
    val nama = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    val error = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    fun onNameChange(nama: String){
        this.nama.value = nama
    }
    fun onEmailChange(email: String){
        this.email.value = email
    }
    fun onPasswordChange(password: String){
        this.password.value = password
    }
    fun onConfirmPasswordChange(confirmPassword: String){
        this.confirmPassword.value = confirmPassword
    }

    fun onRegisterClick(navController: NavHostController, context: Context){
        val idUser = idUser.value
        val nama = nama.value
        val email = email.value
        val password = password.value

        if (isFormValid()){
            isLoading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = Users(
                            idUser, nama, email, password
                        )
                        Firebase.firestore.collection("users")
                            .document(user.idUser!!)
                            .set(user)
                            .addOnSuccessListener {
                                isLoading.value = false
                                Toast.makeText(context, "Berhasil register!!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.LoginScreen.route)
                                clearForm()
                            }
                            .addOnFailureListener {
                                isLoading.value = false
                                error.value = "Gagal menambahkan data: ${it.message}"
                                Toast.makeText(context, error.value, Toast.LENGTH_SHORT).show()
                            }

                    }else{
                        error.value = it.exception?.message?: "Error"
                        Toast.makeText(context, error.value, Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(context, error.value, Toast.LENGTH_SHORT).show()
        }

    }

    private fun isFormValid() : Boolean {
        if (nama.value.isEmpty() || email.value.isEmpty() ||
            password.value.isEmpty() || confirmPassword.value.isEmpty()
        ){
            error.value = "Pastikan semua form terisi!"
            return false
        }else if(password.value != confirmPassword.value){
            error.value = "Password tidak sama!"
            return false
        }
        return true
    }

    private fun clearForm(){
        nama.value = ""
        email.value = ""
        password.value = ""
        confirmPassword.value = ""
        error.value = ""
    }
}
