package com.example.linguareader.utils


import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PdfUtils {

    companion object {
        fun extractTextFromPdf(context: Context, uri: Uri): String {
            return try {
                val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
                val file = File(context.cacheDir, "temp.pdf")
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                val text = StringBuilder()
                val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))

                for (i in 0 until pdfRenderer.pageCount) {
                    val page = pdfRenderer.openPage(i)
                    // Здесь можно добавить код для извлечения текста из страницы.
                    // Здесь можно использовать библиотеку для извлечения текста, например, PdfBox или другие.
                    // В данном примере просто добавим заглушку.
                    text.append("Содержимое страницы $i\n")
                    page.close()
                }
                pdfRenderer.close()
                text.toString()
            } catch (e: Exception) {
                Log.e("PdfUtils", "Error extracting text from PDF", e)
                ""
            }
        }
    }
}