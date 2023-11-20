package com.dhanush.runningapp.db

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class Converters {
    @androidx.room.TypeConverter
    fun bitmapToByteArray(bmp: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
    @androidx.room.TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap{
        return android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
