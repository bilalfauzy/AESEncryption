package com.example.aesencryption

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.aesencryption.customcomponent.MaxTextField
import com.example.aesencryption.routes.Screen
import com.example.aesencryption.ui.theme.backColor
import com.example.aesencryption.ui.theme.baseColor
import com.example.aesencryption.viewmodel.EnkripsiGambarViewModel
import com.example.aesencryption.viewmodel.EnkripsiTextViewModel
import com.example.dentistreservation.view.customcomponent.CustomSpacer
import com.example.dentistreservation.view.customcomponent.IconButton
import com.example.dentistreservation.view.customcomponent.MyAppBar
import com.example.dentistreservation.view.customcomponent.MyButton
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun EnkripsiGambar(
    navController: NavHostController,
    enkripsiGambarViewModel: EnkripsiGambarViewModel
) {

    var selectedImage = enkripsiGambarViewModel.selectedImage
    var encryptedImageBitmap = enkripsiGambarViewModel.encryptedImage
    var decryptedImageBitmap = enkripsiGambarViewModel.decryptedImage
    var enkripsiKey = enkripsiGambarViewModel.encryptionKey

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val scrollState = rememberScrollState()
    var isError = false
    val jenisEnkripsi = remember {
        mutableStateOf("")
    }
    val emailLogin = FirebaseAuth.getInstance().currentUser?.email
    val randomId = UUID.randomUUID().toString()

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column {
        MyAppBar(
            title = "Enkripsi gambar",
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
        ) {
            Column(
                Modifier
                    .padding(40.dp)
                    .fillMaxSize()
            ) {

                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        selectedImage = MediaStore.Images
                            .Media.getBitmap(context.contentResolver,it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver,it)
                        selectedImage = ImageDecoder.decodeBitmap(source)
                    }

                    selectedImage?.let {  btm ->
                        Image(bitmap = btm.asImageBitmap(),
                            contentDescription =null,
                            modifier = Modifier.size(300.dp))
                    }
                }

                CustomSpacer()
                MyButton(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    text = "PICK IMAGE"
                )
                CustomSpacer()
                MaxTextField(
                    value = enkripsiKey,
                    onValueChange = {
                        enkripsiKey = it
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
                            val randomKey16 = enkripsiGambarViewModel.generateRandomKey(16)
                            enkripsiKey = randomKey16
                            jenisEnkripsi.value = "AES-128"
                        },
                        iconResId = R.drawable.ic_key_24,
                        description = "16 byte",
                        color = baseColor,
                        text = "16 byte"
                    )
                    IconButton(
                        modifier = Modifier.clickable {
                            val randomKey24 = enkripsiGambarViewModel.generateRandomKey(24)
                            enkripsiKey = randomKey24
                            jenisEnkripsi.value = "AES-192"

                        },
                        iconResId = R.drawable.ic_key_24,
                        description = "24 byte",
                        color = baseColor,
                        text = "24 byte"
                    )
                    IconButton(
                        modifier = Modifier.clickable {
                            val randomKey32 = enkripsiGambarViewModel.generateRandomKey(32)
                            enkripsiKey = randomKey32
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
                        if (selectedImage != null){
                            enkripsiGambarViewModel.encryptImage(enkripsiKey)
                        }
                    },
                    text = "ENCRYPT"
                )
                CustomSpacer()
                encryptedImageBitmap?.let { imageBitmap ->
                    Text(text = "Gambar terenkripsi :", style = MaterialTheme.typography.body1)
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp)
                    )
                }
                CustomSpacer()
                CustomSpacer()
                MyButton(
                    onClick = {
                        if (encryptedImageBitmap != null){
                            enkripsiGambarViewModel.decryptImage(enkripsiKey)
                        }
                    },
                    text = "DECRYPT"
                )
                CustomSpacer()
                decryptedImageBitmap?.let { imageBitmap ->
                    Text(text = "Hasil dekripsi :", style = MaterialTheme.typography.body1)
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(300.dp)
                    )
                }
                CustomSpacer()
                CustomSpacer()
                Spacer(modifier = Modifier.weight(1f))
                MyButton(
                    onClick = {
                        enkripsiGambarViewModel.clearForm()
                    },
                    text = "CLEAR"
                )
            }
        }


    }


}
