package com.example.aesencryption

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aesencryption.customcomponent.CustomCardHome
import com.example.aesencryption.customcomponent.CustomDivider
import com.example.aesencryption.routes.Screen
import com.example.aesencryption.ui.theme.backColor
import com.example.aesencryption.ui.theme.baseColor
import com.example.aesencryption.viewmodel.UsersViewModel
import com.example.dentistreservation.view.customcomponent.CustomSpacer
import com.example.dentistreservation.view.customcomponent.MyButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(
    navController: NavHostController,
    usersViewModel: UsersViewModel
) {
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
    val auth = FirebaseAuth.getInstance()

    usersViewModel.getUserLogin(emailLogin!!)
    val userLogin by usersViewModel.userLogin.collectAsState(emptyList())
    val namaUser = userLogin.map {
        it.nama.toString()
    }.joinToString()

    val activity = (LocalContext.current as? Activity)
    BackHandler(
        enabled = true,
        onBack = {
            activity?.finish()
        }
    )

    Column() {
        TopAppBar(modifier = Modifier
            .background(MaterialTheme.colors.primary),
            title = {
                Text(text = "Home")
            }
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .padding(40.dp)
        ){

            //Selamat datang user
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(text = "Selamat datang..\n${namaUser}")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_24),
                    contentDescription = null,
                    tint = baseColor,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp)
                )
            }

            CustomSpacer()
            CustomDivider()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()
            CustomSpacer()

            //enkripsi teks
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.EnkripsiTextScreen.route)
                    },
                iconResId = R.drawable.ic_email_24,
                title = "ENKRIPSI TEKS"
            )

            CustomSpacer()
            CustomSpacer()
            //enkripsi gambar
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.EnkripsiGambarScreen.route)
                    },
                iconResId = R.drawable.ic_image_24,
                title = "ENKRIPSI GAMBAR"
            )

            CustomSpacer()
            CustomSpacer()
            //History enkripsi
            CustomCardHome(
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        navController.navigate(Screen.ListTextScreen.route)
                    },
                iconResId = R.drawable.ic_checklist_24,
                title = "LIST TEKS ENKRIPSI"
            )
            Spacer(modifier = Modifier.weight(1f))
            MyButton(
                onClick = {
                    with(sharedPref.edit()){
                        clear()
                        apply()
                    }
                    navController.navigate(Screen.LoginScreen.route)
                },
                text = "LOGOUT"
            )

        }
    }


}