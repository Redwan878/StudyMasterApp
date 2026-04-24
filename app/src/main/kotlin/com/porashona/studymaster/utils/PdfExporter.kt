package com.porashona.studymaster.utils

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.porashona.studymaster.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Minimal PDF export helper that writes a plain text report to
 * `Documents/StudyMaster/<filename>.pdf` (or the app-specific external docs
 * dir as a fallback) and returns a share `Intent` the caller can launch.
 *
 * Not a styled layout renderer — the goal is a readable single-column PDF
 * that works for weekly reports, session histories, notes, etc. Callers
 * pass the already-formatted body text.
 */
object PdfExporter {

    private const val FONT_SIZE = 11f
    private const val PAGE_WIDTH = 595   // A4 at 72dpi
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 36        // 0.5 inch margin

    data class Result(val file: File, val shareIntent: Intent)

    /**
     * Render [title] + [body] into a PDF under the app's documents dir and
     * return a share-ready intent. Returns null on failure (and toasts the
     * user so they aren't left wondering).
     */
    fun export(context: Context, fileName: String, title: String, body: String): Result? {
        return runCatching { render(context, fileName, title, body) }
            .onFailure {
                Toast.makeText(context, R.string.pdf_export_failure, Toast.LENGTH_LONG).show()
            }
            .getOrNull()
    }

    private fun render(context: Context, fileName: String, title: String, body: String): Result {
        val doc = PdfDocument()
        val titlePaint = Paint().apply {
            textSize = 18f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val metaPaint = Paint().apply {
            textSize = 10f
            isAntiAlias = true
            color = 0xFF666666.toInt()
        }
        val bodyPaint = Paint().apply {
            textSize = FONT_SIZE
            isAntiAlias = true
        }

        val lineHeight = FONT_SIZE + 4f
        val usableWidth = PAGE_WIDTH - 2 * MARGIN
        val wrapped = wrap(body, bodyPaint, usableWidth.toFloat())

        var pageNum = 1
        var lineIdx = 0
        while (lineIdx < wrapped.size || pageNum == 1) {
            val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNum).create()
            val page = doc.startPage(pageInfo)
            val canvas = page.canvas

            var y = MARGIN.toFloat() + titlePaint.textSize
            if (pageNum == 1) {
                canvas.drawText(title, MARGIN.toFloat(), y, titlePaint)
                y += titlePaint.textSize
                val generated = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
                canvas.drawText("Generated $generated", MARGIN.toFloat(), y, metaPaint)
                y += lineHeight + 4f
            }

            while (lineIdx < wrapped.size && y + lineHeight < PAGE_HEIGHT - MARGIN) {
                canvas.drawText(wrapped[lineIdx], MARGIN.toFloat(), y, bodyPaint)
                y += lineHeight
                lineIdx++
            }

            doc.finishPage(page)
            pageNum++
            if (lineIdx >= wrapped.size) break
        }

        val outDir = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "StudyMaster"
        ).apply { mkdirs() }
        val safeName = fileName.replace("[^A-Za-z0-9._-]".toRegex(), "_")
        val outFile = File(outDir, "$safeName.pdf")

        FileOutputStream(outFile).use { doc.writeTo(it) }
        doc.close()

        Toast.makeText(
            context,
            context.getString(R.string.pdf_export_success, outFile.name),
            Toast.LENGTH_LONG,
        ).show()

        val uri: Uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            outFile,
        )
        val share = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        return Result(outFile, Intent.createChooser(share, outFile.name))
    }

    private fun wrap(text: String, paint: Paint, maxWidth: Float): List<String> {
        val lines = mutableListOf<String>()
        for (rawLine in text.split('\n')) {
            if (rawLine.isEmpty()) { lines += ""; continue }
            var remaining = rawLine
            while (remaining.isNotEmpty()) {
                val breakAt = paint.breakText(remaining, true, maxWidth, null)
                if (breakAt <= 0) { lines += remaining; break }
                // Try to break on a space boundary so we don't chop words
                // mid-letter. Walk back up to 20 characters looking for whitespace.
                var cut = breakAt
                if (cut < remaining.length) {
                    val searchStart = (cut - 20).coerceAtLeast(0)
                    val lastSpace = remaining.lastIndexOf(' ', cut)
                    if (lastSpace in searchStart until cut) cut = lastSpace
                }
                lines += remaining.substring(0, cut).trimEnd()
                remaining = remaining.substring(cut).trimStart()
            }
        }
        return lines
    }
}
