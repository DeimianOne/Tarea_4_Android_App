package com.example.mediaexplorer.ui.components_utils

fun uriToFilePath(context: Context, uriString: String): String {
    val uri = Uri.parse(uriString)
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}")
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}