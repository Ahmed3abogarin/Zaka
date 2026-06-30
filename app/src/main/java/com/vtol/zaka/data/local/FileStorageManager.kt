package com.vtol.zaka.data.local

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class FileStorageManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private val scansDir = File(context.filesDir, "scans").apply { mkdirs() }

    fun savePdf(bytes: ByteArray, originalName: String): String {
        val fileName = "${System.currentTimeMillis()}_$originalName"
        File(scansDir, fileName).writeBytes(bytes)
        return fileName   // store just the filename in Room
    }

    fun saveImage(bitmap: Bitmap): String {
        val fileName = "${System.currentTimeMillis()}.jpg"
        File(scansDir, fileName).outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return fileName
    }

    fun getFile(fileName: String): File = File(scansDir, fileName)

    fun deleteFile(fileName: String) {
        File(scansDir, fileName).delete()
    }
}