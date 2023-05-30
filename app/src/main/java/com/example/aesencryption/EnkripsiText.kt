package com.example.aesencryption

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aesencryption.customcomponent.CustomDivider
import com.example.aesencryption.customcomponent.MaxTextField
import com.example.aesencryption.customcomponent.OpenDocument
import com.example.aesencryption.model.Texts
import com.example.aesencryption.routes.Screen
import com.example.aesencryption.ui.theme.backColor
import com.example.aesencryption.ui.theme.baseColor
import com.example.aesencryption.viewmodel.EnkripsiTextViewModel
import com.example.aesencryption.viewmodel.TextViewModel
import com.example.aesencryption.viewmodel.toHexString
import com.example.dentistreservation.view.customcomponent.*
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

@Composable
fun EnkripsiText(
    navController: NavHostController,
    enkripsiTextViewModel: EnkripsiTextViewModel,
    textViewModel: TextViewModel
) {
    var isError = false
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE)
    val auth = FirebaseAuth.getInstance()
    val jenisEnkripsi = remember {
        mutableStateOf("")
    }
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val randomId = UUID.randomUUID().toString()
    var selectedDocumentUri = remember { mutableStateOf<Uri?>(null) }


    Column() {
        MyAppBar(
            title = "Enkripsi teks",
            navigationIcon = Icons.Filled.ArrowBack,
            onNavigationClick = {
                navController.popBackStack(Screen.HomeScreen.route, false)
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backColor)
                .verticalScroll(scrollState)
        ){
            Column(
                Modifier
                    .padding(40.dp)
                    .fillMaxSize()
            ) {

                MaxTextField(
                    value = enkripsiTextViewModel.originalText,
                    onValueChange = {
                        enkripsiTextViewModel.originalText = it
                        isError = it.isEmpty()
                    },
                    label = "Original teks",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email_24),
                            contentDescription = "original teks",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError,
                    readOnly = false
                )

                MaxTextField(
                    value = enkripsiTextViewModel.encryptionKey,
                    onValueChange = {
                        enkripsiTextViewModel.encryptionKey = it
                        isError = it.isEmpty()
                    },
                    label = "Key enkripsi",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_key_24),
                            contentDescription = "key enkripsi",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError,
                    readOnly = false
                )

                CustomSpacer()
                Text(
                    text = "Generate random key :",
                    modifier = Modifier.fillMaxWidth()
                )
                CustomSpacer()
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    IconButton(
                        modifier = Modifier.clickable {
                            val randomKey16 = enkripsiTextViewModel.generateRandomKey(16)
                            enkripsiTextViewModel.encryptionKey = randomKey16
                            jenisEnkripsi.value = "AES-128"
                        },
                        iconResId = R.drawable.ic_key_24,
                        description = "16 byte",
                        color = baseColor,
                        text = "16 byte"
                    )
                    IconButton(
                        modifier = Modifier.clickable {
                            val randomKey24 = enkripsiTextViewModel.generateRandomKey(24)
                            enkripsiTextViewModel.encryptionKey = randomKey24
                            jenisEnkripsi.value = "AES-192"

                        },
                        iconResId = R.drawable.ic_key_24,
                        description = "24 byte",
                        color = baseColor,
                        text = "24 byte"
                    )
                    IconButton(
                        modifier = Modifier.clickable {
                            val randomKey32 = enkripsiTextViewModel.generateRandomKey(32)
                            enkripsiTextViewModel.encryptionKey = randomKey32
                            jenisEnkripsi.value = "AES-256"
                        },
                        iconResId = R.drawable.ic_key_24,
                        description = "32 byte",
                        color = baseColor,
                        text = "32 byte"
                    )
                }

                CustomSpacer()
                CustomSpacer()
                MyButton(
                    onClick = {
                        enkripsiTextViewModel.encryptText()
                        val enkripTeks = Texts(
                            randomId,
                            emailLogin.toString(),
                            enkripsiTextViewModel.originalText,
                            enkripsiTextViewModel.encryptedText,
                            enkripsiTextViewModel.encryptionKey,
                            jenisEnkripsi.value
                        )

                        if (randomId.isEmpty() || emailLogin.toString().isEmpty() || enkripsiTextViewModel.originalText.isEmpty() ||
                                enkripsiTextViewModel.encryptedText.isEmpty() ||
                                enkripsiTextViewModel.encryptionKey.isEmpty() ||
                                jenisEnkripsi.value.isEmpty()){
                            Toast.makeText(context, "Pastikan data terisi!!", Toast.LENGTH_SHORT).show()
                        }else{
                            textViewModel.createTextEnkripsi(enkripTeks, context)
                        }
                    },
                    text = "ENCRYPT"
                )

                CustomSpacer()
                CustomSpacer()
                CustomSpacer()
                CustomSpacer()
                CustomSpacer()
                CustomSpacer()
                MaxTextField(
                    value = enkripsiTextViewModel.encryptedText,
                    onValueChange = {
                        enkripsiTextViewModel.encryptedText = it
                        isError = it.isEmpty()
                    },
                    label = "Teks terenkripsi",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email_24),
                            contentDescription = "teks terenkripsi",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError,
                    readOnly = false
                )

                CustomSpacer()
                CustomSpacer()
                MyButton(
                    onClick = {
                        enkripsiTextViewModel.decryptText()
                    },
                    text = "DECRYPT"
                )

                CustomSpacer()
                CustomSpacer()
                MaxTextField(
                    value = enkripsiTextViewModel.decryptedText,
                    onValueChange = {
                        enkripsiTextViewModel.decryptedText = it
                        isError = it.isEmpty()
                    },
                    label = "Hasil dekripsi",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_email_24),
                            contentDescription = "hasil dekripsi",
                            tint = MaterialTheme.colors.primary
                        )
                    },
                    isError = isError,
                    readOnly = true
                )

                CustomSpacer()
                CustomSpacer()
                Spacer(modifier = Modifier.weight(1f))
                MyButton(
                    onClick = {
                        enkripsiTextViewModel.clearForm()
                    },
                    text = "CLEAR"
                )

            }
        }

    }

}

//                OpenDocument(icon = Icons.Filled.KeyboardArrowDown, label = "Open file", onDocumentSelected = {
//                    selectedDocumentUri.value = it
//                })
//                selectedDocumentUri.let { uri ->
//                    Text(
//                        text = "Selected Document URI:",
//                        style = MaterialTheme.typography.body1
//                    )
//                    Text(
//                        text = uri.toString(),
//                        style = MaterialTheme.typography.body2
//                    )
//                }