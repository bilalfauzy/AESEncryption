package com.example.aesencryption.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aesencryption.*
import com.example.aesencryption.viewmodel.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(
            route = Screen.LoginScreen.route
        ){
            Login(
                navController = navController,
                loginViewModel = LoginViewModel(context),
                usersViewModel = UsersViewModel()
            )
        }
        composable(
            route = Screen.RegisterScreen.route
        ){
            Register(navController = navController, registerViewModel = RegisterViewModel())
        }
        composable(
            route = Screen.EnkripsiTextScreen.route
        ){
            EnkripsiText(
                navController = navController ,
                enkripsiTextViewModel = EnkripsiTextViewModel(),
                textViewModel = TextViewModel()
            )
        }
        composable(
            route = Screen.HomeScreen.route
        ){
            Home(navController = navController, usersViewModel = UsersViewModel())
        }
        composable(
            route = Screen.ListTextScreen.route
        ){
            ListTexts(navController = navController, textViewModel = TextViewModel())
        }
        composable(
            route = Screen.EnkripsiGambarScreen.route
        ){
            EnkripsiGambar(
                navController = navController,
                enkripsiGambarViewModel = EnkripsiGambarViewModel()
            )
        }
    }
}
