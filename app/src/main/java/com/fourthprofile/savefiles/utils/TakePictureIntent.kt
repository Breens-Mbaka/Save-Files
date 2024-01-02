package com.fourthprofile.savefiles.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun takePicture(
    context: Context,
    photoFile: File?,
    takePictureLauncher: (Uri, File, String) -> Unit,
) {
    photoFile?.also { file ->
        val photoURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".fileprovider",
            file,
        )

        takePictureLauncher(photoURI, file, file.absolutePath)
    }
}