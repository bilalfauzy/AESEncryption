package com.example.aesencryption.customcomponent

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ImageSelectionButton(
    icon: ImageVector,
    label: String,
    onImageSelected: (ImageBitmap) -> Unit
) {
    val openFileDialog = remember { mutableStateOf(false) }

    Button(
        onClick = { openFileDialog.value = true },
        enabled = !openFileDialog.value
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }

//    if (openFileDialog.value) {
//        openFileDialog.value = false
//        val selectedImageBitmap = selectImageFromGallery() // Replace with your image selection logic
//        if (selectedImageBitmap != null) {
//            onImageSelected(selectedImageBitmap)
//        }
//    }
}
