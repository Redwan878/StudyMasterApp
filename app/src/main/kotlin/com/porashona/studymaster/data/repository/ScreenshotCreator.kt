package com.porashona.studymaster.data.repository

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.os.Looper
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScreenshotCreator(private val activity: Activity) {

    suspend fun captureOverlay(shareText: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val width = 800 // Fixed width for good quality
                val bitmap = Bitmap.createBitmap(width, 600, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE)

                val paint = TextPaint().apply {
                    textSize = 48f
                    color = Color.BLACK
                    isAntiAlias = true
                }

                // Draw share text with word wrapping
                val padding = 48
                val textWidth = width - 2 * padding.toFloat()
                val staticLayout = StaticLayout(
                    shareText,
                    paint,
                    textWidth.toInt(),
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false
                )

                canvas.save()
                canvas.translate(padding.toFloat(), 100f)
                staticLayout.draw(canvas)
                canvas.restore()

                // Add branding
                val infoPaint = TextPaint(paint).apply {
                    textSize = 24f
                    color = Color.GRAY
                }
                canvas.drawText(
                    "Created with StudyMasterApp",
                    padding.toFloat(),
                    550f,
                    infoPaint
                )

                return@withContext bitmap
            } catch (e: Exception) {
                Log.e("ScreenshotCreator", "Error capturing overlay", e)
                return@withContext null
            }
        }
    }

    suspend fun saveScreenshot(bitmap: Bitmap, quality: Int = 85): String? {
        return withContext(Dispatchers.IO) {
            try {
                val dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val fileName = "studymaster_" + System.currentTimeMillis() + ".jpg"
                val file = dir?.let { java.io.File(it, fileName) } ?: return@withContext null

                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, file.outputStream())
                return@withContext file.absolutePath
            } catch (e: Exception) {
                Log.e("ScreenshotCreator", "Error saving screenshot", e)
                return@withContext null
            }
        }
    }
}