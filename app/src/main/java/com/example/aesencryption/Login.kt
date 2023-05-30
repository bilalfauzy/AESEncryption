package com.example.aesencryption

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.aesencryption.routes.Screen
import com.example.aesencryption.ui.theme.backColor
import com.example.aesencryption.viewmodel.LoginViewModel
import com.example.aesencryption.viewmodel.UsersViewModel
import com.example.dentistreservation.view.customcomponent.CustomSpacer
import com.example.dentistreservation.view.customcomponent.CustomTextField
import com.example.dentistreservation.view.customcomponent.MyButton

@Composable
fun Login(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    usersViewModel: UsersViewModel
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    var isError = false

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
    val editPref = sharedPref.edit()
    val emailLogin = remember {
        mutableStateOf(sharedPref.getString("email", ""))
    }
    val passLogin = remember {
        mutableStateOf(sharedPref.getString("pass", ""))
    }

    usersViewModel.getUserEmailPass(email.value, password.value)
    val allUsers by usersViewModel.allUser.collectAsState(emptyList())
    val emailUsers = allUsers.map {
        it.email.toString()
    }.joinToString()
    val passUsers = allUsers.map {
        it.password.toString()
    }.joinToString()


    val activity = (LocalContext.current as? Activity)
    BackHandler(
        enabled = true,
        onBack = {
            activity?.finish()
        }
    )

    if (emailLogin.value!!.isEmpty() && passLogin.value!!.isEmpty()) {
        Column {
            TopAppBar(modifier = Modifier
                .background(MaterialTheme.colors.primary),
                title = {
                    Text(text = "Login")
                }
            )

            Box(
                modifier = Modifier
                    .background(backColor)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        text = "Login"
                    )
                    CustomSpacer()
                    CustomSpacer()
                    CustomSpacer()

                    CustomTextField(
                        value = if (emailLogin.value.equals("")) email.value else emailLogin.value!!,
                        onValueChange = {
                            if (emailLogin.value.equals("")) email.value =
                                it else emailLogin.value = it
                            isError = it.isEmpty()

                        },
                        label = "Masukkan email",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_email_24
                                ),
                                contentDescription = "Email",
                                tint = MaterialTheme.colors.primary
                            )
                        },
                        isError = isError
                    )

                    CustomTextField(
                        value = if (passLogin.value.equals("")) password.value else passLogin.value!!,
                        onValueChange = {
                            if (passLogin.value.equals("")) password.value =
                                it else passLogin.value = it
                            isError = it.isEmpty()

                        },
                        label = "Masukkan password",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_lock
                                ),
                                contentDescription = "Password",
                                tint = MaterialTheme.colors.primary
                            )
                        },
                        isError = isError
                    )

                    CustomSpacer()


                    MyButton(
                        onClick = {
                            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                with(editPref) {
                                    putString("email", email.value)
                                    putString("pass", password.value)
                                    apply()
                                }

                                emailLogin.value = email.value
                                passLogin.value = password.value

                                if (emailLogin.value!!.equals(emailUsers) &&
                                    passLogin.value!!.equals(passUsers)
                                ){
                                    loginViewModel.onLoginClick(
                                        navController,
                                        emailLogin.value!!,
                                        passLogin.value!!,
                                        context
                                    )
                                }else{
                                    Toast.makeText(context, "Email atau password salah!!",
                                        Toast.LENGTH_SHORT).show()
                                    with(sharedPref.edit()){
                                        clear()
                                        apply()
                                    }
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Form tidak boleh kosong!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        text = "LOGIN"
                    )

                    CustomSpacer()
                    Text(text = "Belum mempunyai akun?")
                    CustomSpacer()
                    MyButton(
                        onClick = {
                            navController.navigate(Screen.RegisterScreen.route)
                        },
                        text = "REGISTER"
                    )

                }
            }
        }
    }else if(emailLogin.value!!.isNotEmpty() && passLogin.value!!.isNotEmpty()){
        loginViewModel.onLoginClick(
            navController,
            emailLogin.value!!,
            passLogin.value!!,
            context
        )
    }

}