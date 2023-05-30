package com.example.aesencryption.routes

sealed class Screen(val route: String){
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object EnkripsiTextScreen : Screen("enkripsitext_screen")
    object HomeScreen : Screen("home_screen")
    object ListTextScreen : Screen("listtext_screen")
    object EnkripsiGambarScreen : Screen("enkripsigambar_screen")

}