import android.content.Context
import android.net.Uri
import android.util.Log
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PdfUtils {

    companion object {
        fun extractTextFromPdf(context: Context, uri: Uri): String {
            return try {
                // Инициализация PDFBox
                PDFBoxResourceLoader.init(context)

                val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
                val file = File(context.cacheDir, "temp.pdf")
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                // Открываем PDF файл с помощью PDFBox
                val document = PDDocument.load(file)
                val pdfStripper = PDFTextStripper()

                // Извлекаем текст
                val text = pdfStripper.getText(document)
                document.close()

                text
            } catch (e: Exception) {
                Log.e("PdfUtils", "Error extracting text from PDF", e)
                ""
            }
        }
    }
}