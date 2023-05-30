package com.example.aesencryption

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.aesencryption.viewmodel.RegisterViewModel
import com.example.dentistreservation.view.customcomponent.CustomSpacer
import com.example.dentistreservation.view.customcomponent.CustomTextField
import com.example.dentistreservation.view.customcomponent.MyAppBar
import com.example.dentistreservation.view.customcomponent.MyButton

@Composable
fun Register(
    navController: NavHostController,
    registerViewModel: RegisterViewModel
){
    var isError = false
    val context = LocalContext.current

    var errorText = remember {
        mutableStateOf("")
    }

    Column() {

        MyAppBar(
            title = "Register",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.LoginScreen.route, false)
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
            ){
                Text(
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    text = "Register"
                )
                CustomSpacer()
                CustomSpacer()
                CustomSpacer()
                //nama
                CustomTextField(
                    value = registerViewModel.nama.value,
                    onValueChange = {
                        registerViewModel.onNameChange(it)
                    },
                    label = "Masukkan nama",
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_person_24),
                            contentDescription = "Nama",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //email
                CustomTextField(
                    value = registerViewModel.email.value,
                    onValueChange = {
                        registerViewModel.onEmailChange(it)
                    },
                    label = "Masukkan email",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_email_24),
                            contentDescription = "Email",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //password
                CustomTextField(
                    value = registerViewModel.password.value,
                    onValueChange = {
                        registerViewModel.onPasswordChange(it)
                    },
                    label = "Masukkan password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_lock),
                            contentDescription = "Password",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                //konfirmasi password
                CustomTextField(
                    value = registerViewModel.confirmPassword.value,
                    onValueChange = {
                        registerViewModel.onConfirmPasswordChange(it)
                    },
                    label = "Konfirmasi password",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.ic_lock),
                            contentDescription = "Password",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError
                )

                CustomSpacer()
                CustomSpacer()
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    onClick = {
                        registerViewModel.onRegisterClick(navController, context)
                    }) {
                    if (registerViewModel.isLoading.value){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(4.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }else{
                        Text(text = "REGISTER")
                    }
                }
                CustomSpacer()
                Text(text = "Sudah mempunyai akun?")
                CustomSpacer()
                MyButton(
                    onClick = {
                        navController.popBackStack(Screen.LoginScreen.route, false)
                    },
                    text = "LOGIN"
                )
            }
        }

    }
}