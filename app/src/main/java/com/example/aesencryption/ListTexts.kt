package com.example.aesencryption

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aesencryption.customcomponent.CustomDivider
import com.example.aesencryption.model.Texts
import com.example.aesencryption.routes.Screen
import com.example.aesencryption.ui.theme.baseColor
import com.example.aesencryption.ui.theme.errorColor
import com.example.aesencryption.viewmodel.TextViewModel
import com.example.dentistreservation.view.customcomponent.IconButton
import com.example.dentistreservation.view.customcomponent.MyAppBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ListTexts(
    navController: NavHostController,
    textViewModel: TextViewModel
) {
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    textViewModel.getTextEnkripsibyUser(emailLogin!!)
    val listTeks by textViewModel.getTextsbyUser.collectAsState(emptyList())
    val context = LocalContext.current

    Column() {
        MyAppBar(
            title = "List teks enkripsi",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )
        MyListTeks(
            listTeks,
            textViewModel,
            context,
            navController,
            onItemClick = {
            }
        )
    }
}

@Composable
fun MyListTeks(
    items: List<Texts>,
    textViewModel: TextViewModel,
    context: Context,
    navController: NavHostController,
    onItemClick: (Texts) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items){teks ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onItemClick(teks) }),
                elevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(){
                        Text(
                            fontWeight = FontWeight.Medium,
                            text = "Teks original :"
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "${teks.originalText}"
                        )
                        Text(
                            fontWeight = FontWeight.Medium,
                            text = "Enkripsi :"
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "${teks.enkripsi}"
                        )
                        Text(
                            fontWeight = FontWeight.Medium,
                            text = "Key :"
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "${teks.enkripsiKey}"
                        )
                        Text(
                            fontWeight = FontWeight.Medium,
                            text = "Jenis AES :"
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = "${teks.jenisEnkripsi}"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.clickable {
                            textViewModel.deleteTextEnkripsi(teks.idText!!, context)
                            navController.popBackStack(Screen.HomeScreen.route, true)
                            navController.navigate(Screen.ListTextScreen.route)
                        },
                        iconResId = com.example.aesencryption.R.drawable.ic_delete_24,
                        description = "hapus",
                        color = errorColor,
                        text = "Hapus"
                    )
                }

            }
            CustomDivider()
        }
    }
}