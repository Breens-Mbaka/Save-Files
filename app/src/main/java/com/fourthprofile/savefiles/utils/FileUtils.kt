package com.fourthprofile.savefiles.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun createImageFile(
    context: Context,
): File {
    val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val picturesDir =
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val folder = File(picturesDir, "breens")

    if (!folder.exists()) {
        folder.mkdirs()
        Log.d("FileUtils", "Folder created because it didn't exist")
    }
    Log.e("FileUtils", "Folder exists: ${folder.exists()}")

    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        folder /* directory */
    )
}

fun retrieveImageFile(
    context: Context,
    absolutePath: String,
): File? {
    val picturesDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val folder = File(picturesDir, "breens")

    return folder.let { dir ->
        val file = File(absolutePath)
        if (file.exists() && file.parent == dir.absolutePath) {
            file
        } else {
            null
        }
    }
}