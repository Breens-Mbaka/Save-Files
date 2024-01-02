package com.fourthprofile.savefiles

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.fourthprofile.savefiles.utils.createImageFile
import com.fourthprofile.savefiles.utils.takePicture
import java.io.File

@Composable
fun HomeScreen(
    onNavigateToSavedImages: (String) -> Unit,
) {
    var currentPhotoPath by remember {
        mutableStateOf<String?>(null)
    }
    var photoFile by remember { mutableStateOf<File?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageBitmap = photoFile?.let { file ->
                    if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(context.contentResolver, Uri.fromFile(file))
                            .asImageBitmap()
                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, Uri.fromFile(file))
                        ImageDecoder.decodeBitmap(source).asImageBitmap()
                    }
                }
            } else {
                Log.d("MainActivity", "takePictureLauncher: $success")
            }
        }
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.spacedBy(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "My Pictures", style = MaterialTheme.typography.titleLarge)

            if (imageBitmap != null) {
                Text(
                    text = "Current photo path: $currentPhotoPath",
                    style = MaterialTheme.typography.titleLarge
                )

                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = null,
                    modifier = Modifier
                        .height(80.dp)
                        .width(60.dp)
                )
            }

            Button(
                onClick = {
                    takePicture(
                        context = context,
                        photoFile = createImageFile(context = context),
                        takePictureLauncher = { uri, file, absolutePath ->
                            photoFile = file
                            currentPhotoPath = absolutePath
                            takePictureLauncher.launch(uri)
                        },
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Take Picture",
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            if (imageBitmap != null) {
                OutlinedButton(
                    onClick = {
                        onNavigateToSavedImages(currentPhotoPath.orEmpty())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "View Picture",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}